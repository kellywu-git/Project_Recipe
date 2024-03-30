package algonquin.cst2335.project_recipe.Activity;
import algonquin.cst2335.project_recipe.R;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import algonquin.cst2335.project_recipe.R;


public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide();
    }
}