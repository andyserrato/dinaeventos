package org.dinamizadores.dinaeventos.utiles;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.DdFormapago;
import org.dinamizadores.dinaeventos.model.DdSexo;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.DdTipoEvento;
import org.dinamizadores.dinaeventos.model.DdTiposIva;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.model.Permisos;
import org.dinamizadores.dinaeventos.model.Roles;
import org.dinamizadores.dinaeventos.model.Usuario;

/**
 * Clase que nos permite inicializar la BBDD con los datos necesarios para el evento ValenciaConnect.
 * 
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Stateless
@Named
public class BBDDFakerProduccion implements Serializable {

	private static final long serialVersionUID = -3351071288000532121L;

	/** Logger de la aplicación. */
	private final Logger log = LogManager.getLogger(BBDDFakerProduccion.class);

	/** Acceso a la capa DAO para persistir los datos. */
	@EJB
	private DAOGenerico dao;

	public void crearValenciaConnect() throws Exception {
		log.debug("Empezamos a crear el modelo de datos de Valencia Connect");
		/**
		 * TODO: ·Revisar todos los TODO a lo largo de la función.
		 */

		/**
		 * Zona de creado de los sexos.
		 */
		DdSexo sexoHombre = new DdSexo();
		sexoHombre.setNombre("HOMBRE");

		DdSexo sexoMujer = new DdSexo();
		sexoMujer.setNombre("MUJER");

		try {
			dao.insertar(sexoHombre);
			dao.insertar(sexoMujer);
		} catch (Exception e) {
			log.debug("Error al insertar los sexos.", e);
		}

		/**
		 * Zona de creado de las Formas de Pago de ValenciaConnect.
		 */
		DdFormapago formaPagoDigital = new DdFormapago();
		DdFormapago formaPagoContado = new DdFormapago();

		formaPagoDigital.setNombre("Digital");
		formaPagoContado.setNombre("Al Contado");

		try {
			dao.insertar(formaPagoDigital);
			dao.insertar(formaPagoContado);
		} catch (Exception e) {
			log.debug("Error al insertar las formas de pago.", e);
		}

		/**
		 * Zona de creado de los permisos de los roles de ValenciaConnect.
		 */
		Permisos permisoLectura = new Permisos();
		Permisos permisoEscritura = new Permisos();

		permisoLectura.setNombre("Lectura");
		permisoEscritura.setNombre("Escritura");

		try {
			dao.insertar(permisoLectura);
			dao.insertar(permisoEscritura);
		} catch (Exception e) {
			log.debug("Error al insertar los permisos.", e);
		}

		/**
		 * Zona de creado de los roles de ValenciaConnect.
		 */
		Roles rolAdministrador = new Roles();
		rolAdministrador.setNombre("Administrador");
		rolAdministrador.getPermisos().add(permisoLectura);
		rolAdministrador.getPermisos().add(permisoEscritura);

		try {
			dao.insertar(rolAdministrador);
		} catch (Exception e) {
			log.debug("Error al insertar los roles.", e);
		}

		/**
		 * Zona de creado (por defecto) de las RRSS de ValenciaConnect.
		 */

		// TODO Esto no es necesario
		// Redessociales redSocial = new Redessociales();
		// redSocial.setIdUser(String.valueOf(faker.number().randomNumber(17, true)));
		// redSocial.setEnlacePerfil("https:\\/\\/www.facebook.com\\/app_scoped_user_id\\/" + String.valueOf(faker.number().randomNumber(17, true)) +
		// "\\/");
		// redSocial.setToken(faker.finance().bic());
		// redSocial.setEnlaceFoto("https:\\/\\/scontent.xx.fbcdn.net\\/v\\/t1.0-1\\/p50x50\\/10646650_10153778039437512_8141757141401914305_n.jpg?oh=51c634eaf86133df10fd29aac6d0cf36&oe=58094088");
		// redSocial.setRedSocial("Facebook");
		//
		// try {
		// dao.insertar(redSocial);
		// } catch (Exception e) {
		// log.debug("Error al insertar la red social.", e);
		//
		// }

		/**
		 * Zona de creado de los Usuarios de ValenciaConnect.
		 * 
		 * TODO: Aquí faltan por completar con datos reales de Samuel, los siguientes: · Su fecha de nacimiento. (me da pereza entrar a Facebook,
		 * recuerdo que era en septiembre xd) · Su DNI, se que era 532xxxxxQ o algo así...
		 */
		Usuario usuarioSamuel = new Usuario();
		usuarioSamuel.setNombre("SAMUEL");
		usuarioSamuel.setApellidos("LOZANO ZAPATA");
		usuarioSamuel.setEmail("slozano@valenciaconnect.es");
		usuarioSamuel.setTelefono("691631679");
		// usuarioSamuel.setFechanac(new Date());
		// usuarioSamuel.setDireccion("c/Falsa, 123");
		// usuarioSamuel.setFotoperfil(null);
		// usuarioSamuel.setFotoNombre("");
		usuarioSamuel.setPassword("dinamizadores123.");
		// usuarioSamuel.setCuentacorriente("XXX");
		// usuarioSamuel.setIban("ES-XXX");
		usuarioSamuel.setBloqueado(false);
		usuarioSamuel.setActivo(true);
		usuarioSamuel.setCliente(false);
		// usuarioSamuel.setUltimologin(null);
		usuarioSamuel.setIdsexo(1);
		// usuarioSamuel.setIdredessociales(redSocial.getIdredessociales());
		usuarioSamuel.setRoles(rolAdministrador);
		usuarioSamuel.setIdrol(rolAdministrador.getIdrol());
		// Metemos hardcodeado el codigo postal, np está puesto en orden para cuando se llenen en la BBDD.
		GlobalCodigospostales codigoPostalSamuel = new GlobalCodigospostales(51075, 46, "46001", "VALENCIA");

		usuarioSamuel.setCodigoPostal(codigoPostalSamuel);
		usuarioSamuel.setIdcodigopostal(codigoPostalSamuel.getIdcodigopostal());
		// usuarioSamuel.setDni("532XXXXXQ");

		try {
			dao.insertar(usuarioSamuel);
		} catch (Exception e) {
			log.debug("Error al insertar los usuarios", e);
		}

		/**
		 * Zona de creado de los tipos de Eventos para Valencia Connect.
		 */
		DdTipoEvento tipoEventoCongreso = new DdTipoEvento();
		tipoEventoCongreso.setTipoEvento("Congreso");

		try {
			dao.insertar(tipoEventoCongreso);
		} catch (Exception e) {
			log.debug("Error al insertar el tipo de Evento", e);
		}

		/**
		 * Zona de creado de los organizadores para Valencia Connect. TODO: Completar con los datos reales.
		 */
		Organizadores organizadorValenciaJoven = new Organizadores();
		organizadorValenciaJoven.setCuentacorriente("XXX");
		organizadorValenciaJoven.setIban("ES-XXX");
		organizadorValenciaJoven.setUsuario(usuarioSamuel);
		organizadorValenciaJoven.setIdUsuario(usuarioSamuel.getIdUsuario());

		try {
			dao.insertar(organizadorValenciaJoven);
		} catch (Exception e) {
			log.debug("Error al insertar los organizadores", e);
		}

		/**
		 * Zona de creado del Evento para Valencia Connect. TODO: (Raúl) Hay que revisar el tipo de datos de longitud y latitud, deberían ser floats o
		 * así, se necesitan decimales para representarlos.
		 */
		Evento eventoValenciaConnect = new Evento();
		Calendar fechaEvento = Calendar.getInstance();
		fechaEvento.set(Calendar.YEAR, 2016);
		fechaEvento.set(Calendar.MONTH, 9);
		fechaEvento.set(Calendar.DAY_OF_MONTH, 1);
		GlobalCodigospostales codigoPostalEvento = new GlobalCodigospostales(51087, 46, "46013", "VALENCIA");

		eventoValenciaConnect.setNombre("ValenciaConnect");
		eventoValenciaConnect.setNombreLugar("Oceanogràfic");
		eventoValenciaConnect.setDireccion("Carrer Eduardo Primo Yúfera, 1B");
		fechaEvento.set(Calendar.HOUR, 10);
		fechaEvento.set(Calendar.AM_PM, Calendar.AM);
		eventoValenciaConnect.setFechaIni(fechaEvento.getTime());
		fechaEvento.set(Calendar.AM_PM, Calendar.PM);
		eventoValenciaConnect.setFechaFin(fechaEvento.getTime());
		eventoValenciaConnect.setLogo(null);
		eventoValenciaConnect.setLogoNombre("logo");
		eventoValenciaConnect.setDescripcion("I Congreso de jóvenes empresarios innovadores y empresas consolidadas.");
		eventoValenciaConnect.setAforo(2600);
		eventoValenciaConnect.setLatitud(39);
		eventoValenciaConnect.setLongitud(0);
		eventoValenciaConnect.setEntradasDisponibles(2600);
		eventoValenciaConnect.setCodigoPostal(codigoPostalEvento);
		eventoValenciaConnect.setIdcodigopostal(codigoPostalEvento.getIdcodigopostal());
		eventoValenciaConnect.setDdTipoEvento(tipoEventoCongreso);
		eventoValenciaConnect.setIdtipoevento(tipoEventoCongreso.getIdTipoEvento());
		eventoValenciaConnect.setOrganizador(organizadorValenciaJoven);
		eventoValenciaConnect.setIdorganizador(organizadorValenciaJoven.getIdorganizador());
		eventoValenciaConnect.setFechaAlta(new Date());
		eventoValenciaConnect.setActivo(Boolean.TRUE);
		eventoValenciaConnect.setNombreOrganizador("Samuel Lozano");
		eventoValenciaConnect.setDescripcionOrganizador("Coordinador Valencia Connect");
		eventoValenciaConnect.setEmailContacto("slozano@valenciaconnect.es");

		try {
			dao.insertar(eventoValenciaConnect);
		} catch (Exception e) {
			log.debug("Error al insertar los Eventos.", e);
		}

		/**
		 * Zona de creado de los Patrocinadores para ValenciaConnect. TODO: Aquí falta por hacer copy/paste de la estructura para meter los que
		 * faltan, pero me faltan datos.
		 */
		Patrocinadores patrocinadorIRTIC = new Patrocinadores();
		GlobalCodigospostales codigoPostalIRTIC = new GlobalCodigospostales(50933, 46, "46980", "PATERNA");

		patrocinadorIRTIC.setNombre("IRTIC-UV");
		patrocinadorIRTIC.setDireccion("C/ Catedrático José Beltrán, 2");
		patrocinadorIRTIC.setTelefono("963543572");
		patrocinadorIRTIC.setEmail("ejemplo@irtic.uv.es");
		patrocinadorIRTIC.setCuentacorriente("XXX");
		patrocinadorIRTIC.setIban("ES-XXX");
		patrocinadorIRTIC.setFotoperfil(null);
		patrocinadorIRTIC.setFotoNombre("");
		patrocinadorIRTIC.setLogoNombre("");
		patrocinadorIRTIC.setEvento(eventoValenciaConnect);
		patrocinadorIRTIC.setIdEvento(eventoValenciaConnect.getIdevento());
		patrocinadorIRTIC.setCodigoPostal(codigoPostalIRTIC);
		patrocinadorIRTIC.setIdcodigopostal(codigoPostalIRTIC.getIdcodigopostal());

		try {
			dao.insertar(patrocinadorIRTIC);

			// Aquí actualizamos el Evento con su patrocinador recién creado.
			eventoValenciaConnect.getPatrocinadores().add(patrocinadorIRTIC);
			dao.modificar(eventoValenciaConnect);
		} catch (Exception e) {
			log.debug("Error al insertar los patrocinadores");
		}

		/**
		 * Zona de creado de los tipos de Entrada para Valencia Connect.
		 */

		Calendar fechaFinVentaTipoEntrada = Calendar.getInstance();
		fechaFinVentaTipoEntrada.set(Calendar.YEAR, 2016);
		fechaFinVentaTipoEntrada.set(Calendar.MONTH, 9);
		fechaFinVentaTipoEntrada.set(Calendar.DAY_OF_MONTH, 2);

		DdTipoEntrada tipoEntradaGeneral = new DdTipoEntrada();
		tipoEntradaGeneral.setNombre("GENERAL");
		tipoEntradaGeneral.setPrecio(new BigDecimal(25));
		tipoEntradaGeneral.setCantidad(2000);
		tipoEntradaGeneral.setEvento(eventoValenciaConnect);
		tipoEntradaGeneral.setIdEvento(eventoValenciaConnect.getIdevento());
		tipoEntradaGeneral.setSobreVenta(true);
		tipoEntradaGeneral.setMaxPorPedido(10);
		tipoEntradaGeneral.setCanalDeVentas("ONLINE");
		tipoEntradaGeneral.setFechaInicioVenta(new Date());
		tipoEntradaGeneral.setFechaFinVenta(fechaFinVentaTipoEntrada.getTime());
		tipoEntradaGeneral.setCosteCambioDeNombre(new BigDecimal(0));
		tipoEntradaGeneral.setActiva(true);
		tipoEntradaGeneral.setDescripcion("Entrada general con acceso a la zona de networking.");

		DdTipoEntrada tipoEntradaCompleta = new DdTipoEntrada();
		tipoEntradaCompleta.setNombre("COMPLETA");
		tipoEntradaCompleta.setPrecio(new BigDecimal(60));
		tipoEntradaCompleta.setCantidad(530);
		tipoEntradaCompleta.setEvento(eventoValenciaConnect);
		tipoEntradaCompleta.setIdEvento(eventoValenciaConnect.getIdevento());
		tipoEntradaCompleta.setSobreVenta(false);
		tipoEntradaCompleta.setMaxPorPedido(10);
		tipoEntradaCompleta.setCanalDeVentas("ONLINE");
		tipoEntradaCompleta.setFechaInicioVenta(new Date());
		tipoEntradaCompleta.setFechaFinVenta(fechaFinVentaTipoEntrada.getTime());
		tipoEntradaCompleta.setCosteCambioDeNombre(new BigDecimal(0));
		tipoEntradaCompleta.setActiva(true);
		tipoEntradaCompleta.setDescripcion("Entrada general con acceso a todos los eventos y actividades del congreso.");

		DdTipoEntrada tipoEntradaStartup = new DdTipoEntrada();
		tipoEntradaStartup.setNombre("STARTUP");
		tipoEntradaStartup.setPrecio(new BigDecimal(250));
		tipoEntradaStartup.setCantidad(50);
		tipoEntradaStartup.setEvento(eventoValenciaConnect);
		tipoEntradaStartup.setIdEvento(eventoValenciaConnect.getIdevento());
		tipoEntradaStartup.setSobreVenta(false);
		tipoEntradaStartup.setMaxPorPedido(1);
		tipoEntradaStartup.setCanalDeVentas("ONLINE");
		tipoEntradaStartup.setFechaInicioVenta(new Date());
		tipoEntradaStartup.setFechaFinVenta(fechaFinVentaTipoEntrada.getTime());
		tipoEntradaStartup.setCosteCambioDeNombre(new BigDecimal(0));
		tipoEntradaStartup.setActiva(true);
		tipoEntradaStartup.setDescripcion("Dos Entradas Completas + Stand para dos personas de presentación de Startup.");

		DdTipoEntrada tipoEntradaPrueba = new DdTipoEntrada();
		tipoEntradaPrueba.setNombre("PRUEBA");
		tipoEntradaPrueba.setPrecio(new BigDecimal(1));
		tipoEntradaPrueba.setCantidad(100);
		tipoEntradaPrueba.setEvento(eventoValenciaConnect);
		tipoEntradaPrueba.setIdEvento(eventoValenciaConnect.getIdevento());
		tipoEntradaPrueba.setSobreVenta(false);
		tipoEntradaPrueba.setMaxPorPedido(10);
		tipoEntradaPrueba.setCanalDeVentas("ONLINE");
		tipoEntradaPrueba.setFechaInicioVenta(new Date());
		tipoEntradaPrueba.setFechaFinVenta(fechaFinVentaTipoEntrada.getTime());
		tipoEntradaPrueba.setCosteCambioDeNombre(new BigDecimal(0));
		tipoEntradaPrueba.setActiva(true);
		tipoEntradaPrueba.setDescripcion("Entrada de prueba");

		try {
			dao.insertar(tipoEntradaGeneral);
			dao.insertar(tipoEntradaCompleta);
			dao.insertar(tipoEntradaPrueba);
			// Aquí actualizamos el Evento con su patrocinador recién creado.
			eventoValenciaConnect.getDdTipoEntradas().add(tipoEntradaGeneral);
			eventoValenciaConnect.getDdTipoEntradas().add(tipoEntradaCompleta);
			eventoValenciaConnect.getDdTipoEntradas().add(tipoEntradaPrueba);
			dao.modificar(eventoValenciaConnect);
		} catch (Exception e) {
			log.debug("Error al insertar los tipos de entradas", e);
		}

		/**
		 * Zona de creado de los tipos de IVA para Valencia Connect.
		 */
		DdTiposIva tipoIVAnormal = new DdTiposIva();
		tipoIVAnormal.setNombre("21% IVA");

		try {
			dao.insertar(tipoIVAnormal);
		} catch (Exception e) {
			log.debug("Error al insertar los tipos de IVA", e);
		}

		/**
		 * Zona de creado de los tipos de complemento para Valencia Connect. TODO: Llenar de los datos finales los tipos de complementos.
		 * 
		 * TODO según samuel estos complementos aún no se encuentran definidos.
		 */
		// DdTipoComplemento tipoComplementoTallerA = new DdTipoComplemento();
		// tipoComplementoTallerA.setNombre("Taller A");
		// tipoComplementoTallerA.setPrecio(new BigDecimal(10));
		// tipoComplementoTallerA.setDescripcion("Permite asistir al Taller A impartido por X");
		// tipoComplementoTallerA.setEvento(eventoValenciaConnect);
		// tipoComplementoTallerA.setIdEvento(eventoValenciaConnect.getIdevento());
		// tipoComplementoTallerA.setImagen(null);
		// tipoComplementoTallerA.setNombreImagen("");
		//
		// DdTipoComplemento tipoComplementoTallerB = new DdTipoComplemento();
		// tipoComplementoTallerB.setNombre("Taller B");
		// tipoComplementoTallerB.setPrecio(new BigDecimal(15));
		// tipoComplementoTallerB.setDescripcion("Permite asistir al Taller B impartido por Y");
		// tipoComplementoTallerB.setEvento(eventoValenciaConnect);
		// tipoComplementoTallerB.setIdEvento(eventoValenciaConnect.getIdevento());
		// tipoComplementoTallerB.setImagen(null);
		// tipoComplementoTallerB.setNombreImagen("");
		//
		// DdTipoComplemento tipoComplementoTallerC = new DdTipoComplemento();
		// tipoComplementoTallerC.setNombre("Taller C");
		// tipoComplementoTallerC.setPrecio(new BigDecimal(20));
		// tipoComplementoTallerC.setDescripcion("Permite asistir al Taller C impartido por Z");
		// tipoComplementoTallerC.setEvento(eventoValenciaConnect);
		// tipoComplementoTallerC.setIdEvento(eventoValenciaConnect.getIdevento());
		// tipoComplementoTallerC.setImagen(null);
		// tipoComplementoTallerC.setNombreImagen("");
		//
		// try {
		// dao.insertar(tipoComplementoTallerA);
		// dao.insertar(tipoComplementoTallerB);
		// dao.insertar(tipoComplementoTallerC);
		//
		// // Aquí actualizamos el Evento con sus tipos de complementos recién creados.
		// eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerA);
		// eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerB);
		// eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerC);
		// dao.modificar(eventoValenciaConnect);
		// } catch (Exception e) {
		// log.debug("Error al insertar los tipos de complementos", e);
		// }

		log.debug("Acabamos de crear el modelo de datos de Valencia Connect");
		return;
	}

}
