package shopping.cart.app.models.campaigns

import shopping.cart.app.models.Category

class AmountCampaign(val category: Category,
                     val discount: BigDecimal,
                     val minQuantity: Long
                  ) extends Campaign {

  override def getDiscount(amount: BigDecimal): BigDecimal = discount
}
