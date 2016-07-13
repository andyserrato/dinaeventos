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

		public static final String PAGINA_INICIAL = "/faces/index.xhtml";

		/**
		 * Clase empotrada que contiene las rutas estáticas a las páginas del
		 * panel de administración.
		 */
		public final class Administracion {

			public static final String VISION_GLOBAL = "/administracion/visionGlobal.xhtml?faces-redirect=true";

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
			public static final String CREAR_EVENTO = "/secured/evento/crearEvento.xhtml?faces-redirect=true";
		}
	}
	
	public final class MangoPay{
		public static final String PAGO_OK = "SUCCEEDED";
		public static final String PAGO_FALLIDO = "FAILED";
	}
}
