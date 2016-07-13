package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.dto.facebookprofile.PerfilRedSocial;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.FacebookAppCredential;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.Gson;

@Named
@SessionScoped
@Loggable
public class LoginBean implements Serializable {
	private final Logger log = LogManager.getLogger(EventoBean.class);
	private static final long serialVersionUID = -1161277308459762945L;
	private String email;
	private String password;
	private List<Evento> eventosList;
	private Evento evento;
	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private FacebookAppCredential appAccessToken;
	private Boolean loggedIn = false;
	
	//TODO Falta cambiar la variable cuando se loguee con FEISBUK
	private Boolean loggedInWithFacebook=false;
	
	private String originalURL;
	PerfilRedSocial perfilFacebook = null;
	private static final String NETWORK_NAME = "Facebook";
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.6/me";
	private static final String PROFILE_URL = "https://graph.facebook.com/v2.6/me?fields=id,first_name,last_name,email,birthday,gender,link,picture,location";
	private OAuth2AccessToken accessToken = null;
	private boolean checkFacebookLogin = false;
	private String paginaOriginal = "";
	private Map<String, StreamedContent> imagenes = new HashMap<>();

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

	public boolean isCheckFacebookLogin() {
		return checkFacebookLogin;
	}

	public void setCheckFacebookLogin(boolean checkFacebookLogin) {
		this.checkFacebookLogin = checkFacebookLogin;
	}

	public List<Evento> getEventosList() {
		return eventosList;
	}

	public void setEventosList(List<Evento> eventosList) {
		this.eventosList = eventosList;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	/**
	 * Esto sirve para cuando se hace un login forzoso al intentar acceder a una
	 * pagina protegida sin haberse logueado
	 */
	private void gestinarRedireccion() {
		String salto;

		if (originalURL != null) {
			salto = originalURL;
			originalURL = null;
		} else {
			salto = Constantes.Rutas.PAGINA_INICIAL;
		}
		System.out.println("Volvemos a " + salto + " después de hacer Login");
		ExternalContext econtext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			econtext.redirect(econtext.getRequestContextPath() + salto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void login() {
		System.out.println("Login normal");
		Usuario usuario = usuarioDao.login(email, password);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		if (usuario != null && usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombre());
			gestinarRedireccion();
		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al Iniciar la sesión",
					"Correo/Contraseña incorrecta");
		}
		email = "";
		password = "";
		context.addMessage(null, message);
	}

	public void loginFacebook() {
		final String clientId = "1065091143540194";
		final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
		final String secretState = "secret" + new Random().nextInt(999_999);
		final OAuth20Service service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).scope("email")
				.state(secretState).callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml")
				.build(FacebookApi.instance());

		final String authorizationUrl = service.getAuthorizationUrl();

		checkFacebookLogin = true;
		log.debug("checkFacebooklogin: " + checkFacebookLogin);
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		try {
			externalContext.redirect(authorizationUrl);
		} catch (IOException ex) {
			log.error("Ha ocurrido un problema con el inicio de sesión: ");
			ex.printStackTrace();
		}

	}

	// public void LogoutPerfilFacebook1() throws UnsupportedEncodingException {
	// final String clientId = "1065091143540194";
	// final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
	// final String secretState = "secret" + new Random().nextInt(999_999);
	// final OAuth20Service service = new ServiceBuilder()
	// .apiKey(clientId)
	// .apiSecret(clientSecret)
	// .state(secretState)
	// .callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml")
	// .build(FacebookApi.instance());
	//// String logoutUrl =
	// "https://www.facebook.com/logout.php?next=http://localhost:8080/dinaeventos/&access_token="
	// + accessToken.getAccessToken();
	// String logoutUrl = "https://www.facebook.com/logout.php?next=" +
	// URLEncoder.encode("http://localhost:8080/dinaeventos/faces/login/login.xhtml",
	// "UTF-8") + "&access_token=" + accessToken.getAccessToken();
	//
	// ExternalContext externalContext = FacesContext.getCurrentInstance()
	// .getExternalContext();
	//
	// try {
	// externalContext.redirect(logoutUrl);
	// } catch (IOException ex) {
	// log.error("Ha ocurrido un problema con el inicio de sesión: ");
	// ex.printStackTrace();
	// }
	// // final OAuthRequest request = new OAuthRequest(Verb.GET, logoutUrl,
	// service);
	////
	////
	////
	//// Response response = null;
	//// try {
	//// response = request.send();
	//// } catch (Exception e) {
	//// log.error("Ha ocurrido un error con el cierre de sesión: " +
	// e.getMessage());
	//// }
	////
	//// log.debug("Respuesta" + response.getCode());
	//// try {
	//// log.debug(response.getBody());
	//// } catch (IOException e) {
	//// e.printStackTrace();
	//// }
	//
	// log.debug(accessToken.getAccessToken());
	// }

	public void LogoutPerfilFacebook() throws UnsupportedEncodingException {
		final String clientId = "1065091143540194";
		final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
		final String secretState = "secret" + new Random().nextInt(999_999);
		final OAuth20Service service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).state(secretState)
				.callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml").build(FacebookApi.instance());

		String listaPermisosUrl = "https://graph.facebook.com/v2.6/" + perfilFacebook.getId() + "/permissions";
		final OAuthRequest request = new OAuthRequest(Verb.GET, listaPermisosUrl, service);

