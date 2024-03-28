package algonquin.cst2335.project_recipe.ui;

import static android.media.CamcorderProfile.get;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.project_recipe.R;
import algonquin.cst2335.project_recipe.data.RecipeDAO;
import algonquin.cst2335.project_recipe.data.RecipeDatabase;
import algonquin.cst2335.project_recipe.data.RecipeSearched;
import algonquin.cst2335.project_recipe.data.RecipeViewModel;

import algonquin.cst2335.project_recipe.databinding.ActivityRecipeMainBinding;
import algonquin.cst2335.project_recipe.databinding.RecipeItemBinding;

/**
 * main activity for recipe search page
 * @author Kelly Wu
 * @date March 30, 2024
 */
public class RecipeActivity extends AppCompatActivity {


    protected String recipeSearch;
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
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

//   /**
//     * @param onPause saves the last search keyword to a sharepreference file.
//     */
//    @Override
//    protected void onPause(){
//        super.onPause();
//        SharedPreferences.Editor editor= prefs.edit();
//        //save the last searched keyword to shared preference file
//        editor.putString(KEY_LASTSEARCH, lastSearch);
//        editor.commit();
//    }
//    *//

    /**
     * @param savedInstanceState saved instance state, if available. it calls when the activity is created.
     *                           initialize the activity by setting the layout, toolbar and adapter
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        recipebinding= ActivityRecipeMainBinding.inflate(getLayoutInflater());
        setContentView(recipebinding.getRoot());

        setSupportActionBar(recipebinding.recipeToolbar);
        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "database-name").build();
        rDAO = db.rDAO();
        recipeModel=new ViewModelProvider(this).get(RecipeViewModel.class);
        recipes = recipeModel.downloadedRecipe.getValue();
        queue = Volley.newRequestQueue(this);
        SharedPreferences prefs = getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        String searchRecipe=prefs.getString("search", "");
        EditText sRecipt = findViewById(R.id.searchRecipe);
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
                else {
                    ImageRequest imgReq = new ImageRequest(newRecipe.URL, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            Toast.makeText(getApplicationContext(), newRecipe.URL,
                                    Toast.LENGTH_LONG).show();
                            Bitmap image = bitmap;
                            RecipePhotoDetailFragment recipeFragment = new RecipePhotoDetailFragment(newRecipe, image);
                            getSupportFragmentManager().beginTransaction().addToBackStack("")
                                    .replace(R.id.fragmentLocation, recipeFragment).commit();
                            try {
                                image.compress(Bitmap.CompressFormat.PNG, 100, RecipeActivity.this
                                        .openFileOutput(newRecipe.title + ".png", Activity.MODE_PRIVATE));
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, 100, 100, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Snackbar.make(recipebinding.getRoot(), getResources().getString(R.string.errorMessage), Snackbar.LENGTH_LONG).show();
                        }
                    });
                    queue.add(imgReq);
                }
            });
        /**
         * set onClickListener for the search button in the binding layout file
         * this method retrieves the user-entered search words from the edittext view and saves it to SharedPreferences.
         * if it is empty, this method retrieves all recipes from the database and loads them into the recyclerView using the myAdapter adapter
         * this method sends a GET request to the recipe API with the entered food request and loads up to 10 recipes into the RecyclerView
         * @param click on onClickListener that triggers when the search button isClicked.
         */

        recipebinding.searchBtn.setOnClickListener(click->{
            recipeSearch = recipebinding.searchRecipe.getText().toString();
            recipes.clear();
            myAdapter.notifyDataSetChanged();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(getResources().getString(R.string.searchRecipe), recipeSearch);
            editor.apply();

            if(TextUtils.isEmpty(recipeSearch))
            {
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(()->
                {
                    recipes.addAll(rDAO.getAllrecipes());
                    runOnUiThread(()-> recipebinding.recipeList.setAdapter(myAdapter)); //load the recyclerview
                        });
                recipebinding.searchRecipe.setText("");

            }
            else {
                String stringURL = null;
                stringURL = getResources().getString(R.string.url)
                        + recipeSearch
                        + getResources().getString(R.string.apiKey);
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                        (response) ->
                        {
                            try {
                                JSONArray recipeArray = response.getJSONArray("recipes");
                                int size = recipeArray.length() > 10 ? 10 : recipeArray.length();
                                for (int i = 0; i < size; i++) {
                                    JSONObject j = recipeArray.getJSONObject(i);
                                    JSONObject title = j.getJSONObject("Title");
                                    String img = j.getString("img_src").replace("http", "https");
                                    JSONObject s = j.getJSONObject("summary");
                                    RecipeSearched n = new RecipeSearched(j.getInt("id"), title.getString("title"),
                                            s.getString("summary"), img);
                                    recipes.add(n);

                                    Executor thread = Executors.newSingleThreadExecutor();
                                    thread.execute(() ->
                                    {
                                        runOnUiThread(() -> recipebinding.recipeList.setAdapter(myAdapter)); //load the Recyclerview
                                    });
                                    myAdapter.notifyItemInserted(recipes.size() - 1);
                                }
                            }
                            catch (JSONException e)
                            {
                                throw new RuntimeException(e);
                            }
                        }, (error) -> {});
                queue.add(request);
            }
            });
        /**
         * this section is to hold the whole search result - recycler view
         */

        class MyRowHolder extends RecyclerView.ViewHolder
        {
            TextView titleText;
            ImageView recipeImage;
            public MyRowHolder(@NonNull View itemView)
            {
                super(itemView);
                itemView.setOnClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    RecipeSearched selected = recipes.get(position);
                    recipeModel.selectedRecipe.postValue(selected);
                });
                titleText = itemView.findViewById(R.id.recipeTitle);
                recipeImage = itemView.findViewById(R.id.recipeImage);
            }
        }
        recipebinding.recipeList.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            /**
             * this class is to create the viewHolder to display the recipt photos in the search result
             * return myRowHolder
             */
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                RecipeItemBinding binding = RecipeItemBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            /**
             * binds data to the ViewHolder and loads the image using ImageRequest
             * @param holder it should be updated to represent the contents of the item at the given position
             *               in the data set
             * @param position the position of the item within the adapter's data set
             */

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String name = recipes.get(position).title;
                holder.titleText.setText(name);

                ImageRequest imgReq = new ImageRequest(recipes.get(position).URL, new Response.Listener<Bitmap>()
                {
                    @Override
                    public void onResponse(Bitmap bitmap)
                    {
                        Bitmap image = bitmap;
                        holder.recipeImage.setImageBitmap(image);
                    }
                }, 100, 100, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Snackbar.make(recipebinding.getRoot(), "Error", Snackbar.LENGTH_LONG).show();
                    }
                });
                queue.add(imgReq);
            }

            @Override
            public int getItemCount() {
                return recipes.size();}

        });
        recipebinding.recipeList.setLayoutManager(new LinearLayoutManager(this));

        }
    }



