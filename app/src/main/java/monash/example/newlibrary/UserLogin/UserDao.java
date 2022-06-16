package monash.example.newlibrary.UserLogin;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    //regis user
    @Insert
    void registerUser(User user);

    @Query("SELECT * FROM users WHERE userName =(:userName) and password= (:password)")
    User login(String userName,String password);


}
