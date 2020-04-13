package coupon

import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.ShoppingCart
import shopping.cart.app.models.DiscountType
import shopping.cart.app.models.coupons.Coupon

class RateCouponTest
  extends AnyFunSpecLike
    with MockFactory {

  describe("A rate coupon") {

    it("A rate coupon should calculate discount as 0 for given total discount rate as 0") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(0)

      val coupon = Coupon(100, 50, DiscountType.Rate)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 0)
    }

    it("A rate coupon should calculate discount as discounted as 0 when total rate < coupon min purchase rate") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(80)

      val coupon = Coupon(200, 100, DiscountType.Rate)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 0)
    }

    it("A rate coupon should calculate discount as discounted rate when discount < rate") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(150)

      val coupon = Coupon(50, 50, DiscountType.Rate)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 75)
    }

  }

}
