package com.smait.quyengop.Controller.Helper;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceDividerItemDecoration extends RecyclerView.ItemDecoration  {
    private final int mSpaceHeight;
    private boolean mShowLastDivider;

    public SpaceDividerItemDecoration(int spaceHeight, boolean showLastDivider) {
        this.mSpaceHeight = spaceHeight;
        mShowLastDivider = showLastDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        boolean isLastItem = parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1;
        if (!isLastItem || mShowLastDivider) {
            outRect.bottom = mSpaceHeight;
        }
    }
}
