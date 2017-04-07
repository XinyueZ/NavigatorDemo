package com.demo.navigator.utils;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.demo.navigator.ds.model.Entry;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
	private Utils() {
	}



	@NonNull
	public static List<Entry> rebuildForSections(@NonNull List<Entry> entries) {
		final List<Entry> rebuiltList = new ArrayList<>();
		for (Entry entry : entries) {
			rebuiltList.add(entry);
			if (TextUtils.equals("section", entry.getType())) {
				rebuiltList.addAll(entry.getChildren());
			}
		}
		return rebuiltList;
	}
}
