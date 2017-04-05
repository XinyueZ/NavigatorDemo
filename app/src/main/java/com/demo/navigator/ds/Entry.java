package com.demo.navigator.ds;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Entry implements Serializable {
	@SerializedName("type") private String mType;
	@SerializedName("label") private String mLabel;
	@SerializedName("url") private String mUrl;
	@SerializedName("children") private List<Entry> mChildren;

	public Entry(String type, String label, String url, List<Entry> children) {
		mType = type;
		mLabel = label;
		mUrl = url;
		mChildren = children;
	}

	public String getType() {
		return mType;
	}

	public void setType(String type) {
		mType = type;
	}

	public String getLabel() {
		return mLabel;
	}

	public void setLabel(String label) {
		mLabel = label;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public List<Entry> getChildren() {
		if (mChildren == null) {
			return new ArrayList<>();
		}
		return mChildren;
	}

	public void setChildren(List<Entry> children) {
		mChildren = children;
	}
}
