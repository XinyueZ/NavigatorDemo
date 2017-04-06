package com.demo.navigator.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.customtab.BrowserFallback;
import com.demo.navigator.customtab.CustomTabActivityHelper;


public final class CustomTabUtils {
	private static final BrowserFallback FALLBACK = new BrowserFallback();
	private static final CustomTabActivityHelper HELPER = new CustomTabActivityHelper();
	private static final int BAR_COLOR = ContextCompat.getColor(App.Instance, R.color.colorPrimary);

	public static void bind(Context cxt, @NonNull String... urls) {
		for (String url : urls) {
			HELPER.bindCustomTabsService(cxt)
			      .mayLaunchUrl(Uri.parse(url), null, null);
		}
	}

	public static void unbind(Context cxt) {
		HELPER.unbindCustomTabsService(cxt);
	}


	public static void openWeb(@NonNull Fragment fragment, @NonNull String title, @NonNull Uri uri) {
		Activity activity = fragment.getActivity();
		if (activity == null) {
			return;
		}
		CustomTabActivityHelper.openCustomTab(activity,
		                                      new CustomTabsIntent.Builder().setShowTitle(true)
		                                                                    .setToolbarColor(BAR_COLOR)
		                                                                    .build(),
		                                      title,
		                                      uri,
		                                      FALLBACK);
	}
}
