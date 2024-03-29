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
public class Recipe
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int ID;

   @ColumnInfo(name="id")
   public String id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="Thumbnail")
    public String Thumbnail;

      /**
     * constructors - with no parameters.
     */
    public Recipe()
    {

    }

//     public Recipe(String id, String title, String summary, String URL){
//   //     this.id = id;
//       this.recipeId = id;
//        this.title = title;
//        this.summary = summary;
//        this.URL = URL;
//    }


    public Recipe(String id, String title, String thumbnail) {
        this.id = id;
        this. title = title;
        Thumbnail = thumbnail;

         }



    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

       @Override
    public String toString() {
        return getTitle();
    }
}
///information?apiKey=432da05c987f4b7fab39e7a708c0de77