package com.demo.navigator.ds;


import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.demo.navigator.ds.model.Entry;

public interface DsSource {
	void loadEntry(@NonNull EntryLoadedCallback callback);

	interface EntryLoadedCallback {

		void onLoaded(@NonNull Entry entry);

		void onTextMessage(@StringRes int msgResId);
	}
}
