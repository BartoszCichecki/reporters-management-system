/**
 * Project:   rms-client-android
 * File:      DeviceTextPrettyPrinterPrinter.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      04-01-2013
 */

package pl.bcichecki.rms.client.android.prettyPrinters.impl;

import android.content.Context;

import pl.bcichecki.rms.client.android.R;
import pl.bcichecki.rms.client.android.model.impl.Device;
import pl.bcichecki.rms.client.android.prettyPrinters.AbstractPrettyPrinter;

/**
 * @author Bartosz Cichecki
 * 
 */
public class DeviceTextPrettyPrinterPrinter extends AbstractPrettyPrinter<Device> {

	public DeviceTextPrettyPrinterPrinter(Context context) {
		super(context);
	}

	@Override
	public String print(Device device) {
		StringBuilder sb = new StringBuilder();
		sb.append(getContext().getString(R.string.pretty_printer_device_teaser));
		sb.append(getContext().getString(R.string.pretty_printer_device_name));
		sb.append(device.getName());
		sb.append("\n");
		sb.append(getContext().getString(R.string.pretty_printer_device_description));
		sb.append(device.getDescription());
		sb.append("\n");
		sb.append(getContext().getString(R.string.pretty_printer_device_footer));
		return sb.toString();
	}

}
