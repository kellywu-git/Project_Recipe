package algonquin.cst2335.project_recipe.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_dashboard );

        getSupportFragmentManager().beginTransaction().add( R.id.dashboard_fragment, new DashboardFragment(), "dashboardFragment" ).commit();
    }
}
