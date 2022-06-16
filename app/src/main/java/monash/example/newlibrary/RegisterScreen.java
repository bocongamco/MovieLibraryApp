package monash.example.newlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import monash.example.newlibrary.UserLogin.User;
import monash.example.newlibrary.UserLogin.UserDao;
import monash.example.newlibrary.UserLogin.UserDatabase;

public class RegisterScreen extends AppCompatActivity {
    EditText userName,password,email;

    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        userName = findViewById(R.id.UserName);
        password = findViewById(R.id.UserPassword);
        email = findViewById(R.id.UserEmail);
        register = findViewById(R.id.RegisterBut);
        login = findViewById(R.id.LoginBut);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userRegis = new User(userName.getText().toString(),password.getText().toString(),email.getText().toString());

               // Toast.makeText(getApplicationContext(),"User: "+userRegis.getUserName(),Toast.LENGTH_SHORT).show();

                if(checkInput(userRegis)){

                    UserDatabase userDatabase = UserDatabase.getUserDB(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            userDao.registerUser(userRegis);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"User has been registered",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill all field",Toast.LENGTH_SHORT).show();
                }


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterScreen.this,LoginScreen.class));
            }
        });


    }
    private Boolean checkInput(User user){

        if(user.getPassword().isEmpty()){
            Toast.makeText(getApplicationContext(),"Password is missing",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getUserName().isEmpty()){
            Toast.makeText(getApplicationContext(),"User name is missing",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(user.getEmail().isEmpty()){
            Toast.makeText(getApplicationContext(),"Email is missing",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}