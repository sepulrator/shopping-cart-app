package shopping.cart.app

object Exceptions {

  case class InvalidRateForDiscountException() extends Exception("Discount Rate should have rate between 0 and 100")

}
