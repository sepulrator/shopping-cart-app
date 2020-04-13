package campaign

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.calculators.campaign.{CampaignsDiscountDecider, CampaignsDiscountDeciderMethod, MaxCampaignDiscountDecider}
import shopping.cart.app.models.{DiscountType, Product}
import shopping.cart.app.models.campaigns.Campaign
import utils.CategoryData

class MaxCampaignDiscountDeciderTest extends AnyFunSpecLike
  with CategoryData {

  describe("A Campaign") {

    val campaignsDiscountDecider = CampaignsDiscountDecider(CampaignsDiscountDeciderMethod.SelectMaxDiscount)

    it("max campaign discount decider should return discount as 0 for given no campaigns") {
      val campaigns = Seq.empty
      val product1 = Product("macbook pro", 10000, electronicsCategory)
      val product2 = Product("iphone", 5000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product1, product2)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product1 -> 1, product2 -> 1))

      assert(discount == 0)
    }

    it("max campaign discount decider should return discount as 0 for given no products") {
      val campaign1 = Campaign(electronicsCategory, 10, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 100, 1, DiscountType.Amount)
      val campaigns = Seq(campaign1, campaign2)
      val categoryProductMap = Map(electronicsCategory -> Seq.empty)
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map.empty)
      assert(discount == 0)
    }

    it("max campaign discount decider should return discount as 0 for given product with zero quantity") {
      val campaign1 = Campaign(electronicsCategory, 10, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 100, 1, DiscountType.Amount)
      val campaigns = Seq(campaign1, campaign2)
      val product = Product("macbook pro", 10000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product -> 0))
      assert(discount == 0)
    }

    it("max campaign discount decider should return discount  as highest discount among campaigns") {
      val campaign1 = Campaign(electronicsCategory, 10, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 100, 1, DiscountType.Amount)
      val campaigns = Seq(campaign1, campaign2)

      val product = Product("macbook pro", 10000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product -> 2))

      assert(discount == 2000)
    }

    it("max campaign discount decider should return discount as highest discount among campaigns 2") {
      val campaign1 = Campaign(electronicsCategory, 10, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 5000, 1, DiscountType.Amount)
      val campaigns = Seq(campaign1, campaign2)
      val product1 = Product("macbook pro", 10000, electronicsCategory)
      val product2 = Product("iphone", 5000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product1, product2)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product1 -> 1, product2 -> 1))

      assert(discount == 5000)
    }

    it("max campaign discount decider should return discount of campaign applied to parent category which provides highest discount") {
      val campaign1 = Campaign(electronicsCategory, 5, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 200, 1, DiscountType.Amount)
      val campaign3 = Campaign(computerCategory, 10, 1, DiscountType.Rate)
      val campaigns = Seq(campaign1, campaign2, campaign3)
      val product1 = Product("macbook pro", 10000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product1), computerCategory -> Seq(product1)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product1 -> 1))

      assert(discount == 1000)
    }

    it("max campaign discount decider should return discount of campaign applied to category which provides highest discount") {
      val campaign1 = Campaign(electronicsCategory, 1, 1, DiscountType.Rate)
      val campaign2 = Campaign(electronicsCategory, 200, 1, DiscountType.Amount)
      val campaign3 = Campaign(computerCategory, 10, 2, DiscountType.Rate)
      val campaigns = Seq(campaign1, campaign2, campaign3)
      val product1 = Product("macbook pro", 10000, electronicsCategory)
      val categoryProductMap = (Map(electronicsCategory -> Seq(product1), computerCategory -> Seq(product1)))
      val discount = campaignsDiscountDecider.getDiscount(campaigns, categoryProductMap, Map(product1 -> 1))

      assert(discount == 200)
    }

  }

}
