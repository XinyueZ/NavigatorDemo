package com.demo.navigator.navigation.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.navigation.DaggerNavigatorComponent;
import com.demo.navigator.navigation.Navigator;
import com.demo.navigator.navigation.NavigatorContract;
import com.demo.navigator.navigation.NavigatorModule;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public final class NavigatorFragment extends Fragment implements NavigatorContract.View {
	private static final int LAYOUT = R.layout.fragment_navigator;
	@Inject Navigator mNavigator;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		FragmentNavigatorBinding binding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		binding.setFragment(this);

		DaggerNavigatorComponent.builder()
		                        .navigatorModule(new NavigatorModule(this, binding))
		                        .dsRepositoryComponent((App.Instance).getRepositoryComponent())
		                        .build()
		                        .inject(this);

		return binding.getRoot();
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mNavigator.start();
	}

	@Override
	public void onResume() {
		EventBus.getDefault()
		        .register(mNavigator);
		super.onResume();
	}

	@Override
	public void onPause() {
		EventBus.getDefault()
		        .unregister(mNavigator);
		super.onPause();
	}

	@Override
	public void setPresenter(Navigator presenter) {
		mNavigator = presenter;
	}

	@Override
	public void showEntry() {
		mNavigator.load();
	}
}
