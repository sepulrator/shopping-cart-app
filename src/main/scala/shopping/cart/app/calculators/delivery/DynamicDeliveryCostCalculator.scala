package shopping.cart.app.calculators.delivery

import shopping.cart.app.ShoppingCart
import scala.math.BigDecimal

class DynamicDeliveryCostCalculator(costPerDelivery: BigDecimal,
                                    costsPerProduct: BigDecimal,
                                    fixedCost: BigDecimal)
  extends DeliveryCostCalculator {

  override def calculateFor(cart: ShoppingCart): BigDecimal = {
    val numOfProducts = cart.getNumberOfDistinctProducts
    if (numOfProducts == 0) {
      0
    } else {
      val numOfDeliveries = cart.getNumberOfDistinctCategories
      (costPerDelivery * numOfDeliveries) + (costsPerProduct * numOfProducts) + fixedCost
    }
  }

}