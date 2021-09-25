package com.yjrlab.tabdoctor.libs;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jongrakmoon on 2017. 6. 13..
 */

public abstract class OnBottomEventListener extends RecyclerView.OnScrollListener {
    private boolean loading = true;

    protected abstract void onBottom();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy >= 0) {
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (loading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                loading = false;
                onBottom();
            }
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            loading = true;
        }
    }
}
