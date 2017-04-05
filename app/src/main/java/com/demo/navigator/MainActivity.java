package com.demo.navigator;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

public final class MainActivity extends AppBarActivity {


	@Override
	protected void setupContent(@NonNull FrameLayout contentLayout) {
		Intent intent = getIntent();
		if (intent == null) {
			return;
		}
		getSupportFragmentManager().beginTransaction()
		                           .replace(contentLayout.getId(), WebViewFragment.newInstance(this, Uri.parse(getString(R.string.home_url))))
		                           .commit();
	}

}
