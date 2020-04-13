package shopping.cart.app.models.coupons

import shopping.cart.app.Exceptions.InvalidRateForDiscountException
import shopping.cart.app.ShoppingCart
import shopping.cart.app.models.DiscountType

trait Coupon {
  val minPurchaseAmount: BigDecimal
  val discount: BigDecimal

  def isApplicable(cart: ShoppingCart): Boolean =
    cart.getTotalAmountAfterDiscounts > 0.0 && cart.getTotalAmountAfterDiscounts >= minPurchaseAmount

  protected def getDiscount(cart: ShoppingCart): BigDecimal

  def calculateDiscount(cart: ShoppingCart): BigDecimal =
    if (isApplicable(cart)) getDiscount(cart) else 0.0

}

object Coupon {

  def apply(minPurchaseAmount: BigDecimal, discount: BigDecimal, discountType: DiscountType.Value): Coupon = {
    discountType match {
      case DiscountType.Rate   =>
        if (discount > 100.0) throw InvalidRateForDiscountException()
        new RateCoupon(minPurchaseAmount, discount)
      case DiscountType.Amount => new AmountCoupon(minPurchaseAmount, discount)
      case _                   => ??? // throws NotImplementedError at runtime
    }
  }

}
