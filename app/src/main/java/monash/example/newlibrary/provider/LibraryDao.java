package monash.example.newlibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LibraryDao {
    @Query("SELECT * FROM libraries")
    LiveData<List<Library>> getAllMovies();

    @Insert
    void addMovies(Library library);

    @Delete
    void deleteMovies(Library library);

    @Query("Delete From libraries ")
    void deleteAllMovies();
}
