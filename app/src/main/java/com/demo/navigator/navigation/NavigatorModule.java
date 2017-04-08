package com.demo.navigator.navigation;

import android.support.annotation.NonNull;

import com.demo.navigator.databinding.FragmentNavigatorBinding;

import dagger.Module;
import dagger.Provides;


@Module
public class NavigatorModule {

	private final NavigatorContract.View mView;
	private final FragmentNavigatorBinding mBinding;

	public NavigatorModule(@NonNull NavigatorContract.View view, @NonNull FragmentNavigatorBinding binding) {
		mView = view;
		mBinding = binding;
	}

	@Provides
	NavigatorContract.View provideNavigatorView() {
		return mView;
	}

	@Provides
	FragmentNavigatorBinding provideFragmentNavigatorBinding() {
		return mBinding;
	}
}
