package delivery

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.{Constants, ShoppingCart}
import shopping.cart.app.calculators.delivery.{DeliveryCostCalculator, DeliveryPricingMethod}
import shopping.cart.app.models.Product
import utils.CategoryData

class DynamicDeliveryCostCalculatorTest
  extends AnyFunSpecLike
    with CategoryData {

  describe("A dynamic delivery") {

    it("A dynamic delivery cost calculator should return cost as 0 when no product exists in cart") {
      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 10, Constants.DELIVERY_FIXED_COST)
      val cart = new ShoppingCart()
      val deliveryCost = deliveryCostCalculator.calculateFor(cart)
      assert(deliveryCost == 0)
    }

    it("A dynamic delivery cost calculator should calculate cost for 1 distinct category and 1 distinct product ") {
      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 10, Constants.DELIVERY_FIXED_COST)
      val cart = new ShoppingCart()
      cart.addItem(Product("macbook pro", 10000, electronicsCategory), 2)
      val deliveryCost = deliveryCostCalculator.calculateFor(cart)
      assert(deliveryCost == 22.99)
    }

    it("A dynamic delivery cost calculator should calculate cost for 2 distinct category and 3 distinct product ") {
      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 5, Constants.DELIVERY_FIXED_COST)
      val cart = new ShoppingCart()
      cart.addItem(Product("macbook pro", 10000, electronicsCategory), 2)
      cart.addItem(Product("iphone", 5000, electronicsCategory), 2)
      cart.addItem(Product("flour", 20, foodCategory), 2)
      val deliveryCost = deliveryCostCalculator.calculateFor(cart)
      assert(deliveryCost == 37.99)
    }

    it("A dynamic delivery cost calculator should calculate cost independent of product quantity") {
      val deliveryCostCalculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 10, Constants.DELIVERY_FIXED_COST)
      val cart1 = new ShoppingCart()
      cart1.addItem(Product("macbook pro", 10000, electronicsCategory), 3)
      val deliveryCost1 = deliveryCostCalculator.calculateFor(cart1)

      val cart2 = new ShoppingCart()
      cart2.addItem(Product("macbook pro", 10000, electronicsCategory), 3)
      val deliveryCost2 = deliveryCostCalculator.calculateFor(cart2)

      assert(deliveryCost1 != 0)
      assert(deliveryCost1 == deliveryCost2)
    }
  }

}
