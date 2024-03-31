package algonquin.cst2335.project_recipe.Models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.project_recipe.data.RecipePhoto;

/**
 * this viewModel class extends the ViewModel class provided by the android architecture component library
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public class RecipeViewModel extends ViewModel
{
    public MutableLiveData<ArrayList<RecipePhoto>> downloadedRecipe = new MutableLiveData<>();

    public MutableLiveData<RecipePhoto>selectedRecipe = new MutableLiveData<>();
}
