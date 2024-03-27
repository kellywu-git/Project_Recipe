package algonquin.cst2335.project_recipe.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
/**
 * Dao interface have three methods insert/query/delete for implementation
 * @author Kelly Wu
 */
public interface RecipeDAO {
    @Insert
    long insertRecipe(RecipeSearched r);

    @Update
    void updateRecipe(RecipeSearched r);

    @Query("Select * from RecipeSearched")
    List<RecipeSearched> getAllrecipes();

    @Delete
    void deleteRecipe(RecipeSearched r);
}
