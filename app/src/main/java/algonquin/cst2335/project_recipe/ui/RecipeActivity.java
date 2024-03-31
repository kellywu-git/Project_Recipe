package algonquin.cst2335.project_recipe.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import algonquin.cst2335.project_recipe.R;

public class RecipeActivity extends AppCompatActivity {

    private TextView title, instructions, likes;
    private ImageView img;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private JSONArray ingredientsArr;

    private RecyclerView myrv;
    private boolean like = false;
    public String api_key = "6c93a30ed6624a03be850e3d2c118b6b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Objects.requireNonNull(getSupportActionBar()).hide();

        final Intent intent = getIntent();
        final String recipeId = Objects.requireNonNull(intent.getExtras()).getString("id");
          mAuth = FirebaseAuth.getInstance();
        //    final String id = mAuth.getCurrentUser().getUid();
         mRootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("Bookmarks").child(recipeId);
        Button fab = findViewById(R.id.recipe_fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like = !like;
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (like) {
                            Map favorites = new HashMap();
                            favorites.put("img", intent.getExtras().getString("img"));
                            favorites.put("title", intent.getExtras().getString("title"));
                            mRootRef.setValue(favorites);
                            Toast.makeText(RecipeActivity.this, "The recipe has been Bookmarked.", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                mRootRef.setValue(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        String e = databaseError.toString();
                        Toast.makeText(RecipeActivity.this, e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        img = findViewById(R.id.recipe_image);
        title = findViewById(R.id.recipe_title);
        instructions = findViewById(R.id.recipe_instructions);
        getRecipeData(recipeId);

        mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("mRootRef", String.valueOf(dataSnapshot));
                if (dataSnapshot.getValue() != null) {
                    like = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myrv.setLayoutManager(new GridLayoutManager(this, 2));
    }


    private void getRecipeData(final String recipeId) {
        String URL = " https://api.spoonacular.com/recipes/" + recipeId + "/information?apiKey=" + api_key;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            try {
                                Picasso.get().load((String) response.get("image")).into(img);
                            } catch (Exception e) {
                                img.setImageResource(R.drawable.nopicture);
                            }
                            title.setText((String) response.get("title"));

                            if (response.get("instructions").equals("")) {
                                throw new Exception("No Instructions");
                            } else
                                instructions.setText(Html.fromHtml((String) response.get("instructions")));
                        } catch (Exception e) {
                            String msg  = null;
                            try {
                                msg = "Unfortunately, the recipe you were looking for not found, " +
                                            "to view the original recipe click on the link below:" +
                                            "<a href=" + response.get("spoonacularSourceUrl") + ">"
                                            + response.get("spoonacularSourceUrl") + "</a>";
                            } catch (JSONException ex) {
                                throw new RuntimeException(ex);
                            }
                            instructions.setMovementMethod(LinkMovementMethod.getInstance());
                                instructions.setText(Html.fromHtml(msg));
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
}}

