package com.danielvilha.javatvshow.ui.items;

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

import com.danielvilha.javatvshow.ui.MainActivity;
import com.danielvilha.javatvshow.R;
import com.danielvilha.javatvshow.views.TopRatedAdapter;
import com.danielvilha.javatvshow.models.TopRated;
import com.danielvilha.javatvshow.service.ApiService;
import com.danielvilha.javatvshow.service.RetrofitBuilder;

import java.util.Collections;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 */
public class TopRatedItemsFragment extends Fragment {
    public static String TAG = TopRatedItemsFragment.class.getSimpleName();

    private ProgressBar progressBar;
    private static RecyclerView recycler;
    private static TopRatedAdapter adapter;

    private static Integer page = 1;
    private static TopRated topRated;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopRatedItemsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_rated_items, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ((MainActivity) Objects.requireNonNull(getActivity())).toolbar.setTitle(getString(R.string.app_name));

        progressBar = getActivity().findViewById(R.id.progressBar);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recycler = getActivity().findViewById(R.id.recycler);
        recycler.setLayoutManager(layoutManager);

        getTopRatedRecyclerView(page);

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@androidx.annotation.NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    getTopRatedRecyclerView(page);
                }
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

    private void getTopRatedRecyclerView(int pageNumber) {
        // create an instance of the ApiService
        ApiService apiService = new RetrofitBuilder().retrofit().create(ApiService.class);

        // make a request by calling the corresponding method
        Observable<TopRated> service = apiService.getTopRated(pageNumber);
        service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TopRated>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(@NonNull TopRated rated) {
                        page = page + 1;
                        if (topRated == null || topRated.results == null) {
                            topRated = rated;

                            adapter = new TopRatedAdapter(topRated.results, getActivity());
                            recycler.setAdapter(adapter);
                        } else {
                            int curSize = adapter.getItemCount();
                            topRated.results.addAll(rated.results);
                            adapter.notifyItemRangeInserted(curSize, rated.results.size());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        page = 1;
        topRated = null;
        adapter = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortButtonClick() {
        if (topRated != null) {
            Collections.sort(topRated.results);
            adapter.notifyDataSetChanged();
        }
    }
}