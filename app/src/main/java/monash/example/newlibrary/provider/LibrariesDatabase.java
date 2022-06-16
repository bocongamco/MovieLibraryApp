package monash.example.newlibrary.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Library.class},version = 1)

public abstract class LibrariesDatabase extends RoomDatabase {
    public static final String DB_NAME = "Library_DB_NAME";

    public abstract LibraryDao libraryDao();
    private static volatile LibrariesDatabase instance;

    static final ExecutorService dbWriter = Executors.newFixedThreadPool(4);

    public static LibrariesDatabase getLibraryDB(Context context){
        if(instance==null){

            synchronized (LibrariesDatabase.class){
                if(instance== null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), LibrariesDatabase.class, DB_NAME).build();
                }
            }

        }

        return instance;
    }
}
