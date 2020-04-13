package delivery

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.Constants
import shopping.cart.app.calculators.delivery.{DeliveryCostCalculator, DeliveryPricingMethod, DynamicDeliveryCostCalculator}

class DeliveryCostCalculatorTest extends AnyFunSpecLike {

  describe("A delivery cost calculator") {

    it("Creating delivery cost calculator with dynamic pricing should be type of dynamic delivery cost calculator") {
      val calculator = DeliveryCostCalculator(DeliveryPricingMethod.Dynamic, 10, 10, Constants.DELIVERY_FIXED_COST)
      assert(calculator.isInstanceOf[DynamicDeliveryCostCalculator])
    }
  }

}
