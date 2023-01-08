package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.harutyun.githubtrendingrepositories.databinding.FragmentGithubTrendingReposBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GithubTrendingReposFragment extends Fragment {

    private FragmentGithubTrendingReposBinding mBinding;
    private GithubTrendingReposViewModel mGithubTrendingReposViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGithubTrendingReposBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mGithubTrendingReposViewModel = new ViewModelProvider(this).get(GithubTrendingReposViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}