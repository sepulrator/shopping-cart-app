package shopping.cart.app.calculators.campaign

import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{Category, Product}
import CampaignsDiscountDeciderMethod._

trait CampaignsDiscountDecider {

  /**
   * @param campaigns
   * @param categoryProductMap keeps products grouped by its categories and parent categories
   * @param productMap         keeps product and its quantity
   * @return max campaign discount amount by calculating products of campaigns categories
   */
  def getDiscount(campaigns: Seq[Campaign],
                  categoryProductMap: Map[Category, Seq[Product]],
                  productMap: Map[Product, Long]): BigDecimal
}

object CampaignsDiscountDecider {

  def apply(selectionMethod: CampaignsDiscountDeciderMethod.Value): CampaignsDiscountDecider = {
    selectionMethod match {
      case SelectMaxDiscount => new MaxCampaignDiscountDecider()
      case _                 => ??? // throws NotImplementedError at runtime
    }
  }
}