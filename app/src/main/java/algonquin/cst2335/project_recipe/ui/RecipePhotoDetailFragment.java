package algonquin.cst2335.project_recipe.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.project_recipe.R;
import algonquin.cst2335.project_recipe.data.RecipeDAO;
import algonquin.cst2335.project_recipe.data.RecipeDatabase;
import algonquin.cst2335.project_recipe.data.RecipePhoto;
import algonquin.cst2335.project_recipe.databinding.RecipeDetailLayoutBinding;

/**
 * this class is to display/delete/add selected recipe
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public class RecipePhotoDetailFragment extends Fragment
{
    RecipePhoto selected;
    Bitmap image;
    RecipeDAO rDAO;

    /**
     * use two parameters to display recipedetails especially referring to photos
     * @param n photo
     * @param image image
     */
    public RecipePhotoDetailFragment(RecipePhoto n, Bitmap image){
        selected=n;
        this.image=image;
    }

    /**
     * onCreateView() method used to display details of a photo
     * @param inflater the layoutInflater can be used to inflate any views in the fragment
     * @param container, if not null, this is the parent view that fragement's UI should be attached.
     *                   add the view and generate the LayoutParams of the view
     * @param savedInstanceStaye, if not null, this fragment is re-constructed from a previous saved state as a given one.
     * @return binding.getRoot()
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStaye)
    {
        super.onCreateView(inflater, container, savedInstanceStaye);
        RecipeDetailLayoutBinding binding=RecipeDetailLayoutBinding.inflate(inflater);
        RecipeDatabase db = Room.databaseBuilder(getContext(), RecipeDatabase.class, getResources().getString(R.string.dataName)).build();
        rDAO = db.rDAO();
        binding.recipeImage.setImageBitmap(this.image);
        binding.recipeTitle.setText(selected.title);
        binding.summary.setText(selected.summary);

        //set up the button onClickListener function
        binding.saveBtn.setOnClickListener(click->{
            Executor thread= Executors.newSingleThreadExecutor();
            thread.execute(()->{
                boolean exisedRecipe=false;
                for(RecipePhoto rs: rDAO.getAllrecipes()){
                    if(rs.title.equals(selected.title)){
                        Snackbar.make(binding.getRoot(),getResources().getString(R.string.alreadyexisted),
                                Snackbar.LENGTH_LONG).show();
                        exisedRecipe=true;
                    }
                }
                if(!exisedRecipe)
                {
                    rDAO.insertRecipe(selected);
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.addedMessage),
                            Snackbar.LENGTH_LONG).show();
            }
        });

    });
    binding.delete.setOnClickListener(click->{
    AlertDialog.Builder builder = new AlertDialog.Builder( getContext());
    builder.setMessage(getResources().getString(R.string.deletAlert))
            .setTitle("Question:")
            .setNegativeButton("NO", (dialog,cl)->{})
            .setPositiveButton("YES",(dialog,cl)->{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        rDAO.deleteRecipe(selected);
                    }
                }).start();
                Snackbar.make(binding.getRoot(),getResources().getString(R.string.deleteConfirm),
                        Snackbar.LENGTH_LONG);
            }).create().show();

    });
        binding.url.setOnClickListener(click -> {
            Intent browserIntent = new Intent("android.intent.action.VIEW",
                    Uri.parse(selected.URL));
            startActivity(browserIntent);
        });
        return binding.getRoot();

    }
}
