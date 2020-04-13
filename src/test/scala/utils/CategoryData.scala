package utils

import shopping.cart.app.models.Category

trait CategoryData {

  val foodCategory        = Category("food")
  val electronicsCategory = Category("electronics")
  val computerCategory    = Category("computer", Some(electronicsCategory))
  val phoneCategory    = Category("phone", Some(electronicsCategory))

}
