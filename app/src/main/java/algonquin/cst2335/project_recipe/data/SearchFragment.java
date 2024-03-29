package algonquin.cst2335.project_recipe.data;
import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.project_recipe.R;

public class SearchFragment extends Fragment {
    private List<Ingredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_search, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_search);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(mToolbarContact);
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecyclerViewAdapterIngredient myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);

        Button searchIngredients = RootView.findViewById(R.id.ingredients_search);

        searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tmp = RecyclerViewAdapterIngredient.ingredientsList;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.selectIngredient), Toast.LENGTH_LONG).show();
                }
                else{
                    Intent searchResultsIntent = new Intent(getActivity(), SearchResultsActivity.class);
                    startActivity(searchResultsIntent);
                }
            }
        });


        return RootView;
    }

    private void initializeIngredients() {
        lstIngredient.add(new Ingredient("Beef", jsonObject1.optString("image")));
        lstIngredient.add(new Ingredient("Fish", jsonObject1.optString("image")));
        lstIngredient.add(new Ingredient("Chicken", jsonObject1.optString("image")));
        lstIngredient.add(new Ingredient("Tuna", jsonObject1.optString("image")));

    }



}
