package org.dinamizadores.dinaeventos.utiles;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		// Si loginBean es nulo, o no hay ningun usuario logueado los remitimos
		// al login
		if (loginBean == null || !(loginBean.isLoggedIn() || loginBean.isLoggedInWithFacebook())) {

			// Recuperamos la url desde la que se llama, para que cuando se haga
			// Login, se vuelva a la pagina de acceso inicial
			loginBean.setOriginalURL(req.getRequestURI().substring(req.getContextPath().length()));
			String contextPath = ((HttpServletRequest) request).getContextPath();
			((HttpServletResponse) response).sendRedirect(contextPath + "/login/login.xhtml");
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
