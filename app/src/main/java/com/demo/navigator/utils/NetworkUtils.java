package com.demo.navigator.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

/**
 * Utils for network.
 */
public final class NetworkUtils {
	public static final byte CONNECTION_OFFLINE = 1;
	public static final byte CONNECTION_WIFI = 2;
	public static final byte CONNECTION_ROAMING = 3;
	public static final byte CONNECTION_SLOW = 4;
	public static final byte CONNECTION_FAST = 5;

	private static String sUserId;

	private NetworkUtils() {
	}


	/**
	 * Check if the device is connected to the internet (mobile network or WIFI).
	 */
	public static boolean isOnline(Context _context) {
		boolean online = false;

		TelephonyManager tmanager = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
		if (tmanager != null) {
			if (tmanager.getDataState() == TelephonyManager.DATA_CONNECTED) {
				// Mobile network
				online = true;
			} else {
				// WIFI
				ConnectivityManager cmanager = (ConnectivityManager) _context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (cmanager != null) {
					NetworkInfo info = cmanager.getActiveNetworkInfo();
					if (info != null) {
						online = info.isConnected();
					}
				}
			}
		}

		return online;
	}


	/**
	 * Evaluate the current network connection and return the corresponding type, e.g. CONNECTION_WIFI.
	 */
	public static byte getCurrentNetworkType(Context _context) {
		NetworkInfo netInfo = ((ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (netInfo == null) {
			return CONNECTION_OFFLINE;
		}

		if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return CONNECTION_WIFI;
		}

		if (netInfo.isRoaming()) {
			return CONNECTION_ROAMING;
		}

		if (!(netInfo.getType() == ConnectivityManager.TYPE_MOBILE
				&& (netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_UMTS
				|| netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_HSDPA
				|| netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_HSUPA
				|| netInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_HSPA
				|| netInfo.getSubtype() == 13 // NETWORK_TYPE_LTE
				|| netInfo.getSubtype() == 15))) // NETWORK_TYPE_HSPAP
		{

			return CONNECTION_SLOW;
		}

		return CONNECTION_FAST;
	}


	/**
	 * Return the current IP address of the device or null if it could not be found. </br> Call of this method is equal
	 * to calling {@link #getIpAddress(boolean) getIpAddress} with _ipV4only false. The returned result could be the
	 * IPv6 address of device (depends on device).
	 *
	 * @return IP Address as String
	 */
	public static String getIpAddress() {
		return getIpAddress(false);
	}

	/**
	 * Return the current IP address of the device or null if it could not be found
	 *
	 * @param _ipV4only
	 * 		if true ignores IPv6 addresses
	 *
	 * @return IP Address as String
	 */
	public static String getIpAddress(boolean _ipV4only) {
		String result = null;
		try {
			for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			     interfaces.hasMoreElements(); ) {
				NetworkInterface iface = interfaces.nextElement();
				for (Enumeration<InetAddress> adresses = iface.getInetAddresses(); adresses.hasMoreElements(); ) {
					InetAddress ip = adresses.nextElement();
					if (_ipV4only && ip instanceof Inet6Address) {
						continue;
					}
					if (!ip.isLoopbackAddress()) {
						result = ip.getHostAddress();
						return result;
					}
				}
			}
		} catch (SocketException _e) {
			LL.e("Could not find device's ip address");
		}
		return result;
	}


	/**
	 * Return a MD5 hash of the device id.
	 */
	public static synchronized String getUserId(Context _context) {
		if (sUserId == null) {
			TelephonyManager tm = (TelephonyManager) _context.getSystemService(Context.TELEPHONY_SERVICE);
			String id = tm.getDeviceId();
			try {
				MessageDigest digester = MessageDigest.getInstance("MD5");
				digester.update(id.getBytes());
				byte[] digest = digester.digest();

				// Convert to hex string
				BigInteger converter = new BigInteger(1, digest);
				String md5 = converter.toString(16);
				while (md5.length() < 32) {
					md5 = "0" + md5;
				}
				sUserId = md5;
			} catch (NoSuchAlgorithmException _e) {
				LL.e("Could not find MD5");
			}
		}
		return sUserId;
	}

	/**
	 * Test for whether Airplane-Mode has been on or off.
	 *
	 * @param context
	 * 		A context object.
	 *
	 * @return True if airplane on, false if off.
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static boolean isAirplaneModeOn(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return Settings.System.getInt(context.getContentResolver(),
					Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
		} else {
			return Settings.System.getInt(context.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		}
	}

	/**
	 * Open setting for network.
	 * @param _context A context object.
	 */
	public static void openNetworkSetting(Context _context) {
		_context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	}
}
