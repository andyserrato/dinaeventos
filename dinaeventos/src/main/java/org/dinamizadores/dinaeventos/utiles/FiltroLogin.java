package org.dinamizadores.dinaeventos.utiles;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.view.LoginBean;

public class FiltroLogin implements Filter {

	private static final Logger LOG = LogManager.getLogger(FiltroLogin.class);
	@Inject
	private LoginBean loginBean;

	@Inject
	private UsuarioDao usuarioDao;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		// TODO Cuidado al cerrar Sesion, hay que borrar la coockie!!!!!! fdo.
		// Zipote de la mancha.

		// Si loginBean es nulo, o no hay ningun usuario logueado los remitimos
		// al login
		if (loginBean == null || !(loginBean.isLoggedIn() || loginBean.isLoggedInWithFacebook())) {

			loginBean.setOriginalURL(req.getRequestURI().substring(req.getContextPath().length()));
			String contextPath = req.getContextPath();

			CookieHelper coockieHelper = new CookieHelper();

			Cookie cookieUsuario = coockieHelper.getCookie(Constantes.nombreCookie, req);

			System.err.println("Usuario no logueado.");
			// Si no está logueado, hay que ver si hay una coockie, y en el caso
			// de que la haya ,se cargarán los valores para iniciar sesión

			if (cookieUsuario != null && cookieUsuario.getValue().length() > 1) {

				System.err.println("Estamos dentro de lo de las coockies");
				String[] userPass = cookieUsuario.getValue().split("&");
				String email = userPass[0];
				String pass = userPass[1];

				System.out.println("User encontrado:" + email);
				System.out.println("PassEncontrada:" + pass);

				Usuario usuario = usuarioDao.login(email, pass);
				if (usuario != null) {
					loginBean.setUsuario(usuario);
					loginBean.setLoggedIn(true);
					coockieHelper.setCookie(Constantes.nombreCookie, userPass[0] + "&" + userPass[1], req, resp);
				} else {
					loginBean.setLoggedIn(false);
					resp.sendRedirect(contextPath + "/login/login.xhtml");
				}
				loginBean.setEmail("");
				loginBean.setPassword("");

			} else {
				// Este será el primer Login de todos, o el caso en el que no
				// hay cookies ni nada.
				System.out.println("No hay cookies ni nada, con lo que vamos al Login!");
				resp.sendRedirect(contextPath + "/login/login.xhtml");
			}
		} else {
			System.out.println("Filtro normal.Estamos logueados y no se aplica nada de seguridad.");
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
