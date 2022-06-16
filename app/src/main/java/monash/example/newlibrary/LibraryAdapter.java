package monash.example.newlibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import monash.example.newlibrary.provider.Library;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {
    ArrayList<Library> ds= new ArrayList<Library>();

    public LibraryAdapter(ArrayList<Library> ds) {
        this.ds = ds;
    }




    @NonNull
    @Override
    public LibraryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        MyViewHolder holder = new MyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryAdapter.MyViewHolder holder, int position) {
        Library libraries = ds.get(position);
        holder.movieTitle.setText(libraries.getTitle());
        holder.movieYear.setText(libraries.getYear());
        holder.movieCountry.setText(libraries.getCountry());
        holder.movieGenre.setText(libraries.getGenre());
        holder.movieCost.setText(libraries.getCost());
        holder.movieKeyword.setText(libraries.getKeywords());
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView movieYear;
        TextView movieCountry;
        TextView movieGenre;
        TextView movieCost;
        TextView movieKeyword;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.title_card_id);
            movieYear = itemView.findViewById(R.id.year_card_id);
            movieCountry = itemView.findViewById(R.id.country_card_id);
            movieGenre = itemView.findViewById(R.id.genre_card_id);
            movieCost = itemView.findViewById(R.id.cost_card_id);
            movieKeyword = itemView.findViewById(R.id.keywords_card_id);
        }
    }
}
