# Nutrition Data

A simple web service which supports searching and sorting nutrition data.  Query results are returned as a list of foods and drinks, formatted as a JSON document.

Data comes from https://www.kaggle.com/datasets/trolukovich/nutritional-values-for-common-foods-and-products?resource=download 
and is available under the [CCO: Public Domain](https://creativecommons.org/publicdomain/zero/1.0/) license.


## Querying the data
A number of query parameters are available to customize the search criteria. All query parameters are optional and
may be applied in any order. The full list of query parameters is:

* `fatRating=Low|Medium|High` - filter by fat rating.  When this parameter is omitted, all entries are returned.  Categorization based on https://www.heartuk.org.uk/low-cholesterol-foods/saturated-fat
* `minCalories` - Items will match if they are greater than or equal to the minimum calories.
* `maxCalories` - Items will match if they are less than or equal to the maximum calories.
* `limit` - Limit the number of items returned.  Must be a positive integer.
* `sort` - Items may be sorted by `name` or `calories` or both. The sort parameter is specified as the name of the field,
followed by an underscore (`_`) and then the sort order (`asc` or `desc`), e.g. `calories_desc`. To sort by both name
and calories, specify two sort parameters in the query string, one for each field. The order of the fields 
determines the final order of the items, e.g. if sorting by calories descending and name ascending, the second sort 
parameter is only used when the calories are equal.

  
## Notes
* You will need JDK 11 to compile the project.
* The project is built using Gradle. You can import the project into your favourite IDE or run Gradle from the command line, e.g. from the root directory of the project run: gradlew build
* FilteringTest and AllParamsTest are static inner classes of NutritionControllerTest. Remove (or comment out) the @Disabled annotation to include the tests in the build.
* The test code and test fixtures (JSON files) contain no errors.
* There are no errors in the CSV file of nutrition data.
* Please contact [talent.team@xdesign.com](mailto:talent.team@xdesign.com) if you have any questions, and we’ll respond within normal business hours (Mon-Fri, 09:00 - 17:00).


