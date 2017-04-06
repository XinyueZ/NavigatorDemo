package com.demo.navigator.navigation;


import com.demo.navigator.app.FragmentScoped;
import com.demo.navigator.ds.DsRepositoryComponent;

import dagger.Component;


@FragmentScoped
@Component(dependencies = DsRepositoryComponent.class, modules = NavigatorModule.class)
public interface NavigatorComponent {

	void inject(NavigatorFragment navigatorFragment);
}
