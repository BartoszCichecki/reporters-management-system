
package pl.bcichecki.rms.client.android.activities;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.utils.PojoUtils;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.DevicesRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

public class EditDeviceActivity extends FragmentActivity {

	private static final String TAG = "EditDeviceActivity";

	private static final String EDIT_DEVICE_EXTRA = "EDIT_DEVICE_EXTRA";

	private static final String EDIT_DEVICE_EXTRA_RET = "EDIT_DEVICE_EXTRA_RET";

	private static final int RESULT_CODE_OK = 111;

	private Context context;

	private EditText descriptionText;

	private Device originalDevice;

	private Device deviceCopy;

	private DevicesRestClient devicesRestClient;

	private boolean editDeviceForm = true;

	private EditText nameText;

	private void cancelRequests() {
		if (devicesRestClient != null) {
			devicesRestClient.cancelRequests(this, true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_device);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		context = this;

		devicesRestClient = new DevicesRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		nameText = (EditText) findViewById(R.id.activity_edit_device_name_text);
		descriptionText = (EditText) findViewById(R.id.activity_edit_device_description_text);

		setupFormFromIntent(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_edit_device, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.activity_edit_device_menu_save) {
			performActionSave(item);
			return true;
		}

		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStop() {
		cancelRequests();
		super.onStop();
	}

	private void performActionSave(MenuItem item) {
		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						AppUtils.showCenteredToast(context, R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
			}

			@Override
			public void onFinish() {
				Log.d(TAG, "Finished performing action on " + deviceCopy);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Started performing action on " + deviceCopy);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Action performed successfully. Going back to previous activity...");
				Intent returnIntent = new Intent();
				returnIntent.putExtra(EDIT_DEVICE_EXTRA_RET, originalDevice);
				setResult(RESULT_CODE_OK, returnIntent);
				finish();
			}

		};

		if (editDeviceForm) {
			Log.d(TAG, "Remainder: this is a edit device form!");
			deviceCopy.setName(nameText.getText().toString());
			deviceCopy.setDescription(descriptionText.getText().toString());

			devicesRestClient.updateDevice(deviceCopy, handler);
		} else {
			Log.d(TAG, "Remainder: this is a new device form!");
			deviceCopy = new Device();
			deviceCopy.setName(nameText.getText().toString());
			deviceCopy.setDescription(descriptionText.getText().toString());

			devicesRestClient.createDevice(deviceCopy, handler);
		}
	}

	private void setupFormFromIntent(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				originalDevice = (Device) extras.getSerializable(EDIT_DEVICE_EXTRA);
			} else {
				originalDevice = null;
			}
		} else {
			originalDevice = (Device) savedInstanceState.getSerializable(EDIT_DEVICE_EXTRA);
		}

		deviceCopy = PojoUtils.createDefensiveCopy(originalDevice);

		if (deviceCopy == null) {
			Log.d(TAG, "No device to edit passed. Acting as new device form");
			editDeviceForm = false;
			setTitle(R.string.activity_edit_device_title_new);
		}

		if (deviceCopy != null) {
			Log.d(TAG, "Device to edit passed. Acting as edit device form");
			nameText.setText(deviceCopy.getName());
			descriptionText.setText(deviceCopy.getDescription());
		}
	}

}
