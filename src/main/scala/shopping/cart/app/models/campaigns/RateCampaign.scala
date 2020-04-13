package shopping.cart.app.models.campaigns

import shopping.cart.app.models.Category


class RateCampaign(val category: Category,
                   val discount: BigDecimal,
                   val minQuantity: Long
                  ) extends Campaign {

  override def getDiscount(amount: BigDecimal): BigDecimal =
    (amount / 100.0) * discount

}
