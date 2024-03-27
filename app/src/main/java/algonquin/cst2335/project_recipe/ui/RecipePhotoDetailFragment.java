package algonquin.cst2335.project_recipe.ui;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.project_recipe.data.RecipeDAO;
import algonquin.cst2335.project_recipe.data.RecipeSearched;

/**
 * this class is to display/delete/add selected recipe
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public class RecipePhotoDetailFragment extends Fragment
{
    RecipeSearched selected;
    Bitmap image;
    RecipeDAO rDAO;

    /**
     * use two parameters to display recipedetails especially referring to photos
     * @param n photo
     * @param image image
     */
    public RecipePhotoDetailFragment(RecipeSearched n, Bitmap image){
        selected=n;
        this.image=image;
    }

}
