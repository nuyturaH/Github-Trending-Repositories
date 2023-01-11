package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.usecases.GetTrendingReposByNameCreatedLaterThanXUseCase;
import com.harutyun.domain.usecases.RemoveFavouriteRepoFromLocalDbUseCase;
import com.harutyun.domain.usecases.SaveFavouriteRepoInLocalDbUseCase;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class GithubTrendingReposViewModel extends ViewModel {

    private final GetTrendingReposByNameCreatedLaterThanXUseCase mGetTrendingReposByNameCreatedLaterThanXUseCase;
    private final SaveFavouriteRepoInLocalDbUseCase mSaveFavouriteRepoInLocalDbUseCase;
    private final RemoveFavouriteRepoFromLocalDbUseCase mRemoveFavouriteRepoFromLocalDbUseCase;

    private final MutableLiveData<Boolean> loadingMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> loadingLiveData = loadingMutableLiveData;

    private final MutableLiveData<String> errorMessageMutableLiveData = new MutableLiveData<>();
    public final LiveData<String> errorMessageLiveData = errorMessageMutableLiveData;

    private final MutableLiveData<Boolean> noDataMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> noDataLiveData = noDataMutableLiveData;

    private final MutableLiveData<Boolean> completedMutableLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> completedLiveData = completedMutableLiveData;

    private final MutableLiveData<Boolean> completedRemovingMutableLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> completedRemovingLiveData = completedRemovingMutableLiveData;

    private final MutableLiveData<String> failureMessageMutableLiveData = new MutableLiveData<>();
    public final LiveData<String> failureMessageLiveData = failureMessageMutableLiveData;


    @Inject
    public GithubTrendingReposViewModel(GetTrendingReposByNameCreatedLaterThanXUseCase getTrendingReposByNameCreatedLaterThanXUseCase,
                                        SaveFavouriteRepoInLocalDbUseCase saveFavouriteRepoInLocalDbUseCase,
                                        RemoveFavouriteRepoFromLocalDbUseCase removeFavouriteRepoFromLocalDbUseCase) {
        mGetTrendingReposByNameCreatedLaterThanXUseCase = getTrendingReposByNameCreatedLaterThanXUseCase;
        mSaveFavouriteRepoInLocalDbUseCase = saveFavouriteRepoInLocalDbUseCase;
        mRemoveFavouriteRepoFromLocalDbUseCase = removeFavouriteRepoFromLocalDbUseCase;
    }

    public Flowable<PagingData<GithubRepo>> getTrendingRepositoriesByMinDate(String searchInput, DateRange x) {
        Date minDate = x.getDate();

        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Flowable<PagingData<GithubRepo>> reposFlowable = mGetTrendingReposByNameCreatedLaterThanXUseCase.invoke(searchInput, minDate);
        PagingRx.cachedIn(reposFlowable, viewModelScope);

        return reposFlowable;
    }

    public void saveFavouriteRepoInLocalDb(GithubRepo githubRepo) {
        mSaveFavouriteRepoInLocalDbUseCase.invoke(githubRepo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        setCompletedMutableLiveData(true);
                        githubRepo.setFavourite(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        setFailureMessageMutableLiveData("Error: " + e.getLocalizedMessage());
                    }
                });
    }

    public void removeFavouriteRepoFromLocalDb(GithubRepo githubRepo) {
        mRemoveFavouriteRepoFromLocalDbUseCase.invoke(githubRepo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        setCompletedRemovingMutableLiveData(true);
                        githubRepo.setFavourite(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        setFailureMessageMutableLiveData("Error: " + e.getLocalizedMessage());
                    }
                });
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

    public void setCompletedMutableLiveData(Boolean completedMutableLiveData) {
        this.completedMutableLiveData.setValue(completedMutableLiveData);
    }

    public LiveData<Boolean> getCompletedLiveData() {
        return completedLiveData;
    }

    public void setCompletedRemovingMutableLiveData(Boolean completedRemovingMutableLiveData) {
        this.completedRemovingMutableLiveData.setValue(completedRemovingMutableLiveData);
    }

    public LiveData<Boolean> getCompletedRemovingLiveData() {
        return completedRemovingLiveData;
    }


    public MutableLiveData<String> getFailureMessageMutableLiveData() {
        return failureMessageMutableLiveData;
    }

    public void setFailureMessageMutableLiveData(String failureMessageMutableLiveData) {
        this.failureMessageMutableLiveData.setValue(failureMessageMutableLiveData);
    }

    public LiveData<String> getFailureMessageLiveData() {
        return failureMessageLiveData;
    }

}