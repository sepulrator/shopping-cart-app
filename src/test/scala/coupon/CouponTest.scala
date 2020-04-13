package coupon

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.Exceptions.InvalidRateForDiscountException
import shopping.cart.app.models.DiscountType
import shopping.cart.app.models.coupons.{AmountCoupon, Coupon, RateCoupon}
import utils.CategoryData

class CouponTest extends AnyFunSpecLike with CategoryData {

  describe("A Coupon") {
    it("Creating coupon with discount type rate should be type of rate coupon") {
      val coupon = Coupon(100, 25, DiscountType.Rate)
      assert(coupon.isInstanceOf[RateCoupon])
    }

    it("Creating coupon with discount type amount should be type of amount coupon") {
      val coupon = Coupon(100, 25, DiscountType.Amount)
      assert(coupon.isInstanceOf[AmountCoupon])
    }

    it("Creating rate coupon with rate higher than 1000 should throw exception") {
      val thrown = intercept[Exception] {
        Coupon(10, 110, DiscountType.Rate)
      }
      assert(thrown.isInstanceOf[InvalidRateForDiscountException])
    }
  }

}
