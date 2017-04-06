package com.demo.navigator.navigation;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.ViewDataBinding;
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
import android.widget.Toast;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.bus.EntryClickEvent;
import com.demo.navigator.bus.OpenUriEvent;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.ds.DsRepository;
import com.demo.navigator.ds.DsSource;
import com.demo.navigator.ds.model.Entry;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public final class Navigator implements Toolbar.OnMenuItemClickListener,
                                        View.OnClickListener,
                                        FragmentManager.OnBackStackChangedListener,
                                        NavigatorContract.Presenter {
	private @Nullable FragmentNavigatorBinding mBinding;
	private final DsRepository mDsRepository;
	private final NavigatorContract.View mView;


	@Inject
	Navigator(DsRepository dsRepository, NavigatorContract.View view) {
		mDsRepository = dsRepository;
		mView = view;
	}


	@Inject
	void onReadySetPresenter() {
		mView.setPresenter(this);
	}

	@Override
	public void start(ViewDataBinding binding) {
		mBinding = (FragmentNavigatorBinding) binding;
		mView.showEntry();
	}

	void load() {
		mDsRepository.loadEntry(new DsSource.EntryLoadedCallback() {
			@Override
			public void onLoaded(@NonNull Entry entry) {
				if (mBinding == null) {
					return;
				}
				setupMenuBar();
				navigateEntry(entry, true);
				mBinding.getFragment()
				        .getChildFragmentManager()
				        .addOnBackStackChangedListener(Navigator.this);
			}
		});
	}


	/**
	 * Handler for {@link  EntryClickEvent}.
	 *
	 * @param e Event {@link EntryClickEvent}.
	 */
	@SuppressWarnings("unused")
	@Subscribe
	public void onEvent(EntryClickEvent e) {
		if (mBinding == null) {
			return;
		}

		//Normal link
		if (TextUtils.equals(e.getEntry()
		                      .getType(), "link")) {
			EventBus.getDefault()
			        .post(new OpenUriEvent(Uri.parse(e.getEntry()
			                                          .getUrl())));
			EventBus.getDefault()
			        .post(new CloseNavigatorEvent());
			return;
		}

		//External link, then to browser
		if (TextUtils.equals(e.getEntry()
		                      .getType(), "external-link")) {

			EventBus.getDefault()
			        .post(new CloseNavigatorEvent());

			try {
				Intent myIntent = new Intent(Intent.ACTION_VIEW,
				                             Uri.parse(e.getEntry()
				                                        .getUrl()));
				mBinding.getFragment()
				        .startActivity(myIntent);
			} catch (ActivityNotFoundException ex) {
				try {
					Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
					mBinding.getFragment()
					        .startActivity(myIntent);
				} catch (ActivityNotFoundException exx) {
					Toast.makeText(App.Instance, "Cannot open external-link, cannot find browser.", Toast.LENGTH_SHORT)
					     .show();
				}
			}
			return;
		}

		//Navigate to next entry
		mBinding.navigatorContentFl.show(0, 0, 800);
		navigateEntry(e.getEntry(), false);
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
		if (mBinding.getFragment()
		            .getChildFragmentManager()
		            .getBackStackEntryCount() == 0) {
			mBinding.menuBar.setNavigationIcon(null);
		}
	}
}
