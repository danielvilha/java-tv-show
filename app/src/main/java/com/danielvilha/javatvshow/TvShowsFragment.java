package com.danielvilha.javatvshow;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.danielvilha.javatvshow.object.TvShow;
import com.danielvilha.javatvshow.service.ApiService;
import com.danielvilha.javatvshow.service.RetrofitBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 */
public class TvShowsFragment extends Fragment {
    public static String TAG = TvShowsFragment.class.getSimpleName();

    private ProgressBar progressBar;
    private static RecyclerView recycler;
    private static TvShowAdapter adapter;

    private static TvShow item;
    public static Boolean isSort = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TvShowsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_shows, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ((MainActivity) getActivity()).toolbar.setTitle(getString(R.string.app_name));

        progressBar = getActivity().findViewById(R.id.progressBar);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recycler = getActivity().findViewById(R.id.recycler);
        recycler.setLayoutManager(layoutManager);

        // create an instance of the ApiService
        ApiService apiService = new RetrofitBuilder().retrofit().create(ApiService.class);

        // make a request by calling the corresponding method
        Observable<TvShow> service = apiService.getMovie();
        service
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TvShow>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull TvShow tvShow) {
                        item = tvShow;

                        if (!item.results.isEmpty()) {
                            Comparator<TvShow.TvShowItem> compareByName = (TvShow.TvShowItem tv1, TvShow.TvShowItem tv2) -> tv1.name.compareTo(tv2.name);
                            Collections.sort(item.results, compareByName);

                            adapter = new TvShowAdapter((ArrayList<TvShow.TvShowItem>) item.results, getActivity());
                            recycler.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onPrepareOptionsMenu(@androidx.annotation.NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.sort_button);
        if (item != null) {
            item.setVisible(true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickMenu() {
        if (item != null) {
            if (isSort) {
                Comparator<TvShow.TvShowItem> compareByName = (TvShow.TvShowItem m1, TvShow.TvShowItem m2) -> m1.name.compareTo(m2.name);
                Collections.sort(item.results, compareByName);

                adapter = new TvShowAdapter((ArrayList<TvShow.TvShowItem>) item.results, getActivity());
                recycler.setAdapter(adapter);
                isSort = false;
            } else {
                Comparator<TvShow.TvShowItem> compareByName = (TvShow.TvShowItem m1, TvShow.TvShowItem m2) -> m1.name.compareTo(m2.name);
                Collections.sort(item.results, compareByName.reversed());

                adapter = new TvShowAdapter((ArrayList<TvShow.TvShowItem>) item.results, getActivity());
                recycler.setAdapter(adapter);
                isSort = true;
            }
        }
    }
}