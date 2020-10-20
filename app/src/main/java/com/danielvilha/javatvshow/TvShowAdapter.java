package com.danielvilha.javatvshow;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielvilha.javatvshow.object.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * {@link RecyclerView.Adapter} that can display a {@link TvShow.TvShowItem}.
 */
public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private final ArrayList<TvShow.TvShowItem> mValues;

    public TvShowAdapter(ArrayList<TvShow.TvShowItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position).name);
        Picasso.get().load("https://image.tmdb.org/t/p/w94_and_h141_bestv2" + mValues.get(position).poster_path).into(holder.poster_path);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final ImageView poster_path;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            poster_path = (ImageView) view.findViewById(R.id.poster_path);
        }
    }
}