package com.demo.navigator.retrofit;

import com.demo.navigator.R;
import com.demo.navigator.app.App;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {
	@Provides
	Service provideService() {
		Retrofit r = new Retrofit.Builder().baseUrl(App.Instance.getString(R.string.navi_url))
		                                   .addConverterFactory(GsonConverterFactory.create())
		                                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		                                   .client(new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			                                   @Override
			                                   public okhttp3.Response intercept(Chain chain) throws IOException {
				                                   Request original = chain.request();

				                                   Request request = original.newBuilder()
				                                                             .header("x-api-key", "hz7JPdKK069Ui1TRxxd1k8BQcocSVDkj219DVzzD")
				                                                             .method(original.method(), original.body())
				                                                             .build();

				                                   return chain.proceed(request);
			                                   }
		                                   })
		                                                                     .build())
		                                   .build();
		return r.create(Service.class);
	}
}