		Response response = null;
		try {
			service.signRequest(accessToken, request);
			response = request.send();
			log.debug("Respuesta" + response.getCode());
			log.debug(response.getBody());
		} catch (Exception e) {
			log.error("Ha ocurrido un error con el cierre de sesión: " + e.getMessage());
		}

	}

	public void loginGoogle() {

	}

	public void checkFacebookLogin() {
		final String clientId = "1065091143540194";
		final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
		final String secretState = "secret" + new Random().nextInt(999_999);
		final OAuth20Service service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).state(secretState)
				.callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml").build(FacebookApi.instance());

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

		/*
		 * Proceso de verificación de login en facebook tanto para primera como
		 * para siguientes
		 */
		// Si se recibe code se obtiene token
		// Si se recibe token se tiene que hacer llamada a extremo de inspección
		// Si se recibe code y token se deben de hacer las dos anteriores

		final String code = parameterMap.get("code");
		if (code != null && !"".equals(code)) {
			log.debug("Hemos recibido un code: " + code);
			try {
				log.debug("Procedemos a intercambiar el code por un token");
				accessToken = service.getAccessToken(code);
				log.debug("token: " + accessToken.getAccessToken());
			} catch (IOException e) {
				System.err.println("error:" + e.getMessage());
			}
		}

		final String token = parameterMap.get("token");
		if (code != null && !"".equals(token)) {

		}
	}

	public void checkFirstFacebookLogin() {
		log.debug("Checking loggin");
		log.debug("checkFacebooklogin: " + checkFacebookLogin);
		if (checkFacebookLogin) {
			System.out.println("Entramos a verificar el login");
			final String clientId = "1065091143540194";
			final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
			final String secretState = "secret" + new Random().nextInt(999_999);
			final OAuth20Service service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret)
					.state(secretState).callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml")
					.build(FacebookApi.instance());

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

			// Si se recibe code se obtiene token
			final String code = parameterMap.get("code");
			if (code != null && !"".equals(code)) {
				System.out.println("code: " + code);
				try {
					accessToken = service.getAccessToken(code);
					System.out.println("token: " + accessToken.getAccessToken());
				} catch (IOException e) {
					System.err.println("error:" + e.getMessage());
					checkFacebookLogin = false;
				}
			}

			final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, service);

			try {
				service.signRequest(accessToken, request);
			} catch (Exception e) {
				System.err.println("error:" + e.getMessage());
				checkFacebookLogin = false;
			}

			Response response = null;
			try {
				response = request.send();
			} catch (Exception e) {
				System.err.println("error:" + e.getMessage());
				checkFacebookLogin = false;
			}
			System.out.println("Got it! Lets see what we found...");
			System.out.println();
			System.out.println(response.getCode());
			try {
				System.out.println(response.getBody());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// si se obtiene token se verifica
			final String token = parameterMap.get("token");
			if (token != null && !"".equals(token)) {
				final OAuthRequest request2 = new OAuthRequest(Verb.GET, "https://graph.facebook.com/debug_token",
						service);
				service.signRequest(accessToken, request2);
				final Response response2 = request2.send();
				System.out.println("Got it! Lets see what we found...");
				System.out.println();
				System.out.println(response2.getCode());
				try {
					System.out.println(response2.getBody());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			checkFacebookLogin = false;

			// Verifier v = new Verifier(parameterMap.get("oauth_verifier"));
			// Token accessToken = service.getAccessToken(requestToken, v);
			//
			// OAuthRequest request = new OAuthRequest(Verb.GET,
			// "https://api.linkedin.com/v1/people/~");
			//
			// service.signRequest(accessToken, request);
			// Response response = request.send();
			//
			// System.err.println(response.getBody());
			//
			// return "";
		}
	}

	public void obtenerPerfilFacebook() {
		final String clientId = "1065091143540194";
		final String clientSecret = "51cd238b5a3a10d2b9e0ec51c862c091";
		final String secretState = "secret" + new Random().nextInt(999_999);
		final OAuth20Service service = new ServiceBuilder().apiKey(clientId).apiSecret(clientSecret).state(secretState)
				.callback("http://localhost:8080/dinaeventos/faces/login/login.xhtml").build(FacebookApi.instance());

		final OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL, service);

		try {
			// service.refreshAccessToken(accessToken.getAccessToken());
			// final OAuthRequest requestAuthorization = new
			// OAuthRequest(Verb.GET, service.getAuthorizationUrl(), service);
			// service.signRequest(accessToken, requestAuthorization);
			// Response responseAuthorization = requestAuthorization.send();
			// System.out.println("Respuesta" +
			// responseAuthorization.getBody());

			service.signRequest(accessToken, request);
			Response response = null;
			response = request.send();
			System.out.println("Got it! Lets see what we found...");
			System.out.println(response.getBody());
			Gson gson = new Gson();
			perfilFacebook = gson.fromJson(response.getBody(), PerfilRedSocial.class);
			System.out.println("perfil" + perfilFacebook.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StreamedContent getPhoto(String nombre) throws IOException {
		return imagenes.get(nombre);
	}

	public void setPhoto(UploadedFile imagen) throws IOException {
		if (imagen != null && imagen.getFileName() != null && !"".equals(imagen.getFileName())) {
			String nombreImagen = null;
			imagenes.put(nombreImagen, new DefaultStreamedContent(imagen.getInputstream()));
		}
	}

	public StreamedContent obtenerImagenPrueba() {
		StreamedContent file = null;

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("/img/placeholder.png");
		file = new DefaultStreamedContent(is, "image/png");

		return file;
	}

	public boolean isLoggedInWithFacebook() {
		// TODO Auto-generated method stub
		return loggedInWithFacebook;
	}
}
