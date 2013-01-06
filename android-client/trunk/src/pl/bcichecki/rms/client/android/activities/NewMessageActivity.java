
package pl.bcichecki.rms.client.android.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.fragments.listAdapters.comparators.UsersListComparator;
import pl.bcichecki.rms.client.android.holders.SharedPreferencesWrapper;
import pl.bcichecki.rms.client.android.holders.UserProfileHolder;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.model.impl.MessageRecipent;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.services.clients.restful.https.GsonHttpResponseHandler;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.MessagesRestClient;
import pl.bcichecki.rms.client.android.services.clients.restful.impl.UsersRestClient;
import pl.bcichecki.rms.client.android.utils.AppUtils;

public class NewMessageActivity extends FragmentActivity {

	private static final String TAG = "EditDeviceActivity";

	private static final int RESULT_CODE_OK = 111;

	private static final String MESSAGE_EXTRA = "MESSAGE_EXTRA";

	private Context context;

	private MessagesRestClient messagesRestClient;

	private UsersRestClient usersRestClient;

	private AlertDialog recipentsListDialog;

	private EditText subjectEditText;

	private EditText recipentsEditText;

	private EditText contentEditText;

	private Button recipentsAddButton;

	private List<User> users = new ArrayList<User>();

	private List<User> pickedUsers = new ArrayList<User>();

	private String[] usernames;

	private boolean[] chosenUsers;

	private Message replyMessage;

	private void cancelRequests() {
		if (messagesRestClient != null) {
			messagesRestClient.cancelRequests(this, true);
		}
	}

	private void createRecipentsListDialog() {
		usernames = new String[users.size()];
		chosenUsers = new boolean[users.size()];

		for (int i = 0; i < users.size(); i++) {
			usernames[i] = users.get(i).getUsername();
		}

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
		dialogBuilder.setTitle(R.string.activity_new_message_pick_recipents);
		dialogBuilder.setMultiChoiceItems(usernames, chosenUsers, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					pickedUsers.add(users.get(which));
					recipentsEditText.setError(null);
				} else {
					pickedUsers.remove(users.get(which));
				}
				updateRecipentsEditText();
			}
		});
		dialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		recipentsListDialog = dialogBuilder.create();
	}

	private void loadUsers() {
		Log.d(TAG, "Downloading users list...");

		if (!AppUtils.checkInternetConnection(this)) {
			Log.d(TAG, "There is NO network connected!");
			return;
		}

		usersRestClient.getAllUsers(new GsonHttpResponseHandler<List<User>>(new TypeToken<List<User>>() {
		}.getType(), true) {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Retrieving users failed. Quitting activity... [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Retrieving users finished.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Retrieving users started.");
			}

			@Override
			public void onSuccess(int statusCode, List<User> retrievedUsers) {
				Log.d(TAG, "Retrieving users successful. Retrieved " + retrievedUsers.size() + " objects.");

				users.clear();
				users.addAll(retrievedUsers);
				Collections.sort(users, new UsersListComparator());

				createRecipentsListDialog();

				recipentsAddButton.setEnabled(true);
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		context = this;

		messagesRestClient = new MessagesRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());
		usersRestClient = new UsersRestClient(this, UserProfileHolder.getUsername(), UserProfileHolder.getPassword(),
		        SharedPreferencesWrapper.getServerRealm(), SharedPreferencesWrapper.getServerAddress(),
		        SharedPreferencesWrapper.getServerPort(), SharedPreferencesWrapper.getWebserviceContextPath());

		subjectEditText = (EditText) findViewById(R.id.activity_new_message_subject_text);
		recipentsEditText = (EditText) findViewById(R.id.activity_new_message_recipents_text);
		recipentsAddButton = (Button) findViewById(R.id.activity_new_message_recipents_add);
		contentEditText = (EditText) findViewById(R.id.activity_new_message_content_text);

		subjectEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((EditText) v).setError(null);
			}
		});
		contentEditText.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((EditText) v).setError(null);
			}
		});

		recipentsAddButton.setEnabled(false);
		recipentsAddButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (recipentsListDialog != null) {
					recipentsListDialog.show();
				}
			}
		});

		if (savedInstanceState == null) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				replyMessage = (Message) extras.getSerializable(MESSAGE_EXTRA);
			}
		} else {
			replyMessage = (Message) savedInstanceState.getSerializable(MESSAGE_EXTRA);
		}

		if (replyMessage == null) {
			Log.d(TAG, "External recipent not received! Users will be loaded...");
			loadUsers();
		} else {
			Log.d(TAG, "External recipent received! Adding users is not possible.");
			setUpForm();
			updateRecipentsEditText();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.activity_new_message_menu_send) {
			performActionSend(item);
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

	private void performActionSend(MenuItem item) {
		if (StringUtils.isBlank(subjectEditText.getText().toString())) {
			subjectEditText.setError(getString(R.string.activity_new_message_field_is_required));
			return;
		}
		if (pickedUsers == null || CollectionUtils.isEmpty(pickedUsers)) {
			recipentsEditText.setError(getString(R.string.activity_new_message_field_is_required));
			return;
		}
		if (StringUtils.isBlank(contentEditText.getText().toString())) {
			contentEditText.setError(getString(R.string.activity_new_message_field_is_required));
			return;
		}

		final Message message = new Message();
		message.setSubject(subjectEditText.getText().toString());
		message.setContent(contentEditText.getText().toString());

		Set<MessageRecipent> recipents = new HashSet<MessageRecipent>();
		for (User pickedUser : pickedUsers) {
			MessageRecipent recipent = new MessageRecipent();
			recipent.setRecipent(pickedUser);
			recipents.add(recipent);
		}
		message.setRecipents(recipents);

		AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable error, String content) {
				Log.d(TAG, "Error sending message! [error=" + error + ", content=" + content + "]");
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
				Log.d(TAG, "Finished sending message.");
			}

			@Override
			public void onStart() {
				Log.d(TAG, "Started sending message " + message);
			}

			@Override
			public void onSuccess(int statusCode, String content) {
				Log.d(TAG, "Message sent successfully. Going back to previous activity...");
				Intent intent = new Intent();
				setResult(RESULT_CODE_OK, intent);
				finish();
			}

		};
		messagesRestClient.sendMessage(message, handler);
	}

	private void setUpForm() {
		subjectEditText.setText(replyMessage.getSubject());
		for (MessageRecipent messageRecipent : replyMessage.getRecipents()) {
			pickedUsers.add(messageRecipent.getRecipent());
		}
	}

	private void updateRecipentsEditText() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<User> pickedUsersIterator = pickedUsers.iterator(); pickedUsersIterator.hasNext();) {
			User pickedUser = pickedUsersIterator.next();
			sb.append(pickedUser.getUsername());
			if (pickedUsersIterator.hasNext()) {
				sb.append(", ");
			}
		}
		recipentsEditText.setText(sb.toString());
	}

}
