package com.demo.navigator.navigation;

import dagger.Module;
import dagger.Provides;


@Module
public class NavigatorModule {

    private final NavigatorContract.View mView;

    public NavigatorModule(NavigatorContract.View view) {
        mView = view;
    }

    @Provides
    NavigatorContract.View provideNavigatorView() {
        return mView;
    }
}
