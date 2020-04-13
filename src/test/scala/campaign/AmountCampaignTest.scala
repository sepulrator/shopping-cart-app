package campaign

import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.models.campaigns.Campaign
import shopping.cart.app.models.{DiscountType, Product}
import utils.CategoryData

class AmountCampaignTest
  extends AnyFunSpecLike
  //  with GivenWhenThen
    with CategoryData {
  
    describe("A Amount Campaign") {
      it("given campaign and product with different category,  calculate discount as 0") {
        val campaign = Campaign(foodCategory, 1, 10, DiscountType.Amount)
        val quantity = 1
        assert(campaign.calculateDiscount(10000, quantity) == 0.0)
      }
  
      it("given campaign has higher min quantity limit than product quantity should calculate discount as 0") {
        val campaign = Campaign(electronicsCategory, 1, 2, DiscountType.Amount)
        val quantity = 1
        val discount = campaign.calculateDiscount(10000, quantity)
        assert(discount == 0.0)
      }
  
      it("given product with 0 amount should calculate discount as 0") {
        val campaign = Campaign(electronicsCategory, 1, 2, DiscountType.Amount)
        val product  = Product("macbook pro", 10000, electronicsCategory)
        val quantity = 1
        val discount = campaign.calculateDiscount(10000, quantity)
        assert(discount == 0.0)
      }
  
      it("given product and campaign with same category and product quantity equals campaign min quantity should calculate discount") {
        val campaign = Campaign(electronicsCategory, 500, 2, DiscountType.Amount)
        val quantity = 2
        val discount = campaign.calculateDiscount(10000, quantity)
        assert(discount == 500)
      }
  
      it("given product and campaign with same category and product quantity higher than campaign min quantity should calculate discount") {
        val campaign = Campaign(electronicsCategory, 500, 2, DiscountType.Amount)
        val quantity = 3
        val discount = campaign.calculateDiscount(10000, quantity)
        assert(discount == 500.0)
      }
  
      it("given product with 0 price should calculate discount as 0") {
        val campaign = Campaign(electronicsCategory, 10, 2, DiscountType.Amount)
        val quantity = 3
        val discount = campaign.calculateDiscount(0, quantity)
        assert(discount == 0.0)
      }

      it("given product with higher amount than campaign discount should calculate discount as product price") {
        val campaign = Campaign(electronicsCategory, 15000, 2, DiscountType.Amount)
        val quantity = 3
        val discount = campaign.calculateDiscount(10000, quantity)
        assert(discount == 10000.0)
      }
    }
  }