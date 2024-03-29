package algonquin.cst2335.project_recipe.data;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import algonquin.cst2335.project_recipe.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private List<Recipe> lstRecipe = new ArrayList<>();
    private List<Recipe> searchRecipe;
    private JSONArray testArr;
    private ImageButton searchBtn;

    private TextView searchTv, emptyView;
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbarContact);
        emptyView= RootView.findViewById(R.id.empty_view2);
        myrv = RootView.findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getRandomRecipes();
        searchTv = RootView.findViewById(R.id.home_search_et);
        searchBtn = RootView.findViewById(R.id.home_search_btn);

        searchBtn.setOnClickListener(this);
        searchTv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if(!v.getText().toString().equals("")) {
                        emptyView.setVisibility(View.GONE);
                        myrv.setAlpha(0);
                        searchRecipe(v.getText().toString());
                    }
                    else
                        Toast.makeText(getContext(), "Type something...", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return RootView;
    }

    private void searchRecipe(String search) {
        searchRecipe = new ArrayList<Recipe>();
        String URL="https://api.spoonacular.com/recipes/search?query=" + search + "&number=30&instructionsRequired=true&apiKey=432da05c987f4b7fab39e7a708c0de77";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("results");
                            Log.i("the search res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                searchRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"), Integer.parseInt(jsonObject1.optString("servings")), Integer.parseInt(jsonObject1.optString("readyInMinutes"))));
                            }
                            if(searchRecipe.isEmpty()){
                                myrv.setAlpha(0);
                                emptyView.setVisibility(View.VISIBLE);
                            }
                            else{
                                emptyView.setVisibility(View.GONE);
                                RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), searchRecipe);
                                myrv.setAdapter(myAdapter);
                                myrv.setItemAnimator(new DefaultItemAnimator());
                                myrv.setAlpha(1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private void getRandomRecipes() {
        String URL = " https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=432da05c987f4b7fab39e7a708c0de77";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                lstRecipe.add(new Recipe(jsonObject1.optString("id"),jsonObject1.optString("title"), jsonObject1.optString("image")));
                            }

                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getContext(), lstRecipe);
                            myrv.setAdapter(myAdapter);
                            myrv.setItemAnimator(new DefaultItemAnimator());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());

                        myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if(v==searchBtn){
            try {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
            }
            if(!searchTv.getText().toString().toString().equals("")) {

                myrv.setAlpha(0);
                searchRecipe(searchTv.getText().toString());
            }
            else
                Toast.makeText(getContext(), "Type something...", Toast.LENGTH_LONG).show();
        }
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.recipe_help:
                AlertDialog.Builder builder=new AlertDialog.Builder(RecipeActivityMain.this);
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



}