package monash.example.newlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import monash.example.newlibrary.provider.Library;

public class RecyclerActivity extends AppCompatActivity {
    ArrayList<Library> dataSource= new ArrayList<>() ;


    LibraryAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        dataSource = (ArrayList<Library>) getIntent().getSerializableExtra(MainActivity.SEND_TO_ANOTHER_ACTIVITY);
        adapter = new LibraryAdapter(dataSource);
        recyclerView.setAdapter(adapter);

    }
}