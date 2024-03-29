package algonquin.cst2335.project_recipe.data;

public interface Util {

    // ADD YOUR SPOONACULAR API KEY HERE;
    String spoonacularAPI_Key = "432da05c987f4b7fab39e7a708c0de77";
    String spoonacularRecipeSearchURL =
            "https://api.spoonacular.com/recipes/complexSearch?query={query}&apiKey=" + spoonacularAPI_Key;
    String spoonacularIngredientSearchURL =
            "https://api.spoonacular.com/food/ingredients/search?query={query}&apiKey=" + spoonacularAPI_Key;
    String spoonacularSpecificIngredientSearch =
            "https://api.spoonacular.com/food/ingredients/{id}/information/?amount=1&apiKey=" + spoonacularAPI_Key;
}
