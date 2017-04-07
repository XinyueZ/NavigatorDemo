package com.demo.navigator.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
public class RetrofitModule {
	@Provides
	Service provideService() {
		retrofit2.Retrofit r = new Retrofit.Builder().baseUrl("http://www.example.com/")
		                                             .addConverterFactory(GsonConverterFactory.create())
		                                             .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		                                             .build();
		MockRetrofit Mock = new MockRetrofit.Builder(r).networkBehavior(NetworkBehavior.create())
		                                               .build();
		return new MockService(Mock.create(Service.class));
	}
}
