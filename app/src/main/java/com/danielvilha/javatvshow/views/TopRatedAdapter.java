package com.danielvilha.javatvshow.views;

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

import com.danielvilha.javatvshow.R;
import com.danielvilha.javatvshow.models.TopRated;
import com.danielvilha.javatvshow.ui.result.TopRatedResultFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 * {@link RecyclerView.Adapter} that can display a {@link TopRated.TopRatedResult}.
 */
public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder> {

    private final ArrayList<TopRated.TopRatedResult> mResult;
    private final FragmentActivity mActivity;

    public TopRatedAdapter(ArrayList<TopRated.TopRatedResult> items, FragmentActivity activity) {
        mResult = items;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_rated, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mResult.get(position).name);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + mResult.get(position).poster_path).into(holder.poster_path);

        holder.itemView.setOnClickListener(view -> {
            TopRated.TopRatedResult item = mResult.get(position);

            Fragment newFragment = TopRatedResultFragment.newInstance(item);
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(TopRatedResultFragment.TAG);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final ImageView poster_path;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.name);
            poster_path = view.findViewById(R.id.poster_path);
        }
    }
}