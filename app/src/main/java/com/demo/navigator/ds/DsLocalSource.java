package com.demo.navigator.ds;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.demo.navigator.app.App;
import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.ds.model.NavigationEntries;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@Singleton
public final class DsLocalSource implements DsSource {
	private final App mApp;

	@Inject
	public DsLocalSource(@NonNull App app) {
		mApp = app;
	}


	@Override
	public void loadEntry(@NonNull
	                      final EntryLoadedCallback callback) {
		Observable.just(PreferenceManager.getDefaultSharedPreferences(mApp))
		          .subscribeOn(Schedulers.newThread())
		          .map(new Function<SharedPreferences, Entry>() {
			          @Override
			          public Entry apply(@NonNull SharedPreferences sharedPreferences) throws Exception {
				          String json = sharedPreferences.getString(DsRepository.PRE_KEY_MENU, null);
				          NavigationEntries navigationEntries = mApp.getGson()
				                                                    .fromJson(json, NavigationEntries.class);
				          return new Entry("root",
				                           "root",
				                           null,
				                           navigationEntries == null ?
				                           new ArrayList<Entry>() :
				                           navigationEntries.getEntries());
			          }
		          })
		          .observeOn(AndroidSchedulers.mainThread())
		          .subscribe(new Consumer<Entry>() {
			                     @Override
			                     public void accept(@NonNull Entry rebuiltEntry) throws Exception {
				                     callback.onLoaded(rebuiltEntry);
			                     }
		                     }

		          );
	}
}
