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

import android.support.annotation.NonNull;

import com.demo.navigator.app.App;
import com.demo.navigator.utils.NetworkUtils;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DsRepository implements DsSource {

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
		if (NetworkUtils.isAirplaneModeOn(mApp) || !NetworkUtils.isOnline(mApp)) {
			mLocalDs.loadEntry(callback);
		} else {
			mRemoteDs.loadEntry(callback);
		}
	}
}
