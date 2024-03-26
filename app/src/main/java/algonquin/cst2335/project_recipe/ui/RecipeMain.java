package algonquin.cst2335.project_recipe.ui;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.project_recipe.R;
import algonquin.cst2335.project_recipe.databinding.ActivityReciptMainBinding;

public class ReciptMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityRecipeMainBinding binding;
    ArrayList<FavouriteRecipe>myFavouriteRecipe;
    RecyclerView.Adapter myAdapter;
    RequestQueue queue;
    Bitmap reciptPictures;
    RecipePictureDAO rpDAO;
    public recipePictureDatabase db;
    RecipeViewModel rvm;

    /**
     *
     * @param RecyclerView holder for displaying the favourite recipt
     *  it will include an ImageView, two TextViews, a Button for deletion, and handles onClick events
     *
     */
    class MyRowHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageButton website;
        public TextView summary;
        public TextView time;
        public Button delBtn;

        /**
         * construction  for MyRowHolder, initialize views and set onclick listeners
         * @param itemView for viewholder
          */

        public MyRowHolder(@NonNull View itemView){
            super(itemView);
            //initialize views
         imageView = itemView.findViewById(R.id.imageView);
         website = itemView.findViewById(R.id.website);
         summary=itemView.findViewById(R.id.summary);
         time=itemView.findViewById(R.id.time);
         delBtn=itemView.findViewById(R.id.delBtn);
         //set onClick listener for item view
         itemView.setOnClickListener(click->{
             int position=getAbsoluteAdapterPosition();
         });
//set onclick listener for delete button
            delBtn.setOnClickListener(click->{
                int position=getAbsoluteAdapterPosition();
                FavouriteRecipe clickedFR
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReciptMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarReciptMain.toolbar);
        binding.appBarReciptMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recipt_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.recipt_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_recipt_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}