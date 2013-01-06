/**
 * Project:   rms-client-android
 * File:      UsersTextPrettyPrinter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      06-01-2013
 */

package pl.bcichecki.rms.client.android.prettyPrinters.impl;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

import pl.bcichecki.rms.client.android.model.impl.AddressDataContact;
import pl.bcichecki.rms.client.android.model.impl.User;
import pl.bcichecki.rms.client.android.prettyPrinters.AbstractPrettyPrinter;

/**
 * @author Bartosz Cichecki
 * 
 */
public class UsersTextPrettyPrinter extends AbstractPrettyPrinter<User> {

	public UsersTextPrettyPrinter(Context context) {
		super(context);
	}

	@Override
	public String print(User user) {
		StringBuilder sb = new StringBuilder();

		sb.append(user.getUsername()).append("\n");
		sb.append(user.getEmail()).append("\n\n");

		if (user.getAddress() != null) {
			sb.append(StringUtils.defaultString(user.getAddress().getFirstName())).append(" ")
			        .append(StringUtils.defaultString(user.getAddress().getLastName())).append("\n").append("\n");
			sb.append(StringUtils.defaultString(user.getAddress().getStreet())).append(" ")
			        .append(StringUtils.defaultString(user.getAddress().getStreetNumber())).append("/")
			        .append(StringUtils.defaultString(user.getAddress().getHouseNumber())).append("\n");
			sb.append(StringUtils.defaultString(user.getAddress().getZipCode())).append(" ")
			        .append(StringUtils.defaultString(user.getAddress().getCity())).append("\n\n");

			if (user.getAddress().getContacts() != null) {
				for (AddressDataContact adc : user.getAddress().getContacts()) {
					sb.append(adc.getType()).append(": ").append(adc.getValue()).append("\n");
				}
			}

			sb.append("\n").append(user.getComment());
		}

		return sb.toString();
	}

}
