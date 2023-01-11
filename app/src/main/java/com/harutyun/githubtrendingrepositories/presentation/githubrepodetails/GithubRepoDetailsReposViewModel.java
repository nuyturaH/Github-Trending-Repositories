package com.harutyun.githubtrendingrepositories.presentation.githubrepodetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.harutyun.domain.models.GithubRepo;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GithubRepoDetailsReposViewModel extends ViewModel {

    private final MutableLiveData<GithubRepo> currentRepoMutableLiveData = new MutableLiveData<>();
    public final LiveData<GithubRepo> currentRepoDataLiveData = currentRepoMutableLiveData;

    @Inject
    public GithubRepoDetailsReposViewModel() {
    }

    public void setCurrentRepoMutableLiveData(GithubRepo currentRepoMutableLiveData) {
        this.currentRepoMutableLiveData.setValue(currentRepoMutableLiveData);
    }

    public LiveData<GithubRepo> getCurrentRepoDataLiveData() {
        return currentRepoDataLiveData;
    }
}