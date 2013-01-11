/**
 * Project:   rms-client-android
 * File:      EditEventActivity.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      11-01-2013
 */

package pl.bcichecki.rms.client.android.activities;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.DevicesListComparator;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.UsersListComparator;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.AddressData;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.model.impl.Event;
import pl.bcichecki.rms.client.android.model.impl.EventType;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.model.utils.PojoUtils;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.DevicesRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.EventsRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UsersRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

public class EditEventActivity extends FragmentActivity {

	private static final String TAG = "EditEventActivity";

	private static final String EDIT_EVENT_EXTRA = "EDIT_EVENT_EXTRA";

	private static final String EDIT_EVENT_EXTRA_RET = "EDIT_EVENT_EXTRA_RET";

	private static final int RESULT_CODE_OK = 113;

	private Context context;

	private Event originalEvent;

	private Event eventCopy;

	private EventsRestClient eventsRestClient;

	private UsersRestClient usersRestClient;

	private DevicesRestClient devicesRestClient;

	private EditText titleText;

	private Button startsOnDateButton;

	private Button startsOnTimeButton;

	private Button endsOnDateButton;

	private Button endsOnTimeButton;

	private EditText descriptionText;

	private EditText participantsText;

	private Button participantsAddButton;

	private EditText devicesText;

	private Button devicesAddButton;

	private boolean editEventForm = true;

	private AlertDialog participantsListDialog;

	private AlertDialog devicesListDialog;

	private List<User> participants = new ArrayList<User>();

	private List<User> pickedParticipants = new ArrayList<User>();

	private String[] usernames;

	private boolean[] chosenParticipants;

	private List<Device> devices = new ArrayList<Device>();

	private List<Device> pickedDevices = new ArrayList<Device>();

	private String[] deviceNames;

	private boolean[] chosenDevices;

	private Date startDate;

	private Date endDate;

	private DateFormat dateFormat;

	private DateFormat timeFormat;

	private RadioButton meetingRdbtn;

	private RadioButton interviewRdbtn;

	private void cancelRequests() {
		if (eventsRestClient != null) {
			eventsRestClient.cancelRequests(this, true);
		}
		if (usersRestClient != null) {
			usersRestClient.cancelRequests(this, true);
		}
		if (devicesRestClient != null) {
			devicesRestClient.cancelRequests(this, true);
		}
	}

