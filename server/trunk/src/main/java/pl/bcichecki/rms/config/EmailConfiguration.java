/**
 * Project:   rms-server
 * File:      EmailConfiguration.java
 * License: 
 *            This file is licensed under GNU General Public License version 3
 *            http://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Copyright: Bartosz Cichecki [ cichecki.bartosz@gmail.com ]
 * Date:      01-10-2012
 */

package pl.bcichecki.rms.config;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

/**
 * @author Bartosz Cichecki
 */
public class EmailConfiguration {

	private String from;

	private String fromAddress;

	private String password;

	private String host;

	private int port;

	private boolean authenticate;

	private boolean ssl;

	private boolean tls;

	public Email getConfiguredEmail() throws EmailException {
		Email email = new Email() {

			@Override
			public Email setMsg(String msg) throws EmailException {
				throw new EmailException("You must define email type.");
			}
		};

		email.setFrom(fromAddress, from);
		email.setHostName(host);
		email.setSmtpPort(port);
		if (ssl) {
			email.setSSL(ssl);
			email.setSslSmtpPort(String.valueOf(port));
		}
		email.setTLS(tls);

		if (authenticate) {
			email.setAuthentication(from, password);
		}

		return email;
	}

	public String getFrom() {
		return from;
	}

	public String getFromAddress() {
		return fromAddress;
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

	public boolean isAuthenticate() {
		return authenticate;
	}

	public boolean isSsl() {
		return ssl;
	}

	public boolean isTls() {
		return tls;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
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

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public void setTls(boolean tls) {
		this.tls = tls;
	}
}
