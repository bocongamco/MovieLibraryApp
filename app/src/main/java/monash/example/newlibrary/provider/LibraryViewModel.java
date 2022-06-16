package monash.example.newlibrary.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {

    private LibraryRepository repo;
    private LiveData<List<Library>> libraries;

    public LibraryViewModel(@NonNull Application application) {
        super(application);
        repo = new LibraryRepository(application);
        libraries = repo.getAllMovies();
    }
    public LiveData<List<Library>> getMovies(){
        return libraries;

    }
    public void insertMovies(Library library){
        repo.insertMovies(library);
    }

    public void deleteMovies(Library library){
        repo.deleteMovies(library);
    }
    public void deleteAllMovies(){
        repo.deleteAllMovies();
    }
}
