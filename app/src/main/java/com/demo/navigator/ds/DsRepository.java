/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.navigator.ds;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;

import com.demo.navigator.R;
import com.demo.navigator.app.App;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.demo.navigator.utils.NetworkUtils.CONNECTION_FAST;
import static com.demo.navigator.utils.NetworkUtils.CONNECTION_ROAMING;
import static com.demo.navigator.utils.NetworkUtils.CONNECTION_SLOW;
import static com.demo.navigator.utils.NetworkUtils.CONNECTION_WIFI;
import static com.demo.navigator.utils.NetworkUtils.getCurrentNetworkType;
import static com.demo.navigator.utils.NetworkUtils.isAirplaneModeOn;
import static com.demo.navigator.utils.NetworkUtils.isOnline;


@Singleton
public class DsRepository implements DsSource {

	static final String PRE_KEY_MENU = "menu";
	private static final String PRE_KEY_EXPIRATION = "expiration";
	private final App mApp;
	private final DsSource mRemoteDs;
	private final DsSource mLocalDs;

	@Inject
	DsRepository(@NonNull App app, @Remote DsSource remoteDs, @Local DsSource localDs) {
		mApp = app;
		mRemoteDs = remoteDs;
		mLocalDs = localDs;
	}

	@Override
	public void loadEntry(@NonNull EntryLoadedCallback callback) {
		if (isAirplaneModeOn(mApp) || !isOnline(mApp)) {
			callOffline(callback);
		} else {
			byte currentNetworkType = getCurrentNetworkType(mApp);
			switch (currentNetworkType) {
				case CONNECTION_WIFI:
					callOnline(callback);
					break;
				default:
					switch (currentNetworkType) {
						case CONNECTION_ROAMING:
						case CONNECTION_SLOW:
							callOffline(callback);
							break;
						case CONNECTION_FAST:
							callInFastMobileNetwork(callback);
							break;
					}
					break;
			}
		}
	}

	private void callInFastMobileNetwork(@NonNull EntryLoadedCallback callback) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mApp);
		mLocalDs.loadEntry(callback);
		callback.onTextMessage(R.string.mobile_network);
		long expiration = preferences.getLong(PRE_KEY_EXPIRATION, 0);
		if (expiration > 0 && System.currentTimeMillis() > expiration) {
			callOnline(callback);
			callback.onTextMessage(R.string.forced_loading);
		}
	}

	private void callOnline(@NonNull EntryLoadedCallback callback) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mApp);
		SharedPreferences.Editor editor = preferences.edit();
		mRemoteDs.loadEntry(callback);
		editor.putLong("expiration", System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));
		SharedPreferencesCompat.EditorCompat.getInstance()
		                                    .apply(editor);
	}

	private void callOffline(@NonNull EntryLoadedCallback callback) {
		mLocalDs.loadEntry(callback);
		callback.onTextMessage(R.string.offline);
	}
}
