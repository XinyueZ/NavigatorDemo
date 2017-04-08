package com.demo.navigator.bus;

import android.support.annotation.StringRes;

public final class MessageEvent {
	private final @StringRes int mMessage;

	public MessageEvent(@StringRes int messageRes) {
		mMessage = messageRes;
	}


	public int getMessage() {
		return mMessage;
	}
}
