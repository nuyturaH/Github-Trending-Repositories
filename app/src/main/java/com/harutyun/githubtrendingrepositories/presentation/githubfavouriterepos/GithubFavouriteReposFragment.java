package com.harutyun.githubtrendingrepositories.presentation.githubfavouriterepos;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.harutyun.domain.models.GithubRepo;
import com.harutyun.githubtrendingrepositories.R;
import com.harutyun.githubtrendingrepositories.databinding.FragmentGithubFavouriteReposBinding;
import com.harutyun.githubtrendingrepositories.presentation.githubrepodetails.GithubRepoDetailsReposViewModel;
import com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters.GithubReposHeaderAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GithubFavouriteReposFragment extends Fragment {

    private FragmentGithubFavouriteReposBinding mBinding;
    private GithubRepoDetailsReposViewModel mGithubRepoDetailsReposViewModel;
    private GithubFavouriteReposViewModel mGithubFavouriteReposViewModel;

    private GithubFavouriteReposAdapter mGithubReposAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGithubFavouriteReposBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupToolbar();

        initViewModel();

        setupTrendingRepositoriesRecyclerView();

        setupObservables();

        setupViewListeners();

    }

    private void setupViewListeners() {
        mBinding.etSearchFavouriteRepos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();

                mGithubFavouriteReposViewModel.searchRepos(input);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupObservables() {
        mGithubFavouriteReposViewModel.getFavouriteReposLiveData().observe(getViewLifecycleOwner(), githubRepos -> {
            mGithubReposAdapter.submitList(githubRepos);
        });

        mGithubFavouriteReposViewModel.getNoDataLiveData().observe(getViewLifecycleOwner(), hasNoData -> {
            mBinding.tvNoDataFavouriteRepos.setVisibility(hasNoData ? View.VISIBLE : View.GONE);
            mBinding.rvFavouriteRepos.setVisibility(hasNoData ? View.GONE : View.VISIBLE);
        });

        mGithubFavouriteReposViewModel.getCompletedLiveData().observe(getViewLifecycleOwner(), isCompleted -> {
            if (isCompleted) {
                Snackbar.make(mBinding.getRoot(), getString(R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();
                mGithubFavouriteReposViewModel.setCompletedMutableLiveData(false);
            }
        });

        mGithubFavouriteReposViewModel.getFailureMessageLiveData().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Snackbar.make(mBinding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
                mGithubFavouriteReposViewModel.setCompletedMutableLiveData(null);
            }
        });
    }

    private void setupTrendingRepositoriesRecyclerView() {

        mGithubReposAdapter = new GithubFavouriteReposAdapter(new GithubFavouriteReposAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(GithubRepo githubRepo) {
                mGithubRepoDetailsReposViewModel.setCurrentRepoMutableLiveData(githubRepo);
                NavHostFragment.findNavController(GithubFavouriteReposFragment.this).navigate(R.id.action_githubFavouriteReposFragment_to_githubRepoDetailsFragment);
            }

            @Override
            public void onFavouriteClicked(GithubRepo githubRepo) {
                mGithubFavouriteReposViewModel.removeFavouriteRepoFromLocalDb(githubRepo);
            }
        });
        GithubReposHeaderAdapter githubReposHeaderAdapter = new GithubReposHeaderAdapter(getString(R.string.github_favourite_repositories));
        ConcatAdapter concatAdapter = new ConcatAdapter(githubReposHeaderAdapter, mGithubReposAdapter);
        mBinding.rvFavouriteRepos.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.rvFavouriteRepos.setAdapter(concatAdapter);
    }

    private void initViewModel() {
        mGithubRepoDetailsReposViewModel = new ViewModelProvider(requireActivity()).get(GithubRepoDetailsReposViewModel.class);
        mGithubFavouriteReposViewModel = new ViewModelProvider(this).get(GithubFavouriteReposViewModel.class);
    }


    private void setupToolbar() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(mBinding.tbFavouriteRepos, navController, appBarConfiguration);
    }
}