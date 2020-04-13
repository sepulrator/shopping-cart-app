package shopping.cart.app

import shopping.cart.app.calculators.campaign.{CampaignsDiscountDecider, CampaignsDiscountDeciderMethod}
import shopping.cart.app.calculators.delivery.DeliveryCostCalculator
import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{Category, Product}
import shopping.cart.app.models.coupons.Coupon

class ShoppingCart() {

  private var productMap = Map.empty[Product, Long]

  private var deliveryCost          = BigDecimal(0.0)
  private var totalAmount           = BigDecimal(0.0)
  private var discountedTotalAmount = BigDecimal(0.0)
  private var campaignDiscount      = BigDecimal(0.0)
  private var couponDiscount        = BigDecimal(0.0)
  private var appliedCampaigns      = Seq.empty[Campaign]
  private var appliedCoupon         = Option.empty[Coupon]

  private var deliveryCostCalculator   = Option.empty[DeliveryCostCalculator]
  private var campaignsDiscountDecider = Option.empty[CampaignsDiscountDecider]

  def addItem(product: Product, quantity: Long): Unit = {
    productMap  += (product -> (productMap.get(product).getOrElse(0L) + quantity))
    totalAmount += product.price * quantity
    calculateDeliveryCost
    calculateDiscounts
  }

  def applyCoupon(coupon: Coupon): Unit = {
    appliedCoupon = Some(coupon)
    calculateDiscounts
  }

  def applyDiscounts(campaigns: Campaign*): Unit = {
    appliedCampaigns = appliedCampaigns ++ campaigns.toSeq
    calculateDiscounts
  }

  private def calculateDiscounts(): Unit = {
    discountedTotalAmount = totalAmount
    campaignDiscount = campaignsDiscountDecider
      .map(_.getDiscount(appliedCampaigns, getAllCategoryProductMap, productMap))
      .getOrElse(0.0)
    discountedTotalAmount -= campaignDiscount

    couponDiscount = appliedCoupon
      .map(_.calculateDiscount(this))
      .getOrElse(0)
    discountedTotalAmount -= couponDiscount
  }

  def setCampaignsDiscountDecider(discountDecider: CampaignsDiscountDecider): Unit = {
    campaignsDiscountDecider = Some(discountDecider)
  }

  /**
   *
   * @return map of products by its categories and parent categories
   */
  private def getAllCategoryProductMap: Map[Category, Seq[Product]] = {
    var catMap = Map.empty[Category, Seq[Product]]
    for {
      p <- productMap.keys
      c <- p.category.getAllCategories
    } yield catMap += c -> (catMap.getOrElse(c, Seq.empty[Product]) :+ p)
    catMap
  }

  /**
   *
   * @return map of products by its categories
   */
  private def getCategoryProductMap: Map[Category, Seq[Product]] = {
    var catMap = Map.empty[Category, Seq[Product]]
    for {
      p <- productMap.keys
    } yield catMap += p.category -> (catMap.getOrElse(p.category, Seq.empty[Product]) :+ p)
    catMap
  }

  def getProductMap = productMap

  def getTotalAmount: BigDecimal = totalAmount

  def getTotalAmountAfterDiscounts: BigDecimal = discountedTotalAmount

  def getNumberOfDistinctProducts: Long = productMap.keys.size

  def getNumberOfDistinctCategories: Long = productMap.keys.map(_.category).toSet.size

  def getCouponDiscount: BigDecimal = couponDiscount

  def getCampaignDiscount: BigDecimal = campaignDiscount

  private def calculateDeliveryCost = {
    deliveryCost = deliveryCostCalculator
      .map(_.calculateFor(this))
      .getOrElse(0.0)
  }

  def setDeliveryCostCalculator(deliveryCalculator: DeliveryCostCalculator) =
    deliveryCostCalculator = Some(deliveryCalculator)

  def getDeliveryCost: BigDecimal = deliveryCost

  def print: Unit = {
    println("Items:")
    println(s"|------------------|---------------|---------|------------------|------------------|")
    println(s"|CATEGORY NAME     |PRODUCT NAME   |QUANTITY |UNIT PRICE        |TOTAL PRICE       |")
    println(s"|------------------|---------------|---------|------------------|------------------|")
    getCategoryProductMap.foreach { case (category, products) =>
      products.foreach {
        p => println(s"|${category.title.padTo(17, " ").mkString} |${p.title.padTo(14, " ").mkString} |${productMap(p).toString.padTo(8, " ").mkString} |${p.price.toString().padTo(17, " ").mkString} |${(p.price * productMap(p)).toString().padTo(17, " ").mkString} |")
        println(s"|------------------|---------------|---------|------------------|------------------|")
      }
    }
    println()
    println("Amount info:")
    println(
      s""" | |-------------------------------------------|
           | |Total Amount            = ${totalAmount.toString().padTo(17," ").mkString}|
           | |-------------------------------------------|
           | |Discount Amount         = ${(campaignDiscount + couponDiscount).toString().padTo(17," ").mkString}|
           | |-------------------------------------------|
           | |Discounted Total Amount = ${discountedTotalAmount.toString().padTo(17," ").mkString}|
           | |-------------------------------------------|
           | |Delivery Cost           = ${deliveryCost.toString().padTo(17," ").mkString}|
           | |-------------------------------------------|
           |""".stripMargin)
  }

}