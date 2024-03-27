package algonquin.cst2335.project_recipe.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeSearched.class}, version=1)
/**
 * RecipeDatabase extends from RoomDatabase and have abstract class rDAO()
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDAO rDAO();
}
