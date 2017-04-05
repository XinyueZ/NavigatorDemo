package com.demo.navigator;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DividerItemDecoration;

public final class ListDivider extends DividerItemDecoration {
	public ListDivider(Context context, int orientation) {
		super(context, orientation);
		setDrawable(AppCompatResources.getDrawable(context, R.drawable.divider_drawable));
	}
}
