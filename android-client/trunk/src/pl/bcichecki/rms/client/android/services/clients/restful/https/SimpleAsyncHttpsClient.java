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

package pl.bcichecki.rms.client.android.services.clients.restful.https;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.auth.AuthScope;
import org.apache.http.conn.ssl.SSLSocketFactory;

import com.loopj.android.http.AsyncHttpClient;

import pl.bcichecki.rms.client.android.services.clients.restful.https.ssl.SimpleSSLSocketFactory;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SimpleAsyncHttpsClient extends AsyncHttpClient implements HttpBasicAuthenticatable {

	private static final int CONNECTION_TIMEOUT = 5 * 1000;

	private String username;

	private String password;

	private String realm;

	private String host;

	private int port;

	private boolean authenticated;

	SimpleAsyncHttpsClient() {
		super();
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null, null);
			SSLSocketFactory sslSocketFactory = new SimpleSSLSocketFactory(keyStore);
			setSSLSocketFactory(sslSocketFactory);
			setTimeout(CONNECTION_TIMEOUT);
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

	@Override
	public void authenticate() {
		setBasicAuth(getUsername(), getPassword(), new AuthScope(getHost(), getPort(), getRealm()));
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getRealm() {
		return realm;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	@Override
	public void setBasicAuth(String user, String pass) {
		super.setBasicAuth(user, pass);
		setAuthenticated(true);
	}

	@Override
	public void setBasicAuth(String user, String pass, AuthScope scope) {
		super.setBasicAuth(user, pass, scope);
		setAuthenticated(true);
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

}
