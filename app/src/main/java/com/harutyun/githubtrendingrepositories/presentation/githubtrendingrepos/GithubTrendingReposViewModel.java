package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.usecases.GetTrendingReposByNameCreatedLaterThanXUseCase;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class GithubTrendingReposViewModel extends ViewModel {

    private final GetTrendingReposByNameCreatedLaterThanXUseCase mGetTrendingReposByNameCreatedLaterThanXUseCase;

    private final MutableLiveData<Boolean> loadingMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> loadingLiveData = loadingMutableLiveData;

    private final MutableLiveData<String> errorMessageMutableLiveData = new MutableLiveData<>();
    public final LiveData<String> errorMessageLiveData = errorMessageMutableLiveData;

    private final MutableLiveData<Boolean> noDataMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> noDataLiveData = noDataMutableLiveData;


    @Inject
    public GithubTrendingReposViewModel(GetTrendingReposByNameCreatedLaterThanXUseCase getTrendingReposByNameCreatedLaterThanXUseCase) {
        mGetTrendingReposByNameCreatedLaterThanXUseCase = getTrendingReposByNameCreatedLaterThanXUseCase;
    }

    public Flowable<PagingData<GithubRepo>> getTrendingRepositoriesByMinDate(String searchInput, DateRange x) {
        Date minDate = x.getDate();

        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Flowable<PagingData<GithubRepo>> reposFlowable = mGetTrendingReposByNameCreatedLaterThanXUseCase.invoke(searchInput, minDate);
        PagingRx.cachedIn(reposFlowable, viewModelScope);

        return reposFlowable;
    }


    public void setLoadingMutableLiveData(Boolean loadingMutableLiveData) {
        this.loadingMutableLiveData.setValue(loadingMutableLiveData);
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }


    public void setErrorMessageMutableLiveData(String errorMessageMutableLiveData) {
        this.errorMessageMutableLiveData.setValue(errorMessageMutableLiveData);
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public void setNoDataMutableLiveData(Boolean noDataMutableLiveData) {
        this.noDataMutableLiveData.setValue(noDataMutableLiveData);
    }

    public LiveData<Boolean> getNoDataLiveData() {
        return noDataLiveData;
    }
}