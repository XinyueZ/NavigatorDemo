package com.demo.navigator.retrofit;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public final class Backend {
	public static final retrofit2.Retrofit Retrofit = new Retrofit.Builder().baseUrl("http://example.com/")
	                                                                        .addConverterFactory(GsonConverterFactory.create())
	                                                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
	                                                                        .build();

	private static final MockRetrofit Mock = new MockRetrofit.Builder(Backend.Retrofit).networkBehavior(NetworkBehavior.create())
	                                                                                   .build();
	public static Service Instance = new MockService(Mock.create(Service.class));
}
