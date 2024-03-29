package algonquin.cst2335.project_recipe.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
/**
 * Dao interface have three methods insert/query/delete for implementation
 * @author Kelly Wu
 */
public interface RecipeDAO {
    @Insert
    long insertRecipe(Recipe r);

    @Query("Select * from Recipe")
    List<Recipe> getAllrecipes();

    @Delete
    void deleteRecipe(Recipe r);
}
