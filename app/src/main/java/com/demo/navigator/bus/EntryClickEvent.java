package com.demo.navigator.bus;

import android.support.annotation.NonNull;

import com.demo.navigator.ds.Entry;

public final class EntryClickEvent {
	private Entry mEntry;


	public @NonNull
	Entry getEntry() {
		return mEntry;
	}

	public void setEntry(@NonNull Entry entry) {
		mEntry = entry;
	}
}
