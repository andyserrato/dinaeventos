package org.dinamizadores.dinaeventos.utiles;

import java.io.IOException;

import javax.faces.context.FacesContext;
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

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		LoginBean loginBean = (LoginBean) req.getSession().getAttribute("userActual");

		if (loginBean == null) {
			LOG.debug("Nulo");
		}

		// For the first application request there is no loginBean in the
		// session so user needs to log in
		// For other requests loginBean is present but we need to check if user
		// has logged in successfully
		if (loginBean == null || !loginBean.isLoggedIn()) {

			// /dinaeventos/secured/superSeguro.xhtml
			req.getSession().setAttribute("salto_uri_filtro",
					req.getRequestURI().substring(req.getContextPath().length()));

			String contextPath = ((HttpServletRequest) request).getContextPath();
			// System.out.println(contextPath);

			((HttpServletResponse) response).sendRedirect(contextPath + "/login/login.xhtml");
			// loginBean.setOriginalURL(request.get);
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
