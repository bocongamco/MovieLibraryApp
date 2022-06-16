package monash.example.newlibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LibraryRepository {

    private LibraryDao libraryDao;
    private LiveData<List<Library>> allMovies;
    LibrariesDatabase db;

    public LibraryRepository(Application application) {
        db = LibrariesDatabase.getLibraryDB(application);
        libraryDao =  db.libraryDao();
        allMovies = db.libraryDao().getAllMovies();
    }

    LiveData<List <Library>> getAllMovies(){
        return allMovies;
    }

    void insertMovies(Library library){
        LibrariesDatabase.dbWriter.execute(()->{libraryDao.addMovies(library);});
    }

    void deleteMovies(Library library){
        LibrariesDatabase.dbWriter.execute(()->{libraryDao.deleteMovies(library);});
    }

    void deleteAllMovies(){
        LibrariesDatabase.dbWriter.execute(()->{libraryDao.deleteAllMovies();});
    }
}
