package shopping.cart.app.calculators.delivery

import shopping.cart.app.ShoppingCart

trait DeliveryCostCalculator {

  def calculateFor(cart: ShoppingCart): BigDecimal
}

object DeliveryCostCalculator {

  def apply(deliveryPricingMethod: DeliveryPricingMethod.Value,
            costPerDelivery: BigDecimal,
            costsPerProduct: BigDecimal,
            fixedCost: BigDecimal): DeliveryCostCalculator = {

    deliveryPricingMethod match {
      case DeliveryPricingMethod.Dynamic => new DynamicDeliveryCostCalculator(costPerDelivery, costsPerProduct, fixedCost)
      case _                             => ??? // throws NotImplementedError at runtime
    }

  }

}
