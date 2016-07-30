package org.dinamizadores.dinaeventos.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.dto.facebookprofile.PerfilRedSocial;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.CookieHelper;
import org.dinamizadores.dinaeventos.utiles.FacebookAppCredential;
import org.dinamizadores.dinaeventos.utiles.FacebookLoginService;
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
@Startup
public class LoginBean implements Serializable {
	private final Logger log = LogManager.getLogger(EventoBean.class);
	private static final long serialVersionUID = -1161277308459762945L;
	private String email;
	private String password;
	private List<Evento> eventosList;
	private Evento evento;
	private Usuario usuario;

	@EJB
	private UsuarioDao usuarioDao;
	@EJB
	private FacebookAppCredential appAccessToken;
	private Boolean loggedIn = false;
	private FacebookLoginService facebookLoginService = new FacebookLoginService();

	// TODO Falta cambiar la variable cuando se loguee con FEISBUK
	private Boolean loggedInWithFacebook = false;

	private String originalURL;
	private PerfilRedSocial perfilFacebook = null;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Esto sirve para cuando se hace un login forzoso al intentar acceder a una
	 * pagina protegida sin haberse logueado
	 */
	private void gestinarRedireccion() {
		String salto;
		try {
			if (originalURL != null) {
				salto = originalURL;
				originalURL = null;
			} else {
				salto = Constantes.Rutas.PAGINA_INICIAL;
			}
			log.debug("Volvemos a " + salto + " después de hacer Login");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

			context.redirect(context.getRequestContextPath() + salto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void establecerCookieUsuario(String email, String password) {

		CookieHelper coockieHelper = new CookieHelper();
		System.out.println("Estableciendo coockie de usuario");
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		coockieHelper.setCookie(Constantes.nombreCookie, email + "&" + password, req, resp);

	}

	public void login() {
		log.debug("Login normal");
		Usuario usuario = usuarioDao.login(email, password);
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		if (usuario != null) {
			loggedIn = true;
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", usuario.getNombre());
			this.usuario = usuario;
			establecerCookieUsuario(email, password);
			gestinarRedireccion();

		} else {
			loggedIn = false;
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error al Iniciar la sesión",
					"Correo/Contraseña incorrecta");
		}
		email = "";
		password = "";
		context.addMessage(null, message);
		// return originalURL;
	}

	public void loginFacebook() {

		final String authorizationUrl = facebookLoginService.getService().getAuthorizationUrl();

		checkFacebookLogin = true;
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		try {
			externalContext.redirect(authorizationUrl);
		} catch (IOException ex) {
			log.error("Ha ocurrido un problema con el inicio de sesión: ");
			ex.printStackTrace();
		}

	}

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

		if (checkFacebookLogin) {

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, String> parameterMap = (Map<String, String>) externalContext.getRequestParameterMap();

			// Si se recibe code se obtiene token
			final String code = parameterMap.get("code");
			if (code != null && !"".equals(code)) {
				log.debug("code: " + code);
				try {
					accessToken = facebookLoginService.getService().getAccessToken(code);
					log.debug("token: " + accessToken.getAccessToken());
				} catch (IOException e) {
					log.debug("error:", e);
					checkFacebookLogin = false;
				}
			}

			// si se obtiene token se verifica
			final String token = parameterMap.get("token");
			if (token != null && !"".equals(token)) {
				final OAuthRequest request2 = new OAuthRequest(Verb.GET, "https://graph.facebook.com/debug_token",
						facebookLoginService.getService());
				facebookLoginService.getService().signRequest(accessToken, request2);
				final Response response2 = request2.send();
				log.debug("Got it! Lets see what we found...");
				log.debug(response2.getCode());
				try {
					log.debug(response2.getBody());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			checkFacebookLogin = false;

			obtenerPerfilFacebook();
			log.debug("Access Token: " + accessToken.getAccessToken());
			log.debug("Refresh Token: " + accessToken.getRefreshToken());
			log.debug("Scope: " + accessToken.getScope());
			log.debug("Access Token: " + accessToken.getExpiresIn());

			// accessToken =
			// facebookLoginService.getService().getAccessToken(code);

			// usuarioDao.crearUsuarioConPerfilFacebook(perfilFacebook,
			// accessToken.getAccessToken());

		}
	}

	public void obtenerPerfilFacebook() {

		try {
			final OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_URL, facebookLoginService.getService());
			facebookLoginService.getService().signRequest(accessToken, request);
			Response response = null;
			response = request.send();
			log.debug("Obtenemos perfil facebook");
			log.debug(response.getBody());
			Gson gson = new Gson();
			perfilFacebook = gson.fromJson(response.getBody(), PerfilRedSocial.class);
			log.debug("perfil" + perfilFacebook.toString());
		} catch (Exception e) {
			log.error("Obteniendo perfil de facebook", e);
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

	@Loggable
	public StreamedContent obtenerImagen(DdTipoComplemento complemento) {
		StreamedContent file = null;
		if (complemento != null && !"".equals(complemento.getNombreImagen()) && complemento.getImagen() != null) {
			log.debug("El complemento no es nulo");
			// TODO falta sacar la extensión del nombre de la imagen
			file = new DefaultStreamedContent(new ByteArrayInputStream(complemento.getImagen()));
		}

		return file;
	}

	@Loggable
	public StreamedContent getImagenFromByte(byte[] fichero, String nombre) {
		StreamedContent file = null;
		if (fichero != null && !"".equals(nombre) && nombre != null) {
			log.debug("La imagen no es nula" + nombre);
			// TODO falta sacar la extensión del nombre de la imagen
			file = new DefaultStreamedContent(new ByteArrayInputStream(fichero));
		}

		return file;
	}

	public boolean isLoggedInWithFacebook() {
		// TODO Auto-generated method stub
		return loggedInWithFacebook;
	}
}