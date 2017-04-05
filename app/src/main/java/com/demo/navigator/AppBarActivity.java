package com.demo.navigator;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.demo.navigator.databinding.AppBarLayoutBinding;


public abstract class AppBarActivity extends AppCompatActivity {

	private static final @LayoutRes int LAYOUT = R.layout.activity_appbar;
	private AppBarLayoutBinding mBinding;
	private ActionBarDrawerToggle mDrawerToggle;


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
		super.onResume();
		if (mDrawerToggle != null) {
			mDrawerToggle.syncState();
		}
	}

	/**
	 * Initialize the navigation drawer.
	 */
	private void initDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, getBinding().drawerLayout, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
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

	protected final void setupFragment(@IdRes int container, @NonNull Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
		                           .replace(container, fragment)
		                           .commit();
	}


	protected final void setupFragment(@NonNull Fragment fragment) {
		setupFragment(R.id.appbar_content, fragment);
	}


	protected AppBarLayoutBinding getBinding() {
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
