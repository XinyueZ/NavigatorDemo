package com.demo.navigator.ds;


import com.demo.navigator.app.ApplicationModule;
import com.demo.navigator.retrofit.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = { DsRepositoryModule.class,
                       ApplicationModule.class,
                       RetrofitModule.class })
public interface DsRepositoryComponent {

	DsRepository getDsRepository();
}
