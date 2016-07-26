package org.dinamizadores.dinaeventos.utiles;

import java.io.Serializable;

import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.view.LoginBean;

@Named(value = "navigationBean")
@SessionScoped
@Startup
@Loggable
public class NavigationController implements Serializable {
 
	private static final long serialVersionUID = -702447984621676850L;
	private String originalUrl;
	
	@Loggable
	public String toCrearEvento() {
		return Constantes.Rutas.Evento.CREAR_EVENTO;
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
	
	@Loggable
	public String redirectToCrearUsuario() {
		// TODO colocar flag de tipo de usuario a crear
		return "/usuario/crearUsuario.xhtml?faces-redirect=true";
	}
	
	@Loggable
	public String toCrearUsuario() {
		// TODO colocar flag de tipo de usuario a crear123
		return "/usuario/crearUsuario.xhtml";
	}
	
	public String irComprar(Evento evento){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);
		
		return Constantes.Rutas.Entrada.COMPRAR;
	}
	
	public String irActivar(Evento evento){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);

		return Constantes.Rutas.Entrada.VALIDAR;
	}
	
	public String irCambioNombre(Evento evento){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);

		return Constantes.Rutas.Entrada.CAMBIAR_NOMBRE;
	}
	
	public String irComprarValenciaConnect(Evento evento){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);
		
		return Constantes.Rutas.Entrada.COMPRAR_VALENCIA_CONNECT;
	}
}
