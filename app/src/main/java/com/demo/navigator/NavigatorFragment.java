package com.demo.navigator;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.navigator.databinding.FragmentNavigatorBinding;

public final class NavigatorFragment extends Fragment {
	private static final int LAYOUT = R.layout.fragment_navigator;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragmentNavigatorBinding binding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		binding.setFragment(this);
		App.Instance.navigator.setFragmentNavigatorBinding(binding);
		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		App.Instance.navigator.load();
	}
}
