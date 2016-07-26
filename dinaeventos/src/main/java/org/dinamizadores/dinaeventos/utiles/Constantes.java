package org.dinamizadores.dinaeventos.utiles;

/**
 * Clase de constantes de uso global.
 * 
 * @author Raúl "El niño maravilla" del Río
 *
 */
public final class Constantes {

	/**
	 * Clase empotrada que contiene las rutas estáticas a distintas páginas.
	 */
	public final class Rutas {

		private Rutas() {}

		public static final String PAGINA_INICIAL = "/faces/index.xhtml";

		public static final String PAGINA_404 = "/404.xhtml";

		/**
		 * Si se devuelve esta ruta te quedas en la misma página.
		 */
		public static final String NULA = "#";

		/**
		 * Clase empotrada que contiene las rutas estáticas a las páginas del panel de administración.
		 */
		public final class Administracion {

			public static final String VISION_GLOBAL = "/administracion/visionGlobal.xhtml?faces-redirect=true";

			public static final String VISION_GLOBAL_CON_FACES = "/faces/administracion/visionGlobal.xhtml?faces-redirect=true";

			public static final String MIS_EVENTOS = "/administracion/misEventos.xhtml?faces-redirect=true";

			public static final String LUGARES_ORIGEN = "/administracion/lugaresdeorigen.xhtml?faces-redirect=true";

			public static final String VENTAS_PRECIOS = "/administracion/ventaporprecios.xhtml?faces-redirect=true";

			public static final String VENTAS_PAPEL = "/administracion/ventasenpapel.xhtml?faces-redirect=true";

			public static final String VENTAS_FISICAS = "/administracion/ventasfisicas.xhtml?faces-redirect=true";

			public static final String VENTAS_ONLINE = "/administracion/ventasonline.xhtml?faces-redirect=true";

			public static final String VENTAS_TIPO_ENTRADAS = "/administracion/ventasportiposdeentradas.xhtml?faces-redirect=true";

			public static final String GESTION_ENTRADAS = "/administracion/gestionentradas.xhtml?faces-redirect=true";

			public static final String DESCARGAR_DATOS_ENTRADAS = "/administracion/descargarDatosEntradas.xhtml?faces-redirect=true";
		}

		public final class Evento {

			private Evento() {}

			public static final String CREAR_EVENTO = "/secured/evento/crearEvento.xhtml?faces-redirect=true";

		}

		public final class Entrada {

			private Entrada() {}

			public static final String COMPRAR = "/comprar/comprarEntrada.xhtml?faces-redirect=true";

			public static final String COMPRAR_VALENCIA_CONNECT = "/valenciaConnect/comprar/comprarEntrada.xhtml?faces-redirect=true";

			public static final String VALIDAR = "/validacion/validarEntrada.xhtml?faces-redirect=true";

			public static final String CAMBIAR_NOMBRE = "/cambioNombre/cambioNombre.xhtml?faces-redirect=true";

			public static final String FORMULARIO_CLIENTE = "/comprar/formularioCliente.xhtml?faces-redirect=true";

			public static final String FORMULARIO_CLIENTE_VALENCIA_CONNECT = "/valenciaConnect/comprar/formCrearClientesAndComplementos.xhtml?faces-redirect=true";

			public static final String PAGAR = "/comprar/pagarEntradas.xhtml?faces-redirect=true";

			public static final String PAGAR_VALENCIA_CONNECT = "/valenciaConnect/comprar/pagarEntradas.xhtml?faces-redirect=true";
		}
	}

	public final class MangoPay {
		public static final String PAGO_OK = "SUCCEEDED";

		public static final String PAGO_FALLIDO = "FAILED";
	}

	public final class Facebook {

		private Facebook() {}

		public final class Credenciales {
			private Credenciales() {}

			public static final String CLIENT_ID = "1065091143540194";

			public static final String CLIENT_SECRET = "51cd238b5a3a10d2b9e0ec51c862c091";
		}

		public final class Enlaces {
			private Enlaces() {}

			public static final String API_PLAIN_URL = "https://graph.facebook.com/v2.6/";

			public static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v2.6/me";

			public static final String PROFILE_URL = "https://graph.facebook.com/v2.6/me?fields=id,first_name,last_name,email,gender,link,picture";

			public static final String CALLBACK_TO_LOGIN_PRUEBA = "http://localhost:8080/dinaeventos/faces/login/loginPrueba.xhtml";

			public static final String CALLBACK_TO_LOGIN = "http://localhost:8080/dinaeventos/faces/login/loginPrueba.xhtml";

			public static final String CALLBACK_TO_RRPP_APROVAL = "";
		}

		public final class Permisos {
			private Permisos() {}

			public static final String EMAIL = "email";
		}
	}
}
