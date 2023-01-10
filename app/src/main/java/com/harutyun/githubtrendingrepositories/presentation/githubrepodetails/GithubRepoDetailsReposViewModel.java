package com.harutyun.githubtrendingrepositories.presentation.githubrepodetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.harutyun.domain.models.GithubRepo;
import com.harutyun.domain.usecases.GetTrendingReposByNameCreatedLaterThanXUseCase;
import com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos.DateRange;

import java.util.Date;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

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