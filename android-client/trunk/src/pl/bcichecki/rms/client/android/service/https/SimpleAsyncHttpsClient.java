/**
 * Project:   rms-client-android
 * File:      SimpleAsyncHttpsClient.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.service.https;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.loopj.android.http.AsyncHttpClient;

import pl.bcichecki.rms.client.android.service.https.ssl.SimpleSSLSocketFactory;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SimpleAsyncHttpsClient extends AsyncHttpClient {

	public SimpleAsyncHttpsClient() {
		super();
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, null);
			SSLSocketFactory sslSocketFactory = new SimpleSSLSocketFactory(keyStore);
			setSSLSocketFactory(sslSocketFactory);
		} catch (KeyStoreException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		} catch (CertificateException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (KeyManagementException e) {
			throw new IllegalStateException(e);
		} catch (UnrecoverableKeyException e) {
			throw new IllegalStateException(e);
		}
	}

}
