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

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

import com.loopj.android.http.AsyncHttpClient;

import pl.bcichecki.rms.client.android.services.clients.restful.https.ssl.SimpleSSLSocketFactory;

/**
 * @author Bartosz Cichecki
 * 
 */
public class SimpleAsyncHttpsClient extends AsyncHttpClient {

	protected int connectionTimeout = 5 * 1000;

	protected String username;

	protected String password;

	protected String realm;

	protected String host;

	protected int port;

	private SimpleAsyncHttpsClient() {
		super();
		setUpSsl();
		enableSetPreemptiveAuth();
		setTimeout(connectionTimeout);
	}

	public SimpleAsyncHttpsClient(String host, int port) {
		this();
		this.host = host;
		this.port = port;
	}

	public SimpleAsyncHttpsClient(String username, String password, String realm, String host, int port) {
		this();
		this.username = username;
		this.password = password;
		this.realm = realm;
		this.host = host;
		this.port = port;
		setBasicAuth(username, password, new AuthScope(host, port, realm));
	}

	protected void enableSetPreemptiveAuth() {
		HttpRequestInterceptor preemptiveAuthInterceptor = new HttpRequestInterceptor() {

			@Override
			public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
				AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);
				CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
				HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

				if (authState.getAuthScheme() == null) {
					AuthScope authScope = new AuthScope(targetHost.getHostName(), targetHost.getPort());
					Credentials creds = credsProvider.getCredentials(authScope);
					if (creds != null) {
						authState.setAuthScheme(new BasicScheme());
						authState.setCredentials(creds);
					}
				}
			}
		};

		((DefaultHttpClient) getHttpClient()).addRequestInterceptor(preemptiveAuthInterceptor, 0);
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public int getPort() {
		return port;
	}

	public String getRealm() {
		return realm;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void setBasicAuth(String username, String password) {
		setUsername(username);
		setPassword(password);
		super.setBasicAuth(username, password);
	}

	@Override
	public void setBasicAuth(String username, String password, AuthScope scope) {
		this.username = username;
		this.password = password;
		host = scope.getHost();
		port = scope.getPort();
		realm = scope.getRealm();
		super.setBasicAuth(username, password, scope);
	}

	public void setBasicAuth(String username, String password, String host, int port, String realm) {
		setBasicAuth(username, password, new AuthScope(host, port, realm));
	}

	public void setConnectionTimeout(int cONNECTION_TIMEOUT) {
		connectionTimeout = cONNECTION_TIMEOUT;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	/**
     * 
     */
	protected void setUpSsl() {
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

	public void setUsername(String username) {
		this.username = username;
	}

}
