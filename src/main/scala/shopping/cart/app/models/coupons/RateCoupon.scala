package shopping.cart.app.models.coupons

import shopping.cart.app.ShoppingCart

class RateCoupon(val minPurchaseAmount: BigDecimal,
                 val discount: BigDecimal)
  extends Coupon {

  override def getDiscount(cart: ShoppingCart): BigDecimal =
    cart.getTotalAmountAfterDiscounts.min((cart.getTotalAmountAfterDiscounts / 100.0) * discount)

}
