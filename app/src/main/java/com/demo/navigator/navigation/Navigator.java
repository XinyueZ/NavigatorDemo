package com.demo.navigator.navigation;


import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.bus.EntryClickEvent;
import com.demo.navigator.bus.OpenUriEvent;
import com.demo.navigator.navigation.ui.EntryFragment;
import com.demo.navigator.utils.CustomTabUtils;
import com.demo.navigator.databinding.FragmentNavigatorBinding;
import com.demo.navigator.ds.DsRepository;
import com.demo.navigator.ds.DsSource;
import com.demo.navigator.ds.model.Entry;
import com.demo.navigator.utils.Utils;

import java.util.List;
import java.util.Stack;

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
	private final Stack<Entry> mStackedEntries = new Stack<>();

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
		setupMenuBar();
		mView.showEntry();
	}

	public void load() {
		mDsRepository.loadEntry(new DsSource.EntryLoadedCallback() {
			@Override
			public void onLoaded(@NonNull Entry entry) {
				if (mBinding == null || !mBinding.getFragment()
				                                 .isAdded()) {
					return;
				}
				mBinding.navigatorContentFl.removeAllViews();
				navigateTo(entry, true);
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
		switch (e.getEntry()
		         .getType()) {
			case "link":
				//Normal link
				EventBus.getDefault()
				        .post(new OpenUriEvent(Uri.parse(e.getEntry()
				                                          .getUrl())));
				EventBus.getDefault()
				        .post(new CloseNavigatorEvent());
				return;
			case "external-link":
				//External link, then to browser
				EventBus.getDefault()
				        .post(new CloseNavigatorEvent());

				CustomTabUtils.openWeb(mBinding.getFragment(),
				                       e.getEntry()
				                        .getLabel(),
				                       Uri.parse(e.getEntry()
				                                  .getUrl()));
				return;
			default:
				//Navigate to next entry
				navigateTo(e.getEntry(), false);
				mBinding.menuBar.setTitle(e.getEntry()
				                           .getLabel());
				break;
		}
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

	private void navigateTo(@NonNull Entry entry, boolean isRoot) {
		final List<Entry> entryList = Utils.rebuildForSections(entry.getChildren());
		Entry rebuiltEntry = new Entry(entry.getType(), entry.getLabel(), entry.getUrl(), entryList);

		if (mBinding == null) {
			return;
		}
		Fragment fragment = mBinding.getFragment();
		Activity activity = fragment.getActivity();
		if (activity != null) {
			FragmentTransaction transaction = fragment.getChildFragmentManager()
			                                          .beginTransaction()
			                                          .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right )
			                                          .replace(mBinding.navigatorContentFl.getId(), EntryFragment.newInstance(activity, rebuiltEntry));


			mStackedEntries.push(rebuiltEntry);

			if (isRoot) {
				transaction.commit();
				return;
			}

			transaction.addToBackStack(null)
			           .commit();
			if (mBinding.menuBar.getNavigationIcon() == null) {
				mBinding.menuBar.setNavigationIcon(AppCompatResources.getDrawable(App.Instance, R.drawable.ic_back));
				mBinding.menuBar.setNavigationContentDescription(R.string.navigate_up);
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
		mStackedEntries.pop();
		fragment.getChildFragmentManager()
		        .popBackStack();
		mBinding.menuBar.setTitle(mStackedEntries.peek()
		                                         .getLabel());
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
			mBinding.menuBar.setTitle(R.string.main_menu);
		}
	}
}
