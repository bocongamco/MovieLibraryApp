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

public class LoginScreen extends AppCompatActivity {
    EditText userName,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        userName = findViewById(R.id.UserName);
        password  =findViewById(R.id.UserPassword);
        login = findViewById(R.id.LoginBut);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameString = userName.getText().toString();
                String passwordString = password.getText().toString();
                if(userNameString.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter user name", Toast.LENGTH_SHORT).show();
                }
                if(passwordString.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT).show();
                }else{
                    UserDatabase userLogin = UserDatabase.getUserDB(getApplicationContext());
                    UserDao userDao = userLogin.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = userDao.login(userNameString,passwordString);
                            if(user == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Your name or your password is not correct, Please check again!",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else{
                                startActivity(new Intent(LoginScreen.this,MainActivity.class));
                            }
                        }
                    }).start();
                }
            }
        });
    }
}