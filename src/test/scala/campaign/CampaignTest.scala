package campaign

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.Exceptions.InvalidRateForDiscountException
import shopping.cart.app.models.DiscountType
import shopping.cart.app.models.campaigns.{AmountCampaign, Campaign, RateCampaign}
import utils.CategoryData

class CampaignTest extends AnyFunSpecLike with CategoryData {

  describe("A Campaign") {
    it("Creating campaign with discount type rate should be type of rate campaign") {
      val campaign = Campaign(electronicsCategory, 25, 1, DiscountType.Rate)
      assert(campaign.isInstanceOf[RateCampaign])
    }

    it("Creating campaign with discount type amount should be type of amount campaign") {
      val campaign = Campaign(electronicsCategory, 25, 1, DiscountType.Amount)
      assert(campaign.isInstanceOf[AmountCampaign])
    }

    it("Creating rate campaign with rate higher than 1000 should throw exception") {
      val thrown = intercept[Exception] {
        Campaign(electronicsCategory, 101, 1, DiscountType.Rate)
      }
      assert(thrown.isInstanceOf[InvalidRateForDiscountException])
    }
  }

}
