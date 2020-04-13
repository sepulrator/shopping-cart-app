package shopping.cart.app.calculators.campaign

import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{Category, Product}

class MaxCampaignDiscountDecider() extends CampaignsDiscountDecider {

  /**
   * @param campaigns
   * @param categoryProductMap keeps products grouped by its categories and parent categories
   * @param productMap keeps product and its quantity
   * @return max campaign discount amount by calculating products of campaigns categories
   */
  override def getDiscount(campaigns: Seq[Campaign],
                           categoryProductMap: Map[Category, Seq[Product]],
                           productMap: Map[Product, Long]): BigDecimal = {
    campaigns
      .map { campaign =>
        val products             = categoryProductMap.getOrElse(campaign.category, Seq.empty)
        val totalProductPrice    = products.map(p => p.price * BigDecimal(productMap.getOrElse(p, 0L))).sum
        val totalProductQuantity = products.map(productMap.getOrElse(_, 0L)).sum
        campaign.calculateDiscount(totalProductPrice, totalProductQuantity)
      }
      .maxOption
      .getOrElse(0.0)
  }

}