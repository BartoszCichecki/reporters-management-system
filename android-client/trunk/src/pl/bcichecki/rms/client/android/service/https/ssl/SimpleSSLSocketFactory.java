/**
 * Project:   rms-client-android
 * File:      SimpleSSLSocketFactory.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      23-12-2012
 */

package pl.bcichecki.rms.client.android.service.https.ssl;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SimpleSSLSocketFactory extends SSLSocketFactory {

	protected SSLContext sslContext;

	protected X509TrustManager x509TrustManager;

	public SimpleSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
	        UnrecoverableKeyException {
		super(truststore);
		sslContext = SSLContext.getInstance("SSL");
		x509TrustManager = new SimpleX509TrustManager();
		sslContext.init(null, new TrustManager[] { x509TrustManager }, new SecureRandom());
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}

	@Override
	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
	}

}
