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

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * @author Bartosz Cichecki
 */
public class EmailConfiguration {

	private String from;

	private String fromAddress;

	private String password;

	private String host;

	private int port;

	private Boolean authenticate;

	private Boolean ssl;

	public Boolean getAuthenticate() {
		return authenticate;
	}

	public SimpleEmail getConfiguredEmail() throws EmailException {
		SimpleEmail email = new SimpleEmail();

		email.setFrom(fromAddress, from);
		email.setHostName(host);
		email.setSmtpPort(port);

		if (ssl) {
			email.setSSL(ssl);
			email.setSslSmtpPort(String.valueOf(port));
		}

		if (authenticate) {
			email.setAuthentication(fromAddress, password);
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

	public Boolean getSsl() {
		return ssl;
	}

	public void setAuthenticate(Boolean authenticate) {
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

	public void setSsl(Boolean ssl) {
		this.ssl = ssl;
	}

}
