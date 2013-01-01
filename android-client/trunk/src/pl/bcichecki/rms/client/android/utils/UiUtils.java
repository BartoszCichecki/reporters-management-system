/**
 * Project:   rms-client-android
 * File:      UiUtils.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      29-12-2012
 */

package pl.bcichecki.rms.client.android.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.widget.Toast;

import pl.bcichecki.rms.client.android.R;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UiUtils {

	public static boolean checkInternetConnection(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			return true;
		} else {
			Toast checkYourInternetConnectionToast = Toast.makeText(activity, R.string.activity_login_check_your_internet_connection,
			        Toast.LENGTH_LONG);
			checkYourInternetConnectionToast.show();

			return false;
		}
	}

	public static float convertDpToPixel(Context context, float dp) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public static float convertPixelsToDp(Context context, float px) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}

	public static String getFormattedDateAsString(Date date, Locale locale) {
		return new SimpleDateFormat("HH:mm dd-MM-yyyy", locale).format(date);

	}

	public static boolean validateEmail(String email) {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return Pattern.compile(emailPattern).matcher(email).matches();
	}
}
