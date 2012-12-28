/**
 * Project:   rms-client-android
 * File:      SimpleX509TrustManager.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.services.clients.restful.https.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author Bartosz Cichecki
 * 
 */
class SimpleX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
		// Doesn't care...
	}

	@Override
	public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
		// Doesn't care...
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// Doesn't care...
		return null;
	}

}
