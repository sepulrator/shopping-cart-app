package shopping.cart.app.models

case class Product(title: String, price: BigDecimal, category: Category) {

  override def hashCode(): Int = title.hashCode

  override def equals(that: Any): Boolean =
    that match {
      case that: Product => that.isInstanceOf[Product] && this.hashCode == that.hashCode
      case _ => false
    }

}
