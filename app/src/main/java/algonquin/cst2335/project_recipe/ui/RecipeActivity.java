package algonquin.cst2335.project_recipe.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.ArrayList;

import algonquin.cst2335.project_recipe.R;
import algonquin.cst2335.project_recipe.data.RecipeDAO;
import algonquin.cst2335.project_recipe.data.RecipeDatabase;
import algonquin.cst2335.project_recipe.data.RecipeSearched;
import algonquin.cst2335.project_recipe.data.RecipeViewModel;
import algonquin.cst2335.project_recipe.databinding.ActivityRecipeContainerBinding;
import algonquin.cst2335.project_recipe.databinding.ActivityRecipeMainBinding;

/**
 * main activity for recipe search page
 * @author Kelly Wu
 * @date March 30, 2024
 */
public class RecipeActivity extends AppCompatActivity {
    /**
     * query service URL using API provided by Professor
     */
   public static final String recipeUrl = "https://api.spoonacular.com/recipes";
    /**
     * API key for recipe query request
     */
    public static final string recipeAPIKEY = "432da05c987f4b7fab39e7a708c0de77";
    /**
     * Database helper
     */
    public final String KEY_LASTSEARCH = "lastSearch";

    private String lastSearch;
    protected RequestQueue queue;
    SharedPreferences prefs;
    RecipeViewModel recipeModel;
    private RecyclerView.Adapter myAdapter;
    ArrayList<RecipeSearched>recipes;
    ActivityRecipeMainBinding recipebinding;
    RecipeDAO rDAO;
    public final static String PREFERENCES_FILE = "MyData";

    /**
     * crate the option menu
     * @param menu the menu to create for help
     * @return true if successfully created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.recipe_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    /**
     * implement onOptionItemSelected function for toolbar
     * @param item stands for differnet item on toolbar
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.recipe_help:
                AlertDialog.Builder builder=new AlertDialog.Builder(RecipeActivity.this);
                builder.setMessage(getResources().getString(R.string.instruction))
                        .setTitle(getResources().getString(R.string.help))
                        .setNegativeButton("close",(dialog,cl)->{})
                        .create()
                        .show();
                break;
        }
        return true;
    }

    /**
     * @param onPause saves the last search keyword to a sharepreference file.
     */
    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences.Editor editor= prefs.edit();
        //save the last searched keyword to shared preference file
        editor.putString(KEY_LASTSEARCH, lastSearch);
        editor.commit();
    }

    /**
     * @param savedInstanceState saved instance state, if available. it calls when the activity is created.
     *                           initialize the activity by setting the layout, toolbar and adapter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        recipebinding= ActivityRecipeMainBinding.inflate(getLayoutInflater())
        setContentView(recipebinding.getRoot());

        setSupportActionBar(recipebinding.recipe_Toolbar);
        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "database-name").build();
        rDAO = db.rDAO();
        recipeModel=new ViewModelProvider(this).get(RecipeViewModel.class);
        recipes = recipeModel.downloadedRecipe.getValue();
        queue = Volley.newRequestQueue(this);
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        String searchRecipe=prefs.getString("search", "");
        EditText sRecipt = findViewById(R.id.search_recipe);
        sRecipt.setText(searchRecipe);
        if (recipes == null) {
            recipeModel.downloadedRecipe.setValue(recipes = new ArrayList<>());
        }
             recipeModel.selectedRecipe.observe(this, (newRecipe)->{
                String pathname=getFilesDir()+"/"+newRecipe.title + ".png";
                File file=new File(pathname);
                if(file.exists()){
                    Toast.makeText(getApplicationContext(), pathname,
                            Toast.LENGTH_LONG).show();
                    Bitmap image = BitmapFactory.decodeFile(pathname);
                    RecipePhotoDetailFragment recipeFragment=new RecipePhotoDetailFragment(newRecipe, image);
                    getSupportFragmentManager().beginTransaction().addToBackStack("")
                            .replace(R.id.fragmentLocation, recipeFragment).commit();

                }
            })
        }


        //toast message
        Toast.makeText(this, getString(R.string.welcome_message), Toast.LENGTH_LONG).show();
        //initialize adapter
        adapter=new MyListAdapter(this, R.id.recipe_list);
    }
    /**
     *
     * @param query the query text that is to be submitted
     *
     * @return
     */

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
