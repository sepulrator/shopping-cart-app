import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.calculators.campaign.{CampaignsDiscountDecider, CampaignsDiscountDeciderMethod}
import shopping.cart.app.{Constants, ShoppingCart}
import shopping.cart.app.calculators.delivery.{DeliveryCostCalculator, DeliveryPricingMethod}
import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{DiscountType, Product}
import shopping.cart.app.models.coupons.Coupon
import utils.CategoryData

class ShoppingCartTest
  extends AnyFunSpecLike
    with MockFactory
    with CategoryData {

  describe("A Shopping Cart") {

    it("ShoppingCart total product price should be multiplication product and its price") {
      val cart = new ShoppingCart()
      val product = Product("macbook pro", 10000, computerCategory)
      cart.addItem(product, 2)
      cart.addItem(product, 2)

      assert(cart.getProductMap.size == 1)
      assert(cart.getTotalAmount == cart.getTotalAmountAfterDiscounts)
      assert(cart.getTotalAmount == 40000)
      assert(cart.getNumberOfDistinctProducts == 1)
      assert(cart.getNumberOfDistinctCategories == 1)
      assert(cart.getCampaignDiscount == 0)
      assert(cart.getCouponDiscount == 0)
    }

    it("ShoppingCart dynamic delivery cost should be for 1 distinct category and 1 distinct product") {
      val cart = new ShoppingCart()
      val product = Product("macbook pro", 10000, computerCategory)
      cart.setDeliveryCostCalculator(DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 10, Constants.DELIVERY_FIXED_COST))
      cart.addItem(product, 2)
      cart.addItem(product, 2)

      assert(cart.getDeliveryCost == 22.99)
    }

    it("ShoppingCart dynamic delivery cost should be for 2 distinct category and 3 distinct product") {
      val cart = new ShoppingCart()
      val product1 = Product("macbook pro", 10000, computerCategory)
      val product2 = Product("iphone", 5000, phoneCategory)
      val product3 = Product("flour", 30, foodCategory)

      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      cart.setDeliveryCostCalculator(deliveryCostCalculator)
      cart.addItem(product1, 1)
      cart.addItem(product2, 2)
      cart.addItem(product3, 1)
      cart.addItem(product1, 1)
      cart.print

      assert(cart.getNumberOfDistinctCategories == 3)
      assert(cart.getNumberOfDistinctProducts == 3)
      assert(cart.getTotalAmountAfterDiscounts == 30030)
      assert(cart.getDeliveryCost == 47.99)
    }

    it("ShoppingCart coupon discount should be recalculated after each product item added") {
      val cart = new ShoppingCart()
      val product1 = Product("macbook pro", 10000, computerCategory)
      val product2 = Product("iphone", 5000, phoneCategory)
      val product3 = Product("flour", 30, foodCategory)

      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      cart.setDeliveryCostCalculator(deliveryCostCalculator)

      cart.applyCoupon(Coupon(10000, 1000, DiscountType.Amount))
      cart.addItem(product1, 2)
      cart.addItem(product2, 2)
      cart.addItem(product3, 1)
      cart.print
      assert(cart.getNumberOfDistinctCategories == 3)
      assert(cart.getNumberOfDistinctProducts == 3)
      assert(cart.getTotalAmountAfterDiscounts == 29030)
      assert(cart.getCouponDiscount == 1000)
      assert(cart.getDeliveryCost == 47.99)
    }

    it("ShoppingCart should calcualte discount from latest applied coupon") {
      val cart = new ShoppingCart()
      val product1 = Product("macbook pro", 10000, computerCategory)
      val product2 = Product("iphone", 5000, phoneCategory)
      val product3 = Product("flour", 30, foodCategory)

      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      cart.setDeliveryCostCalculator(deliveryCostCalculator)

      cart.applyCoupon(Coupon(5000, 1000, DiscountType.Amount))
      cart.addItem(product1, 2)
      cart.addItem(product2, 2)
      cart.addItem(product3, 1)
      cart.applyCoupon(Coupon(10000, 1000, DiscountType.Amount))
      cart.print
      assert(cart.getNumberOfDistinctCategories == 3)
      assert(cart.getNumberOfDistinctProducts == 3)
      assert(cart.getTotalAmountAfterDiscounts == 29030)
      assert(cart.getCouponDiscount == 1000)
      assert(cart.getDeliveryCost == 47.99)
    }

    it("ShoppingCart campaigns discount should be recalculated after each product item added") {
      val cart = new ShoppingCart()
      val product1 = Product("macbook pro", 10000, computerCategory)
      val product2 = Product("iphone", 5000, phoneCategory)
      val product3 = Product("flour", 30, foodCategory)

      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      cart.setDeliveryCostCalculator(deliveryCostCalculator)
      val campaignsDiscountDecider = CampaignsDiscountDecider(CampaignsDiscountDeciderMethod.SelectMaxDiscount)
      cart.setCampaignsDiscountDecider(campaignsDiscountDecider)

      val campaign1 = Campaign(electronicsCategory, 2000, 1, DiscountType.Amount)
      cart.applyDiscounts(campaign1)
      val campaign2 = Campaign(electronicsCategory, 1, 1, DiscountType.Rate)
      cart.applyDiscounts(campaign2)

      cart.applyCoupon(Coupon(10000, 1000, DiscountType.Amount))
      cart.addItem(product1, 2)
      cart.addItem(product2, 2)
      cart.addItem(product3, 1)
      cart.print
      assert(cart.getNumberOfDistinctCategories == 3)
      assert(cart.getNumberOfDistinctProducts == 3)
      assert(cart.getCampaignDiscount == 2000)
      assert(cart.getCouponDiscount == 1000)
      assert(cart.getTotalAmount == 30030)
      assert(cart.getTotalAmountAfterDiscounts == 27030)
      assert(cart.getDeliveryCost == 47.99)
    }

    it("ShoppingCart campaigns discount should be calculated after applying discounts") {
      val cart = new ShoppingCart()
      val product1 = Product("macbook pro", 10000, electronicsCategory)
      val product2 = Product("iphone", 5000, electronicsCategory)
      val product3 = Product("flour", 30, foodCategory)

      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      cart.setDeliveryCostCalculator(deliveryCostCalculator)
      val campaignsDiscountDecider = CampaignsDiscountDecider(CampaignsDiscountDeciderMethod.SelectMaxDiscount)
      cart.setCampaignsDiscountDecider(campaignsDiscountDecider)

      cart.addItem(product1, 2)
      cart.addItem(product2, 2)
      cart.addItem(product3, 1)

      val campaign1 = Campaign(electronicsCategory, 2000, 1, DiscountType.Amount)
      cart.applyDiscounts(campaign1)
      val campaign2 = Campaign(electronicsCategory, 1, 1, DiscountType.Rate)
      cart.applyDiscounts(campaign2)

      cart.applyCoupon(Coupon(10000, 1000, DiscountType.Amount))
      cart.print

      assert(cart.getNumberOfDistinctCategories == 2)
      assert(cart.getNumberOfDistinctProducts == 3)
      assert(cart.getCampaignDiscount == 2000)
      assert(cart.getCouponDiscount == 1000)
      assert(cart.getTotalAmount == 30030)
      assert(cart.getTotalAmountAfterDiscounts == 27030)
      assert(cart.getDeliveryCost == 37.99)
    }

  }

}