package com.demo.navigator.ds;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;

import com.demo.navigator.app.App;
import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.ds.model.NavigationEntries;
import com.demo.navigator.retrofit.Service;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public final class DsRemoteSource implements DsSource {
	private final Service mService;
	private final App mApp;

	@Inject
	public DsRemoteSource(@NonNull App app, @NonNull Service service) {
		mService = service;
		mApp = app;
	}


	@Override
	public void loadEntry(@NonNull
	                      final EntryLoadedCallback callback) {
		mService.getNavigationEntries()
		        .subscribeOn(Schedulers.io())
		        .observeOn(AndroidSchedulers.mainThread())
		        .map(new Function<NavigationEntries, Entry>() {
			        @Override
			        public Entry apply(NavigationEntries navigationEntries) throws Exception {
				        String json = mApp.getGson()
				                          .toJson(navigationEntries, NavigationEntries.class);
				        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mApp)
				                                                                 .edit();
				        editor.putString("menu", json);
				        SharedPreferencesCompat.EditorCompat.getInstance()
				                                            .apply(editor);
				        return new Entry("root", "root", null, navigationEntries.getEntries());
			        }
		        })
		        .subscribe(new Consumer<Entry>() {
			        @Override
			        public void accept(@NonNull Entry rebuiltEntry) throws Exception {
				        callback.onLoaded(rebuiltEntry);
			        }
		        });
	}
}
