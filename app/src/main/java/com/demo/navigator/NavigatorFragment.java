package com.demo.navigator;


import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.ds.Entry;
import com.demo.navigator.ds.NavigationEntries;
import com.demo.navigator.retrofit.Service;

import de.greenrobot.event.EventBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class NavigatorFragment extends Fragment implements Toolbar.OnMenuItemClickListener {
	private static final int LAYOUT = R.layout.fragment_navigator;
	private FragmentNavigatorBinding mBinding;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mBinding = DataBindingUtil.inflate(inflater, LAYOUT, container, false);
		return mBinding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loadNavigation();
	}

	private void setupMenuBar() {
		mBinding.menuBar.setTitle(R.string.main_menu);
		mBinding.menuBar.inflateMenu(R.menu.menu_navigator);
		mBinding.menuBar.setOnMenuItemClickListener(this);
	}

	private void loadNavigation() {
		Service.Instance.getNavigationEntries()
		                .subscribeOn(Schedulers.io())
		                .observeOn(AndroidSchedulers.mainThread())
		                .subscribe(new Consumer<NavigationEntries>() {
			                @Override
			                public void accept(NavigationEntries navigationEntries) throws Exception {
				                Activity activity = getActivity();
				                if (activity != null) {
					                setupMenuBar();
					                getChildFragmentManager().beginTransaction()
					                                         .add(mBinding.navigatorContentFl.getId(),
					                                              EntryFragment.newInstance(activity, new Entry("root", "root", null, navigationEntries.getEntries())))
					                                         .commitNow();
				                }
			                }
		                });
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_close_navigator:
				EventBus.getDefault()
				        .post(new CloseNavigatorEvent());
				break;
		}
		return false;
	}
}
