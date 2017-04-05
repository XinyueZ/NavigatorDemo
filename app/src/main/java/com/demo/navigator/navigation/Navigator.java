package com.demo.navigator.navigation;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.demo.navigator.R;
import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.ds.Entry;
import com.demo.navigator.ds.NavigationEntries;
import com.demo.navigator.retrofit.Service;

import de.greenrobot.event.EventBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class Navigator implements Toolbar.OnMenuItemClickListener {
	private @Nullable FragmentNavigatorBinding mBinding;


	public void setFragmentNavigatorBinding(@NonNull FragmentNavigatorBinding navigatorBinding) {
		mBinding = navigatorBinding;
	}

	private void setupMenuBar() {
		if (mBinding == null) {
			return;
		}
		mBinding.menuBar.setTitle(R.string.main_menu);
		mBinding.menuBar.inflateMenu(R.menu.menu_navigator);
		mBinding.menuBar.setOnMenuItemClickListener(this);
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

	public void load() {
		Service.Instance.getNavigationEntries()
		                .subscribeOn(Schedulers.io())
		                .observeOn(AndroidSchedulers.mainThread())
		                .subscribe(new Consumer<NavigationEntries>() {
			                @Override
			                public void accept(NavigationEntries navigationEntries) throws Exception {
				                if (mBinding == null) {
					                return;
				                }
				                Fragment fragment = mBinding.getFragment();
				                Activity activity = fragment.getActivity();
				                if (activity != null) {
					                setupMenuBar();
					                fragment.getChildFragmentManager()
					                        .beginTransaction()
					                        .add(mBinding.navigatorContentFl.getId(), EntryFragment.newInstance(activity, new Entry("root", "root", null, navigationEntries.getEntries())))
					                        .commitNow();
				                }
			                }
		                });
	}
}
