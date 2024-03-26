package algonquin.cst2335.project_recipe.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import algonquin.cst2335.project_recipe.databinding.ActivityReciptMainBinding;

public class ReciptMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityReciptMainBinding binding;
    ArrayList<FavouriteRecipt>myFavouriteRecipt;
    RecyclerView.Adapter myAdapter;
    RequestQueue queue;
    Bitmap reciptPictures;
    ReciptPictureDAO rpDAO;
    public reciptPictureDatabase db;
    ReciptViewModel rvm;

    /**
     *
     * @param RecyclerView holder for displaying the favourite recipt
     *  it will include an ImageView, two TextViews, a Button for deletion, and handles onClick events
     *
     */
    class MyRowHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView
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