	private void createDevicesListDialog() {
		deviceNames = new String[devices.size()];
		chosenDevices = new boolean[devices.size()];

		for (int i = 0; i < devices.size(); i++) {
			deviceNames[i] = devices.get(i).getName();
		}

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.activity_edit_event_pick_devices);
		dialogBuilder.setMultiChoiceItems(deviceNames, chosenDevices, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					pickedDevices.add(devices.get(which));
					devicesText.setError(null);
				} else {
					pickedDevices.remove(devices.get(which));
				}
				updateDevicesText();
			}
		});
		dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		devicesListDialog = dialogBuilder.create();
	}

	private void createRecipentsListDialog() {
		usernames = new String[participants.size()];
		chosenParticipants = new boolean[participants.size()];

		for (int i = 0; i < participants.size(); i++) {
			usernames[i] = participants.get(i).getUsername();
		}

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.activity_edit_event_pick_participants);
		dialogBuilder.setMultiChoiceItems(usernames, chosenParticipants, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					pickedParticipants.add(participants.get(which));
					participantsText.setError(null);
				} else {
					pickedParticipants.remove(participants.get(which));
				}
				updateParticipantsText();
			}
		});
		dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		participantsListDialog = dialogBuilder.create();
	}

	private void loadDevices() {
		Log.d(TAG, "Downloading devices list...");

		if (!AppUtils.checkInternetConnection(this)) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		devicesRestClient.getAllDevices(new GsonHttpResponseHandler<List<Device>>(new TypeToken<List<Device>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving devices failed. Quitting activity... [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						AppUtils.showCenteredToast(context, R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
				finish();
			}

			@Override
			public void onFinish() {
				Log.d(TAG, "Retrieving participants finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving participants started.");
			}

			@Override
			public void onSuccess(int statusCode, List<Device> retrievedDevices) {
				Log.d(TAG, "Retrieving devoces successful. Retrieved " + retrievedDevices.size() + " objects.");

				devices.clear();
				devices.addAll(retrievedDevices);
				Collections.sort(devices, new DevicesListComparator());

				createDevicesListDialog();
				updateDevicesPicker();

				devicesAddButton.setEnabled(true);
			}
		});

	}

	private void loadUsers() {
		Log.d(TAG, "Downloading participants list...");

		if (!AppUtils.checkInternetConnection(this)) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		usersRestClient.getAllUsers(new GsonHttpResponseHandler<List<User>>(new TypeToken<List<User>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving participants failed. Quitting activity... [error=" + error + ", content=" + content + "]");
				if (error instanceof HttpResponseException) {
					if (((HttpResponseException) error).getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
						AppUtils.showCenteredToast(context, R.string.general_unathorized_error_message_title, Toast.LENGTH_LONG);
					} else {
						AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
					}
				} else {
					AppUtils.showCenteredToast(context, R.string.general_unknown_error_message_title, Toast.LENGTH_LONG);
				}
				finish();
			}

			@Override
			public void onFinish() {
				Log.d(TAG, "Retrieving participants finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving participants started.");
			}

			@Override
			public void onSuccess(int statusCode, List<User> retrievedUsers) {
				Log.d(TAG, "Retrieving participants successful. Retrieved " + retrievedUsers.size() + " objects.");

				participants.clear();
				participants.addAll(retrievedUsers);
				Collections.sort(participants, new UsersListComparator());

				createRecipentsListDialog();
				updateParticipantsPicker();

				participantsAddButton.setEnabled(true);
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_event);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		context = this;

		eventsRestClient = new EventsRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		usersRestClient = new UsersRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		devicesRestClient = new DevicesRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		titleText = (EditText) findViewById(R.id.activity_edit_event_title_text);
		meetingRdbtn = (RadioButton) findViewById(R.id.activity_edit_event_type_rdbtn_meeting);
		interviewRdbtn = (RadioButton) findViewById(R.id.activity_edit_event_type_rdbtn_interview);
		startsOnTimeButton = (Button) findViewById(R.id.activity_edit_event_starts_on_time);
		startsOnDateButton = (Button) findViewById(R.id.activity_edit_event_starts_on_date);
		endsOnTimeButton = (Button) findViewById(R.id.activity_edit_event_ends_on_time);
		endsOnDateButton = (Button) findViewById(R.id.activity_edit_event_ends_on_date);
		descriptionText = (EditText) findViewById(R.id.activity_edit_event_description_text);
		participantsText = (EditText) findViewById(R.id.activity_edit_event_participants_text);
		participantsAddButton = (Button) findViewById(R.id.activity_edit_event_participants_add);
		devicesText = (EditText) findViewById(R.id.activity_edit_event_devices_text);
		devicesAddButton = (Button) findViewById(R.id.activity_edit_event_devices_add);

		dateFormat = android.text.format.DateFormat.getDateFormat(context);
		timeFormat = android.text.format.DateFormat.getTimeFormat(context);

		titleText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((EditText) v).setError(null);
			}
		});
		descriptionText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((EditText) v).setError(null);
			}
		});

		startsOnTimeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showStartsOnTimeDialog();
			}

		});
		startsOnDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showStartsOnDateDialog();
			}
		});
		endsOnTimeButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showEndsOnTimeDialog();
			}
		});
		endsOnDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showEndsOnDateDialog();
			}
		});

		participantsAddButton.setEnabled(false);
		participantsAddButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (participantsListDialog != null) {
					participantsListDialog.show();
				}
			}
		});
		devicesAddButton.setEnabled(false);
		devicesAddButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (devicesListDialog != null) {
					devicesListDialog.show();
				}
			}
		});

		setUpForm(savedInstanceState);

		loadUsers();
		loadDevices();

		updateStartDateButtons();
		updateEndDateButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_edit_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.activity_edit_event_menu_save) {
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
		if (StringUtils.isBlank(titleText.getText().toString())) {
			titleText.setError(getString(R.string.activity_edit_event_field_is_required));
			return;
		}
		if (StringUtils.isBlank(descriptionText.getText().toString())) {
			descriptionText.setError(getString(R.string.activity_edit_event_field_is_required));
			return;
		}

		if (startDate.after(endDate)) {
			AppUtils.showCenteredToast(this, R.string.activity_edit_event_error_start_after_end, Toast.LENGTH_LONG);
			return;
		}

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
				Log.d(TAG, "Finished performing action on " + eventCopy);
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Started performing action on " + eventCopy);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Action performed successfully. Going back to previous activity...");
				Intent returnIntent = new Intent();
				returnIntent.putExtra(EDIT_EVENT_EXTRA_RET, originalEvent);
				setResult(RESULT_CODE_OK, returnIntent);
				finish();
			}

		};

		if (editEventForm) {
			Log.d(TAG, "Remainder: this is a edit event form!");
		} else {
			Log.d(TAG, "Remainder: this is a new event form!");
			eventCopy = new Event();
		}

		eventCopy.setTitle(titleText.getText().toString());
		eventCopy.setStartDate(startDate);
		eventCopy.setEndDate(endDate);
		eventCopy.setDescription(descriptionText.getText().toString());
		eventCopy.setParticipants(new HashSet<User>(pickedParticipants));
		eventCopy.setDevices(new HashSet<Device>(pickedDevices));

		if (meetingRdbtn.isChecked()) {
			eventCopy.setType(EventType.MEETING);
		}
		if (interviewRdbtn.isChecked()) {
			eventCopy.setType(EventType.INTERVIEW);
		}

		if (eventCopy.getAddress() == null) {
			eventCopy.setAddress(new AddressData());
		}

		if (editEventForm) {
			eventsRestClient.updateMyEvent(eventCopy, handler);
		} else {
			eventsRestClient.createEvent(eventCopy, handler);
		}
	}

	protected void setUpForm(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				originalEvent = (Event) extras.getSerializable(EDIT_EVENT_EXTRA);
			} else {
				originalEvent = null;
			}
		} else {
			originalEvent = (Event) savedInstanceState.getSerializable(EDIT_EVENT_EXTRA);
		}

		eventCopy = PojoUtils.createDefensiveCopy(originalEvent);

		if (eventCopy == null) {
			Log.d(TAG, "No event to edit passed. Acting as new event form");

			editEventForm = false;

			Date now = new Date();
			startDate = DateUtils.addHours(now, 1);
			endDate = DateUtils.addHours(now, 2);

			setTitle(R.string.activity_edit_event_title_new);

		} else {
			Log.d(TAG, "Event to edit passed. Acting as edit event form");

			titleText.setText(eventCopy.getTitle());
			descriptionText.setText(eventCopy.getDescription());

			if (eventCopy.getType().equals(EventType.MEETING)) {
				meetingRdbtn.setChecked(true);
			}
			if (eventCopy.getType().equals(EventType.INTERVIEW)) {
				interviewRdbtn.setChecked(true);
			}

			startDate = eventCopy.getStartDate();
			endDate = eventCopy.getEndDate();

			pickedParticipants.clear();
			pickedParticipants.addAll(eventCopy.getParticipants());
			pickedDevices.clear();
			pickedDevices.addAll(eventCopy.getDevices());

			updateParticipantsText();
			updateDevicesText();
		}
	}

	private void showEndsOnDateDialog() {
		Calendar endDateCalendar = Calendar.getInstance();
		endDateCalendar.setTime(endDate);

		OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				endDate = DateUtils.setYears(endDate, year);
				endDate = DateUtils.setMonths(endDate, monthOfYear);
				endDate = DateUtils.setDays(endDate, dayOfMonth);
				updateEndDateButtons();
			}
		};

		DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, endDateCalendar.get(Calendar.YEAR),
		        endDateCalendar.get(Calendar.MONTH), endDateCalendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.setTitle(R.string.activity_edit_event_pick_end_date);
		datePickerDialog.show();
	}

	private void showEndsOnTimeDialog() {
		Calendar endTimeCalendar = Calendar.getInstance();
		endTimeCalendar.setTime(endDate);

		boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(context);

		OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				endDate = DateUtils.setHours(endDate, hourOfDay);
				endDate = DateUtils.setMinutes(endDate, minute);
				updateEndDateButtons();
			}
		};

		TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener, endTimeCalendar.get(Calendar.HOUR_OF_DAY),
		        endTimeCalendar.get(Calendar.MINUTE), is24HourFormat);
		timePickerDialog.setTitle(R.string.activity_edit_event_pick_end_time);
		timePickerDialog.show();
	}

	private void showStartsOnDateDialog() {
		Calendar startDateCalendar = Calendar.getInstance();
		startDateCalendar.setTime(startDate);

		OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				long diff = endDate.getTime() - startDate.getTime();

				startDate = DateUtils.setYears(startDate, year);
				startDate = DateUtils.setMonths(startDate, monthOfYear);
				startDate = DateUtils.setDays(startDate, dayOfMonth);
				updateStartDateButtons();

				endDate.setTime(startDate.getTime() + diff);
				updateEndDateButtons();
			}
		};

		DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, startDateCalendar.get(Calendar.YEAR),
		        startDateCalendar.get(Calendar.MONTH), startDateCalendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.setTitle(R.string.activity_edit_event_pick_start_date);
		datePickerDialog.show();
	}

	private void showStartsOnTimeDialog() {
		Calendar startTimeCalendar = Calendar.getInstance();
		startTimeCalendar.setTime(startDate);

		boolean is24HourFormat = android.text.format.DateFormat.is24HourFormat(context);

		OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				long diff = endDate.getTime() - startDate.getTime();

				startDate = DateUtils.setHours(startDate, hourOfDay);
				startDate = DateUtils.setMinutes(startDate, minute);
				updateStartDateButtons();

				endDate.setTime(startDate.getTime() + diff);
				updateEndDateButtons();
			}
		};

		TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener, startTimeCalendar.get(Calendar.HOUR_OF_DAY),
		        startTimeCalendar.get(Calendar.MINUTE), is24HourFormat);
		timePickerDialog.setTitle(R.string.activity_edit_event_pick_start_time);
		timePickerDialog.show();

	}

	private void updateDevicesPicker() {
		for (int i = 0; i < devices.size(); i++) {
			if (pickedDevices.contains(devices.get(i))) {
				chosenDevices[i] = true;
			}
		}
	}

	private void updateDevicesText() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<Device> pickedDevicesIterator = pickedDevices.iterator(); pickedDevicesIterator.hasNext();) {
			Device pickedDevice = pickedDevicesIterator.next();
			sb.append(pickedDevice.getName());
			if (pickedDevicesIterator.hasNext()) {
				sb.append(", ");
			}
		}
		devicesText.setText(sb.toString());
	}

	private void updateEndDateButtons() {
		endsOnTimeButton.setText(timeFormat.format(endDate));
		endsOnDateButton.setText(dateFormat.format(endDate));
	}

	private void updateParticipantsPicker() {
		for (int i = 0; i < participants.size(); i++) {
			if (pickedParticipants.contains(participants.get(i))) {
				chosenParticipants[i] = true;
			}
		}
	}

	private void updateParticipantsText() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<User> pickedUsersIterator = pickedParticipants.iterator(); pickedUsersIterator.hasNext();) {
			User pickedUser = pickedUsersIterator.next();
			sb.append(pickedUser.getUsername());
			if (pickedUsersIterator.hasNext()) {
				sb.append(", ");
			}
		}
		participantsText.setText(sb.toString());
	}

	private void updateStartDateButtons() {
		startsOnTimeButton.setText(timeFormat.format(startDate));
		startsOnDateButton.setText(dateFormat.format(startDate));
	}

}
