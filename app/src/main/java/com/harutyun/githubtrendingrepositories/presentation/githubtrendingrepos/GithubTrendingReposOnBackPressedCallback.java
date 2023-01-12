package com.harutyun.githubtrendingrepositories.presentation.githubtrendingrepos;

import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

public class GithubTrendingReposOnBackPressedCallback extends OnBackPressedCallback implements SlidingPaneLayout.PanelSlideListener {
    private final SlidingPaneLayout slidingPaneLayout;

    public GithubTrendingReposOnBackPressedCallback(SlidingPaneLayout slidingPaneLayout) {
        super(slidingPaneLayout.isSlideable() && slidingPaneLayout.isOpen());
        this.slidingPaneLayout = slidingPaneLayout;
        slidingPaneLayout.addPanelSlideListener(this);
    }

    @Override
    public void handleOnBackPressed() {
        slidingPaneLayout.closePane();
    }

    @Override
    public void onPanelSlide(@NonNull View panel, float slideOffset) {
    }

    @Override
    public void onPanelOpened(@NonNull View panel) {
        setEnabled(true);
    }

    @Override
    public void onPanelClosed(@NonNull View panel) {
        setEnabled(false);
    }
}
