package com.demo.navigator.app;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {

    private final App mApp;

    ApplicationModule(App app) {
        mApp = app;
    }

    @Provides
    App provideApp() {
        return mApp;
    }
}