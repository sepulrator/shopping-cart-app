package shopping.cart.app.models

case class Category(title: String,
                    parentCategoryOpt: Option[Category] = None) {

  override def hashCode(): Int = title.hashCode

  override def equals(that: Any): Boolean =
    that match {
      case that: Category => that.isInstanceOf[Category] && this.hashCode == that.hashCode
      case _ => false
    }

  def getAllCategories : Set[Category] = getParentCategories + this

  def getParentCategories: Set[Category] = {
    def getParentCategory(category: Category): Set[Category] = {
      category.parentCategoryOpt match {
        case None                 => Set.empty
        case Some(parentCategory) => getParentCategory(parentCategory) + parentCategory
      }
    }
    getParentCategory(this)
  }

}
