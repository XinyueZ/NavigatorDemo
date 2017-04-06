/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱。

package com.demo.navigator.app;

import android.support.multidex.MultiDexApplication;

import com.demo.navigator.ds.DaggerDsRepositoryComponent;
import com.demo.navigator.ds.DsRepositoryComponent;


/**
 * The app-object of the project.
 *
 * @author Xinyue Zhao
 */
public final class App extends MultiDexApplication {

	/**
	 * Application's instance.
	 */
	@SuppressWarnings("WeakerAccess") public static App Instance;


	{
		Instance = this;
	}




	private DsRepositoryComponent mRepositoryComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		mRepositoryComponent = DaggerDsRepositoryComponent.builder()
		                                                  .applicationModule(new ApplicationModule((getApplicationContext())))
		                                                  .build();

	}


	public DsRepositoryComponent getRepositoryComponent() {
		return mRepositoryComponent;
	}
}
