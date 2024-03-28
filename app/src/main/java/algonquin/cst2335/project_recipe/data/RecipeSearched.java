package algonquin.cst2335.project_recipe.data;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

/**
 * this class used for Recipe database
 * @author Kelly Wu
 * @March 30, 2024
 */
@Entity
public class RecipeSearched
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="summary")
    public String summary;

   @ColumnInfo(name="URL")
    public String URL;

     public RecipeSearched(int id, String title, String summary, String URL){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.URL = URL;
    }
}

///information?apiKey=432da05c987f4b7fab39e7a708c0de77