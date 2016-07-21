package org.dinamizadores.dinaeventos.utiles;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

public class FacebookLoginService implements Serializable{
	
	private static final long serialVersionUID = -5259384295920183425L;
	private final Logger log = LogManager.getLogger(FacebookLoginService.class);
	private OAuth2AccessToken accessToken = null;
	final OAuth20Service service = new ServiceBuilder()
			.apiKey(Constantes.Facebook.Credenciales.CLIENT_ID)
			.apiSecret(Constantes.Facebook.Credenciales.CLIENT_SECRET)
			.scope(Constantes.Facebook.Permisos.EMAIL)
			.callback(Constantes.Facebook.Enlaces.CALLBACK_TO_LOGIN_PRUEBA)
			.build(FacebookApi.instance());
	
	public FacebookLoginService() {
		
	}
	
	public void loginFacebook() {
		final String authorizationUrl = service.getAuthorizationUrl();

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(authorizationUrl);
		} catch (IOException ex) {
			log.error("Ha ocurrido un problema con el inicio de sesión: ", ex);
		}
	}

	public OAuth20Service getService() {
		return service;
	}
	
}
