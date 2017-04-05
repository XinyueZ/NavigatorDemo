package com.demo.navigator.navigation;


import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.bus.EntryClickEvent;
import com.demo.navigator.bus.OpenUriEvent;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.ds.Entry;
import com.demo.navigator.ds.NavigationEntries;
import com.demo.navigator.retrofit.Service;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public final class Navigator implements Toolbar.OnMenuItemClickListener,
                                        View.OnClickListener,
                                        FragmentManager.OnBackStackChangedListener {
	private @Nullable FragmentNavigatorBinding mBinding;

	/**
	 * Handler for {@link  EntryClickEvent}.
	 *
	 * @param e Event {@link EntryClickEvent}.
	 */
	@SuppressWarnings("unused")
	@Subscribe
	public void onEvent(EntryClickEvent e) {
		if (TextUtils.equals(e.getEntry()
		                      .getType(), "link")) {
			EventBus.getDefault()
			        .post(new OpenUriEvent(Uri.parse(e.getEntry()
			                                          .getUrl())));
			EventBus.getDefault()
			        .post(new CloseNavigatorEvent());
			return;
		}
		if (mBinding == null) {
			return;
		}
		mBinding.navigatorContentFl.show(0, 0, 800);
		navigateEntry(e.getEntry(), false);
	}

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
				                Entry entry = new Entry("root", "root", null, navigationEntries.getEntries());
				                setupMenuBar();
				                navigateEntry(entry, true);
				                mBinding.getFragment()
				                        .getChildFragmentManager()
				                        .addOnBackStackChangedListener(Navigator.this);
			                }
		                });
	}

	private void navigateEntry(@NonNull Entry entry, boolean isRoot) {
		if (mBinding == null) {
			return;
		}
		Fragment fragment = mBinding.getFragment();
		Activity activity = fragment.getActivity();
		if (activity != null) {
			FragmentTransaction transaction = fragment.getChildFragmentManager()
			                                          .beginTransaction()
			                                          .add(mBinding.navigatorContentFl.getId(), EntryFragment.newInstance(activity, entry));


			if (isRoot) {
				transaction.commit();
				return;
			}

			transaction.addToBackStack(null)
			           .commit();
			if (mBinding.menuBar.getNavigationIcon() == null) {
				mBinding.menuBar.setNavigationIcon(AppCompatResources.getDrawable(App.Instance, R.drawable.ic_back));
				mBinding.menuBar.setNavigationOnClickListener(this);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if (mBinding == null) {
			return;
		}
		Fragment fragment = mBinding.getFragment();
		fragment.getChildFragmentManager()
		        .popBackStack();

	}

	@Override
	public void onBackStackChanged() {
		if (mBinding == null) {
			return;
		}
		if(mBinding.getFragment().getChildFragmentManager().getBackStackEntryCount() == 0)
			mBinding.menuBar.setNavigationIcon(null);
	}
}
