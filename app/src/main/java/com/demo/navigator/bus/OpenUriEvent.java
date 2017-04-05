package com.demo.navigator.bus;


import android.net.Uri;
import android.support.annotation.NonNull;

public final class OpenUriEvent {
	private final @NonNull Uri mUri;


	public OpenUriEvent(@NonNull Uri uri) {
		mUri = uri;
	}

	public @NonNull
	Uri getUri() {
		return mUri;
	}
}
