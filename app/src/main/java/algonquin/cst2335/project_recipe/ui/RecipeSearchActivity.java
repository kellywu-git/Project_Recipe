package algonquin.cst2335.project_recipe.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_recipesearch );

        getSupportFragmentManager().beginTransaction().add( R.id.recipeSearchActivity_fragment, new RecipeSearchFragment(), "recipeSearchFragment" ).commit();
    }
}
