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
public class RecipePhoto
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

   @ColumnInfo(name="pid")
   public String pid;
    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="summary")
    public String summary;

   @ColumnInfo(name="URL")
    public String URL;

    /**
     * constructors - with no parameters.
     */
    public RecipePhoto()
    {

    }
    /**
     * constructors takes four parameters corresponding to the fields defined in above class.
     * @param id recipe id in string
     * @param summary recipe summary
     * @param title recipe title
     * @param URL  Spoonacular URL
     */
     public RecipePhoto(String id, String title, String summary, String URL){
   //     this.id = id;
       this.pid = id;
        this.title = title;
        this.summary = summary;
        this.URL = URL;
    }
//    public String getPid(){
//        return this.pid;
//    }
//    public String getTitle(){
//        return this.title;
//    }
//    public String getSummary(){
//        return this.summary;
//    }
//    public String getURL(){
//        return this.URL;
//    }
}

///information?apiKey=432da05c987f4b7fab39e7a708c0de77