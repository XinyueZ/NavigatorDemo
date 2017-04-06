package com.demo.navigator.ds;


import com.demo.navigator.app.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = { DsRepositoryModule.class, ApplicationModule.class})
public interface DsRepositoryComponent {

    DsRepository getDsRepository();
}
