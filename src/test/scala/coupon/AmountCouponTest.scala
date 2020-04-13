package coupon

import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.ShoppingCart
import shopping.cart.app.models.DiscountType
import shopping.cart.app.models.coupons.Coupon

class AmountCouponTest
  extends AnyFunSpecLike with MockFactory {

  describe("A amount coupon") {

    it("A amount coupon should calculate discount as 0 for given total discount amount as 0") {
//      val shoppingCart = new ShoppingCart()
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(80)

      val coupon = Coupon(100, 50, DiscountType.Amount)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 0)
    }

    it("A amount coupon should calculate discount as discounted as 0 when total amount < coupon min purchase amount") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(80)

      val coupon = Coupon(200, 100, DiscountType.Amount)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 0)
    }

    it("A amount coupon should calculate discount as discounted total amount when discount > amount") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(80)

      val coupon = Coupon(50, 100, DiscountType.Amount)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 80)
    }

    it("A amount coupon should calculate discount as discounted amount when discount < amount") {
      val shoppingCart = mock[ShoppingCart]
      (shoppingCart.getTotalAmountAfterDiscounts _).stubs().returns(150)

      val coupon = Coupon(50, 100, DiscountType.Amount)
      val couponDiscount = coupon.calculateDiscount(shoppingCart)
      assert(couponDiscount == 100)
    }

  }

}
