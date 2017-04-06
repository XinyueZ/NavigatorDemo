package com.demo.navigator.ds.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public final class NavigationEntries {
	@SerializedName("navigationEntries") private List<Entry> mEntries;

	public NavigationEntries(List<Entry> entries) {
		mEntries = entries;
	}

	public List<Entry> getEntries() {
		if(mEntries == null) {
			return new ArrayList<>();
		}
		return mEntries;
	}

	public void setEntries(List<Entry> entries) {
		mEntries = entries;
	}
}
