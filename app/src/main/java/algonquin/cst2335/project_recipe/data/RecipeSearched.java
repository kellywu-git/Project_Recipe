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
    @ColumnInfo(name="author")
    public String author;

    @ColumnInfo(name="summary")
    public String summary;

    @ColumnInfo(name="date")
    public String date;

    @ColumnInfo(name="URL")
    public String URL;

     public RecipeSearched(int id, String title, String author, String summary, String date, String URL){
        this.id = id;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.date = date;
        this.URL = URL;
    }
}
