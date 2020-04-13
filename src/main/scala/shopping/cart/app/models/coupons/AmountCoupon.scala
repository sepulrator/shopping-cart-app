package shopping.cart.app.models.coupons

import shopping.cart.app.ShoppingCart

class AmountCoupon(val minPurchaseAmount: BigDecimal,
                   val discount: BigDecimal) extends Coupon {

  override def getDiscount(cart: ShoppingCart): BigDecimal =
    cart.getTotalAmountAfterDiscounts.min(discount)
}
