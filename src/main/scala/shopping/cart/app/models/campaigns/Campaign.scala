package shopping.cart.app.models.campaigns

import shopping.cart.app.Exceptions.{InvalidRateForDiscountException}
import shopping.cart.app.models.{Category, DiscountType}

trait Campaign {

  val category: Category
  val discount: BigDecimal
  val minQuantity: Long

  private def isApplicable(quantity: Long): Boolean =
    minQuantity <= quantity

  protected def getDiscount(amount: BigDecimal): BigDecimal

  def calculateDiscount(amount: BigDecimal, quantity: Long): BigDecimal = {
    if (isApplicable(quantity))
      getDiscount(amount).min(amount)
    else
      0.0
  }

}

object Campaign {

  def apply(category: Category, discount: BigDecimal, minQuantity: Long, discountType: DiscountType.Value): Campaign = {
    discountType match {
      case DiscountType.Rate   =>
        if (discount > 100.0) throw InvalidRateForDiscountException()
        new RateCampaign(category, discount, minQuantity)
      case DiscountType.Amount =>
        new AmountCampaign(category, discount, minQuantity)
      case _                   => ??? // throws NotImplementedError at runtime
    }
  }

}