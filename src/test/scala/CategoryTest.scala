import org.scalatest.funspec.AnyFunSpecLike
import shopping.cart.app.models.Category

class CategoryTest extends AnyFunSpecLike {

  describe("A Category") {

    it("a category with none parent category should return single category which is self") {
      val electronics   = Category("electronics")
      val allCategories = electronics.getAllCategories
      assert(allCategories.size == 1)
    }

    it("a category with 2 parent category should return all 3 categories including self") {
      val electronics   = Category("electronics")
      val desktopLaptop = Category("desktop and laptop", Some(electronics))
      val laptops       = Category("laptop", Some(desktopLaptop))
      val allCategories = laptops.getAllCategories
      assert(allCategories.size == 3)
    }

    it("a category with same title must have same hashcode") {
      val electronics1   = Category("electronics")
      val electronics2   = Category("electronics")
      assert(Set(electronics1, electronics2).size == 1)
    }
  }

}