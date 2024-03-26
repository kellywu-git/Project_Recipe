package algonquin.cst2335.project_recipe.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.project_recipe.R;

/**
 * activity running as container for Fragment recipe
 * @author Kelly
 * @project Recipe search
 */
public class EmptyContainerActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState if the activity reinitialized after previously being closed then this
     *                           bundle contains the data it most recently contained in onSaveInstanceState(Bundle)
     *                           otherwise it is null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_container);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample
        Fragment_recipe dFragment = new Fragment_recipe();
        dFragment.setArguments(dataToPass); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_fragmentLocation, dFragment)
                .commit();
    }
}
