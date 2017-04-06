package com.demo.navigator.ds;


import android.support.annotation.NonNull;

import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.ds.model.NavigationEntries;
import com.demo.navigator.retrofit.Backend;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Singleton
public final class DsRemoteSource implements DsSource {

	@Inject
	public DsRemoteSource() {}


	@Override
	public void loadEntry(@NonNull
	                      final EntryLoadedCallback callback) {
		Backend.Instance.getNavigationEntries()
		                .subscribeOn(Schedulers.io())
		                .observeOn(AndroidSchedulers.mainThread())
		                .subscribe(new Consumer<NavigationEntries>() {
			                @Override
			                public void accept(NavigationEntries navigationEntries) throws Exception {
				                Entry entry = new Entry("root", "root", null, navigationEntries.getEntries());
				                callback.onLoaded(entry);
			                }
		                });
	}
}
