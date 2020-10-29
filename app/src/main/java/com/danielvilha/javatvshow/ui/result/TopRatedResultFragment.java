package com.danielvilha.javatvshow.ui.result;

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

import com.danielvilha.javatvshow.resources.URLs;
import com.danielvilha.javatvshow.models.TopRated;
import com.danielvilha.javatvshow.ui.MainActivity;
import com.danielvilha.javatvshow.R;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by danielvilha on 22/10/20
 * https://github.com/danielvilha
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRatedResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedResultFragment extends Fragment {
    public static String TAG = TopRatedResultFragment.class.getSimpleName();
    private static final String PARAM = "param";

    private TopRated.TopRatedResult result;

    public TopRatedResultFragment() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param result TvShow.TvShowItem.
     * @return A new instance of fragment TvShowFragment.
     */
    public static TopRatedResultFragment newInstance(TopRated.TopRatedResult result) {
        TopRatedResultFragment fragment = new TopRatedResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_rated_result, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            result = (TopRated.TopRatedResult) getArguments().getSerializable(PARAM);
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) Objects.requireNonNull(getActivity())).toolbar.setTitle(getString(R.string.detail));

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

        Picasso.get().load(URLs.RESULT_URL + result.poster_path).into(poster_path);
        id.setText(result.id.toString());
        name.setText(result.name);
        original_name.setText(result.original_name);
        popularity.setText(String.format("%s: %s", getString(R.string.popularity), result.popularity));
        vote_count.setText(String.format("%s: %s", getString(R.string.vote_count), result.vote_count));
        vote_average.setText(String.format("%s: %s", getString(R.string.vote_average), result.vote_average));

        StringBuilder genres = new StringBuilder();
        for (String genre : result.genre_ids) {
            genres.append(genre).append(",");
        }
        genre_ids.setText(String.format("%s: %s", getString(R.string.genre), genres.toString()));

        LocalDate dateTime = LocalDate.parse(result.first_air_date);
        first_air_date.setText(String.format("%s/%s/%s", dateTime.getDayOfMonth(), dateTime.getMonthValue(), dateTime.getYear()));
        origin_country.setText(result.origin_country.get(0));
        overview.setText(result.overview);
    }

    @Override
    public void onPrepareOptionsMenu(@androidx.annotation.NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.sort_button);
        if (item != null) {
            item.setVisible(false);
        }
    }
}