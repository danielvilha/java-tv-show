package com.danielvilha.javatvshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.danielvilha.javatvshow.object.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 * {@link RecyclerView.Adapter} that can display a {@link TvShow.TvShowItem}.
 */
public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private final ArrayList<TvShow.TvShowItem> mValues;
    private final FragmentActivity mActivity;

    public TvShowAdapter(ArrayList<TvShow.TvShowItem> items, FragmentActivity activity) {
        mValues = items;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_show, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position).name);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + mValues.get(position).poster_path).into(holder.poster_path);

        holder.itemView.setOnClickListener(view -> {
            TvShow.TvShowItem item = mValues.get(position);

            Fragment newFragment = TvShowFragment.newInstance(item);
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(TvShowFragment.TAG);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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