package algonquin.cst2335.project_recipe.data;

import androidx.annotation.NonNull;
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
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="summary")
    public String summary;

   @ColumnInfo(name="SourceUrl")
    public String sourceUrl;

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
       this.id = id;
        this.title = title;
        this.summary = summary;
        this.sourceUrl = sourceUrl;
    }

    public RecipePhoto(String id, String title, String image) {
    }

    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public String getSummary(){
        return this.summary;
    }
    public String getSourceUrl(){
        return this.sourceUrl;
    }
}

///information?apiKey=432da05c987f4b7fab39e7a708c0de77