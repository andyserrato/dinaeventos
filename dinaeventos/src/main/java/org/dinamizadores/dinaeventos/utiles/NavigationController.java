package org.dinamizadores.dinaeventos.utiles;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.view.LoginBean;

@Named(value = "navigationBean")
@SessionScoped
public class NavigationController implements Serializable {
 
	private static final long serialVersionUID = -702447984621676850L;
	private String originalUrl;
	@Inject
	private LoginBean loginBean;
	
	@Loggable
	public String toCrearEvento() {
		String destinoUrl;
		
		if (loginBean.isLoggedIn()) {
			destinoUrl = "/evento/crearEvento.xhtml";
		} else {
			originalUrl = "/evento/crearEvento.xhtml";
			destinoUrl = redirectToLogin();
		}
		
		return destinoUrl;
    }
	
	/**
     * Redirect to login page.
     * @return Login page name.
     */
    public String redirectToLogin() {
        return "/login/login.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to login page.
     * @return Login page name.
     */
    public String toLogin() {
        return "/login.xhtml";
    }
     
    /**
     * Redirect to info page.
     * @return Info page name.
     */
    public String redirectToInfo() {
        return "/info.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to info page.
     * @return Info page name.
     */
    public String toInfo() {
        return "/info.xhtml";
    }
     
    /**
     * Redirect to welcome page.
     * @return Welcome page name.
     */
    public String redirectToWelcome() {
//        return "/secured/welcome.xhtml?faces-redirect=true";
        System.out.println("redirectToWelcome  ");
        return "/sc/dashboard.xhtml?faces-redirect=true";
    }
     
    /**
     * Go to welcome page.
     * @return Welcome page name.
     */
    public String toWelcome() {
        return "/sc/dashboard.xhtml";
    }

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
}
