package com.harutyun.githubtrendingrepositories.presentation.githubfavouriterepos;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.usecases.GetFavouriteReposFromLocalDbUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class GithubFavouriteReposViewModel extends ViewModel {

    private final GetFavouriteReposFromLocalDbUseCase mGetFavouriteReposFromLocalDbUseCase;

    private final MutableLiveData<List<GithubRepo>> favouriteReposMutableLiveData = new MutableLiveData<>();
    public final LiveData<List<GithubRepo>> favouriteReposLiveData = favouriteReposMutableLiveData;

    private final MutableLiveData<Boolean> noDataMutableLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> noDataLiveData = noDataMutableLiveData;

    @Inject
    public GithubFavouriteReposViewModel(GetFavouriteReposFromLocalDbUseCase getFavouriteReposFromLocalDbUseCase) {
        mGetFavouriteReposFromLocalDbUseCase = getFavouriteReposFromLocalDbUseCase;

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

}