package com.harutyun.githubtrendingrepositories.presentation.githubfavouriterepos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        initViewModel();

        setupTrendingRepositoriesRecyclerView();

        setupObservables();

    }

    private void setupObservables() {
        mGithubFavouriteReposViewModel.getFavouriteReposLiveData().observe(getViewLifecycleOwner(), githubRepos -> {
            mGithubReposAdapter.submitList(githubRepos);
        });

        mGithubFavouriteReposViewModel.getNoDataLiveData().observe(getViewLifecycleOwner(), hasNoData -> {
            mBinding.tvNoDataFavouriteRepos.setVisibility(hasNoData ? View.VISIBLE : View.GONE);
        });
    }

    private void setupTrendingRepositoriesRecyclerView() {

        mGithubReposAdapter = new GithubFavouriteReposAdapter(new GithubFavouriteReposAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(GithubRepo githubRepo) {
                mGithubRepoDetailsReposViewModel.setCurrentRepoMutableLiveData(githubRepo);
                NavHostFragment.findNavController(GithubFavouriteReposFragment.this).navigate(R.id.action_githubTrendingReposFragment_to_githubRepoDetailsFragment);
            }

            @Override
            public void onFavouriteClicked(GithubRepo githubRepo) {
                
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
}