package com.demo.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.FrameLayout;

public final class MainActivity extends AppBarActivity {

	public static void showInstance(@NonNull Activity cxt) {
		Intent intent = new Intent(cxt, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ActivityCompat.startActivity(cxt, intent, Bundle.EMPTY);
	}


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
