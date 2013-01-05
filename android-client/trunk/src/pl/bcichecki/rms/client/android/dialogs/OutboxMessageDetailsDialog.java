/**
 * Project:   rms-client-android
 * File:      DeviceDetailsDialog.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      05-01-2013
 */

package pl.bcichecki.rms.client.android.dialogs;

import java.util.Iterator;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.model.impl.Message;
import pl.bcichecki.rms.client.android.model.impl.MessageRecipent;
import pl.bcichecki.rms.client.android.utils.AppUtils;

/**
 * @author Bartosz Cichecki
 * 
 */
public class OutboxMessageDetailsDialog extends DialogFragment {

	private Message message;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (message == null) {
			throw new IllegalStateException("Message has not been set!");
		}
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		dialogBuilder.setTitle(R.string.dialog_outbox_message_details_title);
		dialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_outbox_message_details, null));
		dialogBuilder.setPositiveButton(R.string.general_close, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Nothing to do...
			}
		});

		AlertDialog dialog = dialogBuilder.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

			@Override
			public void onShow(DialogInterface dialog) {
				TextView subjectTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_outbox_message_details_subject_text);
				subjectTextView.setText(message.getSubject());

				StringBuilder recipents = new StringBuilder();
				int counter = 1;
				for (Iterator<MessageRecipent> iterator = message.getRecipents().iterator(); iterator.hasNext();) {
					MessageRecipent recipent = iterator.next();
					recipents.append(counter).append(". ");
					if (recipent.getRecipent().getAddress() != null && recipent.getRecipent().getAddress().getFirstName() != null
					        && recipent.getRecipent().getAddress().getLastName() != null) {
						recipents.append(recipent.getRecipent().getAddress().getFirstName()).append(" ")
						        .append(recipent.getRecipent().getAddress().getLastName());
					} else {
						recipents.append(recipent.getRecipent().getUsername());
					}
					if (iterator.hasNext()) {
						recipents.append("\n");
						counter++;
					}
				}

				TextView recipentsTextView = (TextView) ((AlertDialog) dialog)
				        .findViewById(R.id.dialog_outbox_message_details_recipents_text);
				recipentsTextView.setText(recipents.toString());

				TextView sentDataTextView = (TextView) ((AlertDialog) dialog)
				        .findViewById(R.id.dialog_outbox_message_details_sent_date_text);
				sentDataTextView.setText(AppUtils.getFormattedDateAsString(message.getDate(), Locale.getDefault()));

				TextView contentTextView = (TextView) ((AlertDialog) dialog).findViewById(R.id.dialog_outbox_message_details_content_text);
				contentTextView.setText(message.getContent());
			}
		});

		return dialog;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
