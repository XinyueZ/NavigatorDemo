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


	@Singleton
	@Binds
	@Local
	abstract DsSource provideLocalDataSource(DsLocalSource dsLocalSource);


//
//	@Singleton
//	@Provides
//	@Remote
//	DsSource provideRemoteDataSource(@NonNull App app, @NonNull Service service) {
//		return new DsRemoteSource(app, service);
//	}
//
// 	@Singleton
//	@Provides
//	@Local
//	DsSource provideLocalDataSource(@NonNull App app) {
//		return new DsLocalSource(app);
//	}
}
