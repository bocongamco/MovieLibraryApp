package monash.example.newlibrary.UserLogin;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class},version=1)
public abstract class UserDatabase extends RoomDatabase {

    public static final String DB_USER = "USER_LOGIN";

    private static volatile UserDatabase instance;

    static final ExecutorService dbWriter = Executors.newFixedThreadPool(4);

    public static UserDatabase getUserDB(Context context){
        if(instance==null){
            synchronized (UserDatabase.class){
                if(instance== null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DB_USER).fallbackToDestructiveMigration().build();
                }
            }

        }

        return instance;
    }
    public abstract UserDao userDao();

}
