package com.harutyun.githubtrendingrepositories.presentation.githubfavouriterepos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.usecases.GetFavouriteReposFromLocalDbUseCase;
import com.harutyun.domain.usecases.RemoveFavouriteRepoFromLocalDbUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class GithubFavouriteReposViewModel extends ViewModel {

    private final GetFavouriteReposFromLocalDbUseCase mGetFavouriteReposFromLocalDbUseCase;
    private final RemoveFavouriteRepoFromLocalDbUseCase mRemoveFavouriteRepoFromLocalDbUseCase;

    private final MutableLiveData<List<GithubRepo>> favouriteReposMutableLiveData = new MutableLiveData<>();
    public final LiveData<List<GithubRepo>> favouriteReposLiveData = favouriteReposMutableLiveData;

    private final MutableLiveData<Boolean> noDataMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> noDataLiveData = noDataMutableLiveData;

    private final MutableLiveData<Boolean> completedMutableLiveData = new MutableLiveData<>(false);
    public final LiveData<Boolean> completedLiveData = completedMutableLiveData;

    private final MutableLiveData<String> failureMessageMutableLiveData = new MutableLiveData<>();
    public final LiveData<String> failureMessageLiveData = failureMessageMutableLiveData;

    @Inject
    public GithubFavouriteReposViewModel(GetFavouriteReposFromLocalDbUseCase getFavouriteReposFromLocalDbUseCase,
                                         RemoveFavouriteRepoFromLocalDbUseCase removeFavouriteRepoFromLocalDbUseCase) {
        mGetFavouriteReposFromLocalDbUseCase = getFavouriteReposFromLocalDbUseCase;
        mRemoveFavouriteRepoFromLocalDbUseCase = removeFavouriteRepoFromLocalDbUseCase;
        getFavouriteRepos();
    }

    private void getFavouriteRepos() {
        mGetFavouriteReposFromLocalDbUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favouriteRepos -> {
                    if (favouriteRepos == null || favouriteRepos.isEmpty()) {
                        setNoDataMutableLiveData(true);
                    } else {
                        setNoDataMutableLiveData(false);
                        setFavouriteReposMutableLiveData(favouriteRepos);
                    }
                }, throwable -> Log.e(GithubFavouriteReposViewModel.class.getSimpleName(), "getFavouriteRepos: "+throwable.getLocalizedMessage() ));
    }

    public void removeFavouriteRepoFromLocalDb(GithubRepo githubRepo) {
        mRemoveFavouriteRepoFromLocalDbUseCase.invoke(githubRepo).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        setCompletedMutableLiveData(true);
                        githubRepo.setFavourite(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        setFailureMessageMutableLiveData("Error: "+e.getLocalizedMessage());
                    }
                });
    }


    public MutableLiveData<List<GithubRepo>> getFavouriteReposMutableLiveData() {
        return favouriteReposMutableLiveData;
    }

    public void setFavouriteReposMutableLiveData(List<GithubRepo> favouriteReposMutableLiveData) {
        this.favouriteReposMutableLiveData.setValue(favouriteReposMutableLiveData);
    }

    public LiveData<List<GithubRepo>> getFavouriteReposLiveData() {
        return favouriteReposLiveData;
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