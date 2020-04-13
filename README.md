

### Assumptions
 - It is assumed that title is unique for product and category
 - parent category can has a parent category
 - campaigns also can be applied to parent category. To be applied campaigns also should be search among all product parent categories.
 - all amount fields are type of BigDecimal
 - if total product price of a category is less than applied campaign discount, then total product price is applied as discount
 - if cart total amount is less than coupon discount, then cart total amount is applied as discount
 - Coupon discount applied to total cart amount after campaign amount discounted
 - Delivery cost should be calculated whenever change happens in added products and its quantities
 - In the future, different type of campaign can be implemented and integrated
 - In the future, different type of coupon can be implemented and integrated
 - In the future, different type of campaign discount decider can be implemented and integrated
 - In the future, different type of delivery cost calculation can be implemented and integrated
 - A user can only benefit from single campaign with highest discount
 - A user can apply campaigns at any time
 - A user can apply coupon at any time
 - A user can apply only one coupon
 - A user can change applied coupon by applying another coupon, the last applied coupon discount will be applied
  
 
 ### Build & Run
  - To run the project, first sbt must be installed
  - To run the test `sbt test` on project directory