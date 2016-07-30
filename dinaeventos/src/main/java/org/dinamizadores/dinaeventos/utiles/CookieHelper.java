package org.dinamizadores.dinaeventos.utiles;

import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dinamizadores.dinaeventos.utiles.log.Loggable;

import java.io.Serializable;

@Named
@SessionScoped
@Startup
public class CookieHelper implements Serializable {

	private static final long serialVersionUID = -8017242863959628411L;
	private final int dias = 15;

	public void setCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {

		Cookie cookie = null;
		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					break;
				}
			}
		}
		if (cookie != null) {
			cookie.setValue(value);
		} else {
			cookie = new Cookie(name, value);
			cookie.setPath(request.getContextPath());
		}

		cookie.setMaxAge(dias * 24 * 60 * 60);
		System.out.println("Guardando Coockie, valor:" + value);
		response.addCookie(cookie);
	}

	public Cookie getCookie(String name, HttpServletRequest request) {

		Cookie cookie = null;

		Cookie[] userCookies = request.getCookies();
		if (userCookies != null && userCookies.length > 0) {
			for (int i = 0; i < userCookies.length; i++) {
				System.out.println(
						"userCookies[" + i + "]+ : " + userCookies[i].getName() + " -- " + userCookies[i].getValue());
				if (userCookies[i].getName().equals(name)) {
					cookie = userCookies[i];
					return cookie;
				}
			}
		}
		return null;
	}
}
