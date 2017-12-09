package com.apollo.andorid.apollosearch.view.base;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chulwoo on 2017. 12. 9..
 */

public abstract class BaseFragment extends Fragment {

    protected void bind(@NonNull Disposable disposable) {
        if (Lifecycle.State.CREATED.equals(getLifecycle().getCurrentState())) {
            if (onStartDisposables != null) {
                onStartDisposables.add(disposable);
            }
        } else {
            if (onResumeDisposables != null) {
                onResumeDisposables.add(disposable);
            }
        }

    }

    private CompositeDisposable onStartDisposables;
    private CompositeDisposable onResumeDisposables;

    @LayoutRes
    protected abstract int getLayoutResource();

    @Override
    public void onStart() {
        super.onStart();
        onStartDisposables = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeDisposables = new CompositeDisposable();
    }

    @Override
    public void onPause() {
        if (onResumeDisposables != null) {
            onResumeDisposables.clear();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (onStartDisposables != null) {
            onStartDisposables.clear();
        }
        super.onStop();
    }
}
