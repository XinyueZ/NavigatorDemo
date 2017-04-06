package com.demo.navigator.app;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.demo.navigator.R;
import com.demo.navigator.bus.CloseNavigatorEvent;
import com.demo.navigator.bus.PbDoneEvent;
import com.demo.navigator.bus.PbLoadingEvent;
import com.demo.navigator.databinding.AppBarLayoutBinding;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


public abstract class AppBarActivity extends AppCompatActivity {

	private static final @LayoutRes int LAYOUT = R.layout.activity_appbar;
	private AppBarLayoutBinding mBinding;
	private ActionBarDrawerToggle mDrawerToggle;

	//------------------------------------------------
	//Subscribes, event-handlers
	//------------------------------------------------

	/**
	 * Handler for {@link CloseNavigatorEvent}.
	 *
	 * @param e Event {@link CloseNavigatorEvent}.
	 */
	@SuppressWarnings("unused")
	@Subscribe
	public void onEvent(CloseNavigatorEvent e) {
		getBinding().drawerLayout.closeDrawers();
	}

	/**
	 * Handler for {@link PbLoadingEvent}.
	 *
	 * @param e Event {@link PbLoadingEvent}.
	 */
	@SuppressWarnings("unused")
	@Subscribe
	public void onEvent(PbLoadingEvent e) {
		mBinding.loadingPb.setVisibility(View.VISIBLE);
	}

	/**
	 * Handler for {@link PbDoneEvent}.
	 *
	 * @param e Event {@link PbDoneEvent}.
	 */
	@SuppressWarnings("unused")
	@Subscribe
	public void onEvent(PbDoneEvent e) {
		mBinding.loadingPb.setVisibility(View.GONE);
	}
	//------------------------------------------------

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, LAYOUT);
		setupMain();
		initDrawer();
		setupContent(getBinding().appbarContent);
	}

	@Override
	protected void onDestroy() {
		if (mDrawerToggle != null) {
			getBinding().drawerLayout.removeDrawerListener(mDrawerToggle);
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		EventBus.getDefault()
		        .register(this);
		super.onResume();
		if (mDrawerToggle != null) {
			mDrawerToggle.syncState();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		EventBus.getDefault()
		        .unregister(this);
	}

	/**
	 * Initialize the navigation drawer.
	 */
	private void initDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, getBinding().drawerLayout, R.string.app_name, R.string.app_name);
		getBinding().drawerLayout.addDrawerListener(mDrawerToggle);
	}


	private void setupMain() {
		setSupportActionBar(getBinding().appbar.toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}

	protected abstract void setupContent(@NonNull FrameLayout contentLayout);


	private AppBarLayoutBinding getBinding() {
		return mBinding;
	}

	private boolean isDrawerOpened() {
		return getBinding().drawerLayout.isDrawerOpen(GravityCompat.START) || getBinding().drawerLayout.isDrawerOpen(GravityCompat.END);
	}

	@Override
	public void onBackPressed() {
		if (isDrawerOpened()) {
			getBinding().drawerLayout.closeDrawers();
		} else {
			super.onBackPressed();
		}
	}
}