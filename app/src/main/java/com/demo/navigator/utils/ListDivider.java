package com.demo.navigator.utils;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DividerItemDecoration;

import com.demo.navigator.R;

public final class ListDivider extends DividerItemDecoration {
	public ListDivider(Context context) {
		super(context, DividerItemDecoration.VERTICAL);
		setDrawable(AppCompatResources.getDrawable(context, R.drawable.divider_drawable));
	}
}
