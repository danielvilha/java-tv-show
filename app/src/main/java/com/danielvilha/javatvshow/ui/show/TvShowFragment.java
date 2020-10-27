package com.danielvilha.javatvshow.ui.show;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielvilha.javatvshow.ui.MainActivity;
import com.danielvilha.javatvshow.R;
import com.danielvilha.javatvshow.object.TvShow;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

/**
 * Created by danielvilha on 22/10/20
 * https://github.com/danielvilha
 * A simple {@link Fragment} subclass.
 * Use the {@link TvShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TvShowFragment extends Fragment {
    public static String TAG = TvShowFragment.class.getSimpleName();
    private static final String PARAM = "param";

    private TvShow.TvShowItem tvShowItem;

    public TvShowFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param showItem TvShow.TvShowItem.
     * @return A new instance of fragment TvShowFragment.
     */
    public static TvShowFragment newInstance(TvShow.TvShowItem showItem) {
        TvShowFragment fragment = new TvShowFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM, showItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tvShowItem = (TvShow.TvShowItem) getArguments().getSerializable(PARAM);
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).toolbar.setTitle(getString(R.string.detail));

        ImageView poster_path = getActivity().findViewById(R.id.poster_path);
        TextView id = getActivity().findViewById(R.id.id);
        TextView name = getActivity().findViewById(R.id.name);
        TextView original_name = getActivity().findViewById(R.id.original_name);
        TextView popularity = getActivity().findViewById(R.id.popularity);
        TextView vote_count = getActivity().findViewById(R.id.vote_count);
        TextView vote_average = getActivity().findViewById(R.id.vote_average);
        TextView genre_ids = getActivity().findViewById(R.id.genre_ids);
        TextView first_air_date = getActivity().findViewById(R.id.first_air_date);
        TextView origin_country = getActivity().findViewById(R.id.origin_country);
        TextView overview = getActivity().findViewById(R.id.overview);

        Picasso.get().load("https://image.tmdb.org/t/p/w220_and_h330_face" + tvShowItem.poster_path).into(poster_path);
        id.setText(tvShowItem.id.toString());
        name.setText(tvShowItem.name);
        original_name.setText(tvShowItem.original_name);
        popularity.setText(String.format("%s: %s", getString(R.string.popularity), tvShowItem.popularity));
        vote_count.setText(String.format("%s: %s", getString(R.string.vote_count), tvShowItem.vote_count));
        vote_average.setText(String.format("%s: %s", getString(R.string.vote_average), tvShowItem.vote_average));

        StringBuilder genres = new StringBuilder();
        for (String genre : tvShowItem.genre_ids) {
            genres.append(genre).append(",");
        }
        genre_ids.setText(String.format("%s: %s", getString(R.string.genre), genres.toString()));

        LocalDate dateTime = LocalDate.parse(tvShowItem.first_air_date);
        first_air_date.setText(String.format("%s/%s/%s", dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear()));
        origin_country.setText(tvShowItem.origin_country.get(0));
        overview.setText(tvShowItem.overview);
    }

    @Override
    public void onPrepareOptionsMenu(@androidx.annotation.NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.sort_button);
        if (item != null) {
            item.setVisible(false);
        }
    }
}