package org.bumph;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.util.IOTools;
import org.apache.catalina.valves.ValveBase;

/**
 * This valve allows the remote user to be set by the user based on a form 
 * entry.
 * 
 * @author buckett
 */
public class RemoteUserValve extends ValveBase {

	private Pattern matcher;

	public RemoteUserValve() {
	}

	public void setRequestURI(String pattern) {
		this.matcher = Pattern.compile(pattern);
	}

	@Override
	public void invoke(final Request request, final Response response)
			throws IOException, ServletException {
		if (matcher.matcher(request.getRequestURI()).matches()) {
			String username = request.getParameter("username");
			if (username == null || username.length() == 0) {
				sendPage(response);
			} else {
				final String credentials = "credentials";
				final List<String> roles = Collections.emptyList();
				// Tomcat 6 version:
				final Principal principal = new GenericPrincipal(null,
						username, credentials, roles);

				request.setUserPrincipal(principal);
				getNext().invoke(request, response);
			}
		} else {
			getNext().invoke(request, response);
		}

	}

	private void sendPage(Response response) throws IOException {
		InputStream resourceAsStream = getClass().getResourceAsStream(
				"form.html");
		response.addHeader("content-type", "text/html");
		ServletOutputStream outputStream = response.getOutputStream();
		IOTools.flow(resourceAsStream, outputStream);
	}

}
