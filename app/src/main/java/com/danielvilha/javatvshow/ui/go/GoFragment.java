package com.danielvilha.javatvshow.ui.go;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.danielvilha.javatvshow.ui.MainActivity;
import com.danielvilha.javatvshow.R;
import com.danielvilha.javatvshow.ui.shows.TvShowsFragment;

/**
 * Created by danielvilha on 18/10/20
 * https://github.com/danielvilha
 */
public class GoFragment extends Fragment {
    public static String TAG = GoFragment.class.getSimpleName();

    public GoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_go, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).toolbar.setTitle(getString(R.string.app_name));

        Button buttonGo = getActivity().findViewById(R.id.button_go);
        buttonGo.setOnClickListener(view -> {
            Fragment newFragment = new TvShowsFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(TvShowsFragment.TAG);
            transaction.commit();
        });
    }

    @Override
    public void onPrepareOptionsMenu(@androidx.annotation.NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.sort_button);
        if (item != null) {
            item.setVisible(false);
        }
    }
}