package com.demo.navigator.ds;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;


@Module
abstract class DsRepositoryModule {

	@Singleton
	@Binds
	@Remote
	abstract DsSource provideRemoteDataSource(DsRemoteSource dsRemoteSource);
}
