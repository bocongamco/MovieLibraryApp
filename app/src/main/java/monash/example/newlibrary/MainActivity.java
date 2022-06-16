package monash.example.newlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import monash.example.newlibrary.provider.Library;
import monash.example.newlibrary.provider.LibraryViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav;
    FloatingActionButton fab;
    ListView listView;

    View box;
    GestureDetector gestureDetector;

    FirebaseDatabase database;
    DatabaseReference ref1;

    private EditText editTitle;
    private EditText yearText;
    private EditText countryText;
    private EditText genreText;
    private EditText costText;
    private EditText keywordsText;
    private Button addMovieButton;
    private Button clearAllbutton;

    //private Button doubleCost;
    //private Button deepClean;

    public static final String SEND_TO_ANOTHER_ACTIVITY = "send_to_another_activity";

    private static final String TITLE_KEY = "title";
    private static final String YEAR_KEY = "year";
    private static final String COUNTRY_KEY = "country";
    private static final String GENRE_KEY = "genre";
    private static final String COST_KEY = "cost";
    private static final String KEYWORDS_KEY = "keywords";


    private static final String SHARED_PREFS = "sharePreferences";

    SharedPreferences sP;
    private String title;
    private String year;
    private String country;
    private String genre;
    private String cost;
    private String keywords;


    ArrayList<String> newArray;
    ArrayAdapter<String> arrayAdap;

    private LibraryViewModel viewModel;

    ArrayList<Library> libraries= new ArrayList<Library>();

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState ) {
        super.onSaveInstanceState(outState);
        String enterName = editTitle.getText().toString();
        String genre = genreText.getText().toString().toLowerCase();
        outState.putString(TITLE_KEY,enterName);
        outState.putString(GENRE_KEY,genre);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instate){
        super.onRestoreInstanceState(instate);
        instate.getString(TITLE_KEY);
        instate.getString(GENRE_KEY);
        genreText.setText(instate.getString(GENRE_KEY));
        editTitle.setText(instate.getString(TITLE_KEY).toUpperCase());

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(TITLE_KEY,editTitle.getText().toString());
        editor.putString(YEAR_KEY,yearText.getText().toString());
        editor.putString(COUNTRY_KEY,countryText.getText().toString());
        editor.putString(GENRE_KEY,genreText.getText().toString());
        editor.putString(COST_KEY,costText.getText().toString());
        editor.putString(KEYWORDS_KEY,keywordsText.getText().toString());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        title = sharedPreferences.getString(TITLE_KEY," ");
        year = sharedPreferences.getString(YEAR_KEY," ");
        country = sharedPreferences.getString(COUNTRY_KEY," ");
        genre = sharedPreferences.getString(GENRE_KEY," ");
        cost = sharedPreferences.getString(COST_KEY, " ");
        keywords = sharedPreferences.getString(KEYWORDS_KEY," ");

    }

    public void updateView() {
        editTitle.setText(title);
        yearText.setText(year);
        countryText.setText(country);
        genreText.setText(genre);
        costText.setText(cost);
        keywordsText.setText(keywords);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_id);

        toolbar =findViewById(R.id.toolbar_id);
        drawerLayout= findViewById(R.id.drawer_layout_id);
        nav = findViewById(R.id.nav_id);
        fab= findViewById(R.id.floatingActionButton2);
        listView = findViewById(R.id.listview_id);

        //initialise
        editTitle = findViewById(R.id.editTextTextPersonName);// connect the edit text with the layout, by references its id
        yearText = findViewById(R.id.editTextNumber);
        countryText = findViewById(R.id.editTextTextPersonName2);
        genreText = findViewById(R.id.editTextTextPersonName3);
        costText = findViewById(R.id.editTextNumber2);
        keywordsText = findViewById(R.id.editTextTextMultiLine);
        addMovieButton = findViewById(R.id.button);
        clearAllbutton = findViewById(R.id.button2);

        gestureDetector = new GestureDetector(this,new LibraryHandler());

        box = findViewById(R.id.view);
        box.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                /*float x =0;
                float y =0;
                float travelX=0;
                float travelY=0;

                int action = motion.getActionMasked();
                    switch(action){
                        case(MotionEvent.ACTION_DOWN):
                            x = motion.getX();
                            y =  motion.getY();
                            return true;
                        case(MotionEvent.ACTION_MOVE):
                            return true;
                        case(MotionEvent.ACTION_UP):
                            travelX = motion.getX()-x;
                            travelY  = motion.getY()-y;
                            if(Math.abs(travelX)>Math.abs(travelY)){
                                if(travelX>0){
                                    Toast.makeText(MainActivity.this,"right",Toast.LENGTH_SHORT).show();

                                    addMovie();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"left",Toast.LENGTH_SHORT).show();

                                    addMovie();
                                }
                            }
                            else{
                                if(travelY>0){
                                    Toast.makeText(MainActivity.this,"Movie Clear",Toast.LENGTH_SHORT).show();
                                    clearField();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Movie Clear",Toast.LENGTH_SHORT).show();
                                    clearField();
                                }

                            }

                            return true;
                        default:
                            return false;

                    }
                }*/
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });



        //close and open drawer when click on element
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(toogle); // add this toogle to the listener
        toogle.syncState();
        nav.setNavigationItemSelectedListener(this);

        //initialise array
        newArray = new ArrayList<>();
        arrayAdap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,newArray);
        listView.setAdapter(arrayAdap);

        viewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        viewModel.getMovies().observe(this,newData->{
            libraries= (ArrayList<Library>) newData;
        });



        //function set on click listener for some few button
        clearAllbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearField();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();
            }
        });

        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie();

            }
        });
        loadData();
        updateView();



        database = FirebaseDatabase.getInstance();
        ref1= database.getReference().child("/Week8Library");


    }

    class LibraryHandler extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int costInc = Integer.valueOf(costText.getText().toString())+150;
            costText.setText(String.valueOf(costInc));
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            editTitle.setText("Harry Potter");
            yearText.setText("2020");
            countryText.setText("USA");
            genreText.setText("Action");
            costText.setText("700");
            keywordsText.setText("Drama,fiction");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            String year = yearText.getText().toString();
            if(!year.isEmpty()){
                int value = Integer.valueOf(year) + Math.round(distanceX);
                yearText.setText(String.valueOf(value));
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            moveTaskToBack(true);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            clearField();
        }
    }
    public void clearField(){
        editTitle.setText("");
        yearText.setText("");
        countryText.setText("");
        genreText.setText("");
        costText.setText("");
        keywordsText.setText("");
    }

    public void addMovie(){

        String getName = editTitle.getText().toString();
        String getYear = yearText.getText().toString();
        String getCountry = countryText.getText().toString();
        String getGenre = genreText.getText().toString();
        String getCost = costText.getText().toString();
        String getKeywords = keywordsText.getText().toString();

        String moveSpec = getName + " | " + getYear ;


        Library movies = new Library(getName,getYear,getCountry,getGenre,getCost,getKeywords);

        libraries.add(movies);

        viewModel.insertMovies(movies);

        //tho under is from w5 activity
        newArray.add(moveSpec);
        arrayAdap.notifyDataSetChanged();

        Toast.makeText(MainActivity.this,"Movie: " +getName  + " has been added",Toast.LENGTH_SHORT).show();
        saveData();
        ref1.push().setValue(movies);



    }

    public void removeLastMovie(){
        if(libraries.size()>0){
            newArray.remove(newArray.size()-1);
            arrayAdap.notifyDataSetChanged();

            //
            viewModel.deleteMovies(libraries.get(libraries.size()-1));

        }

    }

    public void removeAllMovie(){
        newArray.clear();
        arrayAdap.notifyDataSetChanged();
        viewModel.deleteAllMovies();

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref1= database.getReference();

        ref1.push().setValue(null);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.options_menu_clear:
                clearField();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch(id){
            case R.id.drl_add_movie:
                addMovie();
                break;
            case R.id.drl_remove_last_movie:
                removeLastMovie();
                break;
            case R.id.drl_remove_all_movie:
                removeAllMovie();
                break;
            case R.id.drl_list_all_movie:
                Intent intent = new Intent(this,RecyclerActivity.class);
                intent.putExtra(SEND_TO_ANOTHER_ACTIVITY,libraries);
                startActivity(intent);
                break;
        }


        drawerLayout.closeDrawer(GravityCompat.START);// Can close from left to right
        return true;
    }

    private void showSnackbar(){
        
    }

}