package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import static com.harutyun.githubtrendingrepositories.helper.UiHelper.hideKeyboard;
import static autodispose2.AutoDispose.autoDisposable;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.harutyun.data.remote.NoNetworkConnectionException;
import com.harutyun.githubtrendingrepositories.R;
import com.harutyun.githubtrendingrepositories.databinding.FragmentGithubTrendingReposBinding;
import com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters.GithubReposAdapter;
import com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters.GithubReposHeaderAdapter;
import com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.adapters.GithubReposLoadStateAdapter;

import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GithubTrendingReposFragment extends Fragment {

    private FragmentGithubTrendingReposBinding mBinding;
    private GithubTrendingReposViewModel mGithubTrendingReposViewModel;
    private GithubReposAdapter mGithubReposAdapter;
    private DateRange mCurrentDateRange = DateRange.LAST_DAY;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentGithubTrendingReposBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();

        setupTrendingRepositoriesRecyclerView();

        setupViewListeners();

        setupObservables();

        getGithubTrendingRepos(DateRange.LAST_DAY);
    }

    private void setupObservables() {
        mGithubTrendingReposViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            mBinding.srlTrendingRepos.setRefreshing(isLoading);
        });


        mGithubTrendingReposViewModel.getErrorMessageLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                mBinding.rvTrendingRepos.setVisibility(View.GONE);
                mBinding.tvErrorTrendingRepos.setVisibility(View.VISIBLE);
                mBinding.tvErrorTrendingRepos.setText(String.format(getString(R.string.swipe_down_refresh), errorMessage));
            } else {
                mBinding.rvTrendingRepos.setVisibility(View.VISIBLE);
                mBinding.tvErrorTrendingRepos.setVisibility(View.GONE);
            }
        });

        mGithubTrendingReposViewModel.getNoDataLiveData().observe(getViewLifecycleOwner(), isNoData -> {
            mBinding.tvNoDataTrendingRepos.setVisibility(isNoData ? View.VISIBLE : View.GONE);
        });
    }

    private void setupViewListeners() {
        // Swipe refresh layout
        mBinding.srlTrendingRepos.setOnRefreshListener(() -> mGithubReposAdapter.refresh());

        // Toggle date range buttons
        mBinding.toggleBtnDateRangeTrendingRepos.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                DateRange dateRange = DateRange.LAST_DAY;

                if (checkedId == R.id.btn_last_day_trending_repos) {
                    dateRange = DateRange.LAST_DAY;
                } else if (checkedId == R.id.btn_last_week_trending_repos) {
                    dateRange = DateRange.LAST_WEEK;
                } else if (checkedId == R.id.btn_last_month_trending_repos) {
                    dateRange = DateRange.LAST_MONTH;
                }

                mCurrentDateRange = dateRange;

                getGithubTrendingRepos(dateRange);
            }
        });

        // Close search mode
        mBinding.ivSearchClearTrendingRepos.setOnClickListener(searchView -> {
            searchModeOff();
            getGithubTrendingRepos(mCurrentDateRange);
        });

        // Search button
        mBinding.btnSearchTrendingRepos.setOnClickListener(view1 -> {
            hideKeyboard(requireActivity());
            mBinding.etSearchTrendingRepos.clearFocus();
            getGithubTrendingRepos(mCurrentDateRange);
        });

        // Search view
        mBinding.etSearchTrendingRepos.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchModeOn();
            }
        });

        mBinding.etSearchTrendingRepos.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(requireActivity());
                mBinding.etSearchTrendingRepos.clearFocus();
                getGithubTrendingRepos(mCurrentDateRange);
                return true;
            }
            return false;
        });

        // Github repositories adapter
        mGithubReposAdapter.addLoadStateListener(combinedLoadStates -> {
            mGithubTrendingReposViewModel.setLoadingMutableLiveData(combinedLoadStates.getRefresh() instanceof LoadState.Loading);

            if (combinedLoadStates.getRefresh() instanceof LoadState.Error) {
                LoadState.Error loadStateError = (LoadState.Error) combinedLoadStates.getRefresh();
                if (loadStateError.getError() instanceof NoNetworkConnectionException) {
                    mGithubTrendingReposViewModel.setErrorMessageMutableLiveData(loadStateError.getError().getLocalizedMessage());
                }
            } else {
                mGithubTrendingReposViewModel.setErrorMessageMutableLiveData(null);
            }

            mGithubTrendingReposViewModel.setNoDataMutableLiveData(combinedLoadStates.getRefresh() instanceof LoadState.NotLoading && combinedLoadStates.getAppend().getEndOfPaginationReached() && mGithubReposAdapter.getItemCount() < 1);
            return null;
        });

        // Github repositories recyclerview
        mBinding.rvTrendingRepos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && mBinding.fabFavouritesTrendingRepos.isExtended()) {
                    mBinding.fabFavouritesTrendingRepos.shrink();
                } else if (dy < 0 && !mBinding.fabFavouritesTrendingRepos.isExtended()) {
                    mBinding.fabFavouritesTrendingRepos.extend();
                }
            }
        });
    }

    private void getGithubTrendingRepos(DateRange dateRange) {
        mGithubTrendingReposViewModel.getTrendingRepositoriesByMinDate(mBinding.etSearchTrendingRepos.getText().toString(), dateRange).to(autoDisposable(AndroidLifecycleScopeProvider.from(requireActivity()))).subscribe(pagingData -> mGithubReposAdapter.submitData(getLifecycle(), pagingData));
    }

    private void searchModeOn() {
        TransitionManager.beginDelayedTransition(mBinding.tbTrendingRepos, getSearchViewsTransitionSet());
        mBinding.ivSearchClearTrendingRepos.setVisibility(View.VISIBLE);
        mBinding.ivSearchTrendingRepos.setVisibility(View.GONE);
        mBinding.btnSearchTrendingRepos.setVisibility(View.VISIBLE);
    }

    private void searchModeOff() {
        hideKeyboard(requireActivity());
        mBinding.etSearchTrendingRepos.getText().clear();
        mBinding.etSearchTrendingRepos.clearFocus();

        TransitionManager.beginDelayedTransition(mBinding.tbTrendingRepos, getSearchViewsTransitionSet());
        mBinding.ivSearchTrendingRepos.setVisibility(View.VISIBLE);
        mBinding.ivSearchClearTrendingRepos.setVisibility(View.GONE);
        mBinding.btnSearchTrendingRepos.setVisibility(View.GONE);
    }

    private TransitionSet getSearchViewsTransitionSet() {
        TransitionSet set = new TransitionSet();

        Transition changeBounds = new ChangeBounds();
        changeBounds.setDuration(100);
        changeBounds.addTarget(mBinding.etSearchTrendingRepos);

        Transition slide = new Slide(Gravity.TOP);
        slide.setDuration(100);
        slide.addTarget(mBinding.ivSearchClearTrendingRepos);
        slide.addTarget(mBinding.ivSearchTrendingRepos);
        slide.addTarget(mBinding.btnSearchTrendingRepos);

        set.addTransition(changeBounds);
        set.addTransition(slide);
        return set;
    }

    private void setupTrendingRepositoriesRecyclerView() {
        mGithubReposAdapter = new GithubReposAdapter(githubRepo -> {
            mGithubTrendingReposViewModel.setCurrentRepoMutableLiveData(githubRepo);
            NavHostFragment.findNavController(this).navigate(R.id.action_currencyConverterFragment_to_githubRepoDetailsFragment);
        });
        GithubReposLoadStateAdapter githubReposLoadStateAdapter = new GithubReposLoadStateAdapter(v -> mGithubReposAdapter.retry());
        GithubReposHeaderAdapter githubReposHeaderAdapter = new GithubReposHeaderAdapter();
        ConcatAdapter concatAdapter = new ConcatAdapter(githubReposHeaderAdapter, mGithubReposAdapter.withLoadStateFooter(githubReposLoadStateAdapter));
        mBinding.rvTrendingRepos.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.rvTrendingRepos.setAdapter(concatAdapter);
    }


    private void initViewModel() {
        mGithubTrendingReposViewModel = new ViewModelProvider(requireActivity()).get(GithubTrendingReposViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}