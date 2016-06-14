package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.Usuario;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
	private static final long serialVersionUID = -1161277308459762945L;
	private String email;
    private String password;
    @EJB
    private UsuarioDao usuarioDao;
    private Boolean loggedIn = false;
    private String originalURL;
    
    public void recordOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }
    
    public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
	}

	public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public void login() {
        Usuario usuario = usuarioDao.login(email, password);
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        
        if(usuario != null && usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombre());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al Iniciar la sesión", "Correo/Contraseña incorrecta");
        }
         
       context.addMessage(null, message);
    }
    
    public void loginFacebook() {
    	
    }
}
