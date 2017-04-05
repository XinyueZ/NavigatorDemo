package com.demo.navigator.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.demo.navigator.R;
import com.demo.navigator.databinding.FragmentWebViewBinding;

public final class WebViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
	private static final int LAYOUT = R.layout.fragment_web_view;
	private static final String EXTRAS_URI = WebViewFragment.class.getName() + ".EXTRAS.uri";
	private FragmentWebViewBinding mBinding;

	public static WebViewFragment newInstance(@NonNull Context cxt, @NonNull Uri uri) {
		Bundle args = new Bundle(1);
		args.putParcelable(EXTRAS_URI, uri);
		return (WebViewFragment) WebViewFragment.instantiate(cxt, WebViewFragment.class.getName(), args);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Uri uri = getArguments().getParcelable(EXTRAS_URI);
		if (uri == null) {
			return;
		}
		setupWebViewSettings();
		mBinding.contentWv.loadUrl(uri.toString());
//		mBinding.contentWvSrl.setOnRefreshListener(this);
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setupWebViewSettings() {
		WebSettings settings = mBinding.contentWv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
	}

	@Override
	public void onRefresh() {
		mBinding.contentWv.reload();
	}
}
