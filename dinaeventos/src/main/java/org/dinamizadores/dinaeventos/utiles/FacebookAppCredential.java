package org.dinamizadores.dinaeventos.utiles;

import java.io.Serializable;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.view.EventoBean;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Singleton
@Startup
public class FacebookAppCredential implements Serializable {

	private static final long serialVersionUID = -5773089070375278851L;
	private final Logger log = LogManager.getLogger(EventoBean.class);
	final String clientId = "1065091143540194";
    final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
    final String secretState = "secret" + new Random().nextInt(999_999);
    final OAuth20Service service = new ServiceBuilder()
            .apiKey(clientId)
            .apiSecret(clientSecret)
            .state(secretState)
            .callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml")
            .build(FacebookApi.instance());
	private String appAccessToken;
	
	@PostConstruct
    public void init() {
    	log.debug("Se procede a generar un token de aplicación");
        String appAccessTokenUrl = "https://graph.facebook.com/oauth/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=client_credentials";
        final OAuthRequest request = new OAuthRequest(Verb.GET, appAccessTokenUrl, service);
        
        Response response = null;
        
        try {
        	response = request.send();
        	
        	if (response.getCode() == 200) {
        		appAccessToken = response.getBody().substring(response.getBody().indexOf("|") + 1);
        		log.debug("Application access token: " + appAccessToken);
        	}
        } catch (Exception e) {
        	log.error("Ha ocurrido un error con el cierre de sesión: " + e.getMessage());
        }
	}

	public String getAppAccessToken() {
		return appAccessToken;
	}

	public void setAppAccessToken(String appAccessToken) {
		this.appAccessToken = appAccessToken;
	}
	
}
