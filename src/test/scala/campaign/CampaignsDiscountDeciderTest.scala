package campaign

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.calculators.campaign.{CampaignsDiscountDecider, CampaignsDiscountDeciderMethod, MaxCampaignDiscountDecider}

class CampaignsDiscountDeciderTest extends AnyFunSpecLike {

  describe("A Campaign") {
    it("Creating campaign discount decider with discount type rate should be type of rate campaign") {
      val campaign = CampaignsDiscountDecider(CampaignsDiscountDeciderMethod.SelectMaxDiscount)
      assert(campaign.isInstanceOf[MaxCampaignDiscountDecider])
    }
  }

}
