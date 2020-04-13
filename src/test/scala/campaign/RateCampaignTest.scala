package campaign

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{DiscountType, Product}
import utils.CategoryData

class RateCampaignTest
  extends AnyFunSpecLike
//  with GivenWhenThen
  with CategoryData {

  describe("A Rate Campaign") {
    it("given campaign and product with different category,  calculate discount as 0") {
      val campaign = Campaign(foodCategory, 1, 10, DiscountType.Rate)
      val quantity = 1
      assert(campaign.calculateDiscount(10000, quantity) == 0.0)
    }

    it("given campaign has higher min quantity limit than product quantity should calculate discount as 0") {
      val campaign = Campaign(electronicsCategory, 1, 2, DiscountType.Rate)
      val quantity = 1
      val discount = campaign.calculateDiscount(10000, quantity)
      assert(discount == 0.0)
    }

    it("given product with 0 amount should calculate discount as 0") {
      val campaign = Campaign(electronicsCategory, 1, 2, DiscountType.Rate)
      val quantity = 1
      val discount = campaign.calculateDiscount(10000, quantity)
      assert(discount == 0.0)
    }

    it("given product and campaign with same category and product quantity equals campaign min quantity should calculate discount") {
      val campaign = Campaign(electronicsCategory, 10, 2, DiscountType.Rate)
      val quantity = 2
      val discount = campaign.calculateDiscount(10000, quantity)
      assert(discount == 1000.0)
    }

    it("given product and campaign with same category and product quantity higher than campaign min quantity should calculate discount") {
      val campaign = Campaign(electronicsCategory, 10, 2, DiscountType.Rate)
      val quantity = 3
      val discount = campaign.calculateDiscount(10000, quantity)
      assert(discount == 1000.0)
    }

    it("given product with 0 price should calculate discount as 0") {
      val campaign = Campaign(electronicsCategory, 10, 2, DiscountType.Rate)
      val quantity = 3
      val discount = campaign.calculateDiscount(0, quantity)
      assert(discount == 0.0)
    }

  }

}
