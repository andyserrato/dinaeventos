package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
 
	private static final long serialVersionUID = -1161277308459762945L;
	private String email;
    private String password;
    private UsuarioDao usuarioDao;
 
     
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
 
    public void login(ActionEvent event) {
        Usuario usuario = usuarioDao.login(email, password);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
         
        if(usuario != null && usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombre());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al Iniciar la sesión", "Correo/Contraseña incorrecta");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
    
    public void loginFacebook() {
    	
    }
}
