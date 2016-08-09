package org.dinamizadores.dinaeventos.utiles;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.DdFormapago;
import org.dinamizadores.dinaeventos.model.DdSexo;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.DdTipoEvento;
import org.dinamizadores.dinaeventos.model.DdTiposIva;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.model.Permisos;
import org.dinamizadores.dinaeventos.model.Redessociales;
import org.dinamizadores.dinaeventos.model.Roles;
import org.dinamizadores.dinaeventos.model.Usuario;
import com.github.javafaker.Faker;

/**
 * Clase que nos permite inicializar la BBDD con los datos necesarios para el evento ValenciaConnect.
 * 
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Stateless
public class BBDDFakerProduccion {
	
	/** Clase Faker para generar cosas random. */
	private Faker faker;

	/** Logger de la aplicación. */
	private final Logger log = LogManager.getLogger(BBDDFakerProduccion.class);
	
	/** Acceso a la capa DAO para persistir los datos. */
	@EJB
	private DAOGenerico dao;

	@PostConstruct
	public void init() {
		faker = new Faker(new Locale("es"));
	}
	
	public void crearValenciaConnect() throws Exception {
		System.out.println("Empezamos a crear el modelo de datos de Valencia Connect");
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
			System.out.println("Error al insertar los sexos.");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de las Formas de Pago de ValenciaConnect.
		 */
		DdFormapago formaPagoDigital = new DdFormapago();
		DdFormapago formaPagoContado = new DdFormapago();
		
		formaPagoDigital.setNombre("Digital");
		formaPagoContado.setNombre("Al Contado");
		
		try{
			dao.insertar(formaPagoDigital);
			dao.insertar(formaPagoContado);
		} catch(Exception e) {
			System.out.println("Error al insertar las formas de pago.");
			e.printStackTrace();
		}

		/**
		 * Zona de creado de los permisos de los roles de ValenciaConnect.
		 */
		Permisos permisoLectura = new Permisos();
		Permisos permisoEscritura = new Permisos();
		
		permisoLectura.setNombre("Lectura");
		permisoEscritura.setNombre("Escritura");
		
		try{
			dao.insertar(permisoLectura);
			dao.insertar(permisoEscritura);
		} catch(Exception e) {
			System.out.println("Error al insertar los permisos.");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los roles de ValenciaConnect.
		 */
		Roles rolAdministrador = new Roles();
		rolAdministrador.setNombre("Administrador");
		rolAdministrador.getPermisos().add(permisoLectura);
		rolAdministrador.getPermisos().add(permisoEscritura);
		
		try{
			dao.insertar(rolAdministrador);
		} catch(Exception e) {
			System.out.println("Error al insertar los roles.");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado (por defecto) de las RRSS de ValenciaConnect.
		 */
		Redessociales redSocial = new Redessociales();
		redSocial.setIdUser(String.valueOf(faker.number().randomNumber(17, true)));
		redSocial.setEnlacePerfil("https:\\/\\/www.facebook.com\\/app_scoped_user_id\\/" + String.valueOf(faker.number().randomNumber(17, true)) + "\\/");
		redSocial.setToken(faker.finance().bic());
		redSocial.setEnlaceFoto("https:\\/\\/scontent.xx.fbcdn.net\\/v\\/t1.0-1\\/p50x50\\/10646650_10153778039437512_8141757141401914305_n.jpg?oh=51c634eaf86133df10fd29aac6d0cf36&oe=58094088");
		redSocial.setRedSocial("Facebook");
		
		try{
			dao.insertar(redSocial);
		} catch(Exception e) {
			System.out.println("Error al insertar la red social.");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los Usuarios de ValenciaConnect.
		 * 
		 * TODO: Aquí faltan por completar con datos reales de Samuel, los siguientes:
		 *  · Su fecha de nacimiento. (me da pereza entrar a Facebook, recuerdo que era en septiembre xd)
		 *  · Su DNI, se que era 532xxxxxQ o algo así...
		 */
		Usuario usuarioSamuel = new Usuario();
		usuarioSamuel.setNombre("SAMUEL");
		usuarioSamuel.setApellidos("LOZANO ZAPATA");
		usuarioSamuel.setEmail("slozano@valenciaconnect.es");
		usuarioSamuel.setTelefono("691631679");
		usuarioSamuel.setFechanac(new Date());
		usuarioSamuel.setDireccion("c/Falsa, 123");
		usuarioSamuel.setFotoperfil(null);
		usuarioSamuel.setFotoNombre("");
		usuarioSamuel.setPassword("dinamizadores123.");
		usuarioSamuel.setCuentacorriente("XXX");
		usuarioSamuel.setIban("ES-XXX");
		usuarioSamuel.setBloqueado(false);
		usuarioSamuel.setActivo(true);
		usuarioSamuel.setCliente(false);
		usuarioSamuel.setUltimologin(null);
		usuarioSamuel.setIdsexo(1);
		usuarioSamuel.setIdredessociales(redSocial.getIdredessociales());
		usuarioSamuel.setRoles(rolAdministrador);
		
		//Metemos hardcodeado el codigo postal, np está puesto en orden para cuando se llenen en la BBDD.
		GlobalCodigospostales codigoPostalSamuel = new GlobalCodigospostales(51075, 46, "46001", "VALENCIA");
		
		usuarioSamuel.setCodigoPostal(codigoPostalSamuel);
		usuarioSamuel.setDni("532XXXXXQ");
		
		try {
			dao.insertar(usuarioSamuel);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error al insertar los usuarios");
		}
		
		/**
		 * Zona de creado de los tipos de Eventos para Valencia Connect.
		 */
		DdTipoEvento tipoEventoCongreso = new DdTipoEvento();
		tipoEventoCongreso.setTipoEvento("Congreso");
		
		try {
			dao.insertar(tipoEventoCongreso);
		} catch (Exception e) {
			System.out.println("Error al insertar el tipo de Evento");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los organizadores para Valencia Connect.
		 * TODO: Completar con los datos reales.
		 */
		Organizadores organizadorValenciaJoven = new Organizadores();
		organizadorValenciaJoven.setCuentacorriente("XXX");
		organizadorValenciaJoven.setIban("ES-XXX");
		organizadorValenciaJoven.setUsuario(usuarioSamuel);
		
		try {
			dao.insertar(organizadorValenciaJoven);
		} catch (Exception e) {
			System.out.println("Error al insertar los organizadores");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado del Evento para Valencia Connect.
		 * TODO: (Raúl) Hay que revisar el tipo de datos de longitud y latitud, deberían ser floats o así, se necesitan decimales para representarlos.
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
		eventoValenciaConnect.setFechaIni(fechaEvento.getTime());
		eventoValenciaConnect.setFechaFin(fechaEvento.getTime());
		eventoValenciaConnect.setLogo(null);
		eventoValenciaConnect.setLogoNombre("logo");
		eventoValenciaConnect.setDescripcion("I Congreso de jóvenes empresarios innovadores y empresas consolidadas.");
		eventoValenciaConnect.setAforo(1000);
		eventoValenciaConnect.setLatitud(39);
		eventoValenciaConnect.setLongitud(0);
		eventoValenciaConnect.setEntradasDisponibles(1000);
		eventoValenciaConnect.setCodigoPostal(codigoPostalEvento);
		eventoValenciaConnect.setDdTipoEvento(tipoEventoCongreso);
		eventoValenciaConnect.setOrganizador(organizadorValenciaJoven);
		eventoValenciaConnect.setFechaAlta(new Date());
		eventoValenciaConnect.setActivo(true);
		eventoValenciaConnect.setNombreOrganizador("Valencia Joven");
		eventoValenciaConnect.setDescripcionOrganizador("Organización con el objetivo de promover el joven emprendedurismo en Valencia");
		eventoValenciaConnect.setEmailContacto("slozano@valenciaconnect.es");
		
		try {
			dao.insertar(eventoValenciaConnect);
		} catch (Exception e) {
			System.out.println("Error al insertar los Eventos.");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los Patrocinadores para ValenciaConnect.
		 * TODO: Aquí falta por hacer copy/paste de la estructura para meter los que faltan, pero me faltan datos.
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
		patrocinadorIRTIC.setCodigoPostal(codigoPostalIRTIC);
		
		try{
			dao.insertar(patrocinadorIRTIC);
			
			//Aquí actualizamos el Evento con su patrocinador recién creado.
			eventoValenciaConnect.getPatrocinadores().add(patrocinadorIRTIC);
			dao.modificar(eventoValenciaConnect);
		} catch (Exception e) {
			System.out.println("Error al insertar los patrocinadores");
		}
		
		/**
		 * Zona de creado de los tipos de Entrada para Valencia Connect.
		 */
		DdTipoEntrada tipoEntradaGeneral = new DdTipoEntrada();
		tipoEntradaGeneral.setNombre("GENERAL");
		tipoEntradaGeneral.setPrecio(new BigDecimal(20));
		tipoEntradaGeneral.setCantidad(500);
		tipoEntradaGeneral.setEvento(eventoValenciaConnect);
		tipoEntradaGeneral.setSobreVenta(false);
		tipoEntradaGeneral.setMaxPorPedido(6);
		tipoEntradaGeneral.setCanalDeVentas("ONLINE");
		tipoEntradaGeneral.setFechaInicioVenta(new Date());
		tipoEntradaGeneral.setFechaFinVenta(new Date());
		tipoEntradaGeneral.setCosteCambioDeNombre(new BigDecimal(5));
		tipoEntradaGeneral.setActiva(true);
		tipoEntradaGeneral.setDescripcion("Entrada general con acceso a la zona de networking.");
		
		DdTipoEntrada tipoEntradaCompleta = new DdTipoEntrada();
		tipoEntradaCompleta.setNombre("COMPLETA");
		tipoEntradaCompleta.setPrecio(new BigDecimal(40));
		tipoEntradaCompleta.setCantidad(500);
		tipoEntradaCompleta.setEvento(eventoValenciaConnect);
		tipoEntradaCompleta.setSobreVenta(false);
		tipoEntradaCompleta.setMaxPorPedido(6);
		tipoEntradaCompleta.setCanalDeVentas("ONLINE");
		tipoEntradaCompleta.setFechaInicioVenta(new Date());
		tipoEntradaCompleta.setFechaFinVenta(new Date());
		tipoEntradaCompleta.setCosteCambioDeNombre(new BigDecimal(5));
		tipoEntradaCompleta.setActiva(true);
		tipoEntradaCompleta.setDescripcion("Entrada general con acceso a todos los eventos y actividades del congreso.");
		
		try {
			dao.insertar(tipoEntradaGeneral);
			dao.insertar(tipoEntradaCompleta);
			
			//Aquí actualizamos el Evento con su patrocinador recién creado.
			eventoValenciaConnect.getDdTipoEntradas().add(tipoEntradaGeneral);
			eventoValenciaConnect.getDdTipoEntradas().add(tipoEntradaCompleta);
			dao.modificar(eventoValenciaConnect);
		} catch (Exception e) {
			System.out.println("Error al insertar los tipos de entradas");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los tipos de IVA para Valencia Connect.
		 */
		DdTiposIva tipoIVAnormal = new DdTiposIva();
		tipoIVAnormal.setNombre("21% IVA");
		
		try {
			dao.insertar(tipoIVAnormal);
		} catch (Exception e) {
			System.out.println("Error al insertar los tipos de IVA");
			e.printStackTrace();
		}
		
		/**
		 * Zona de creado de los tipos de complemento para Valencia Connect.
		 * TODO: Llenar de los datos finales los tipos de complementos.
		 */
		DdTipoComplemento tipoComplementoTallerA = new DdTipoComplemento();
		tipoComplementoTallerA.setNombre("Taller A");
		tipoComplementoTallerA.setPrecio(new BigDecimal(10));
		tipoComplementoTallerA.setDescripcion("Permite asistir al Taller A impartido por X");
		tipoComplementoTallerA.setEvento(eventoValenciaConnect);
		tipoComplementoTallerA.setImagen(null);
		tipoComplementoTallerA.setNombreImagen("");
		
		DdTipoComplemento tipoComplementoTallerB = new DdTipoComplemento();
		tipoComplementoTallerB.setNombre("Taller B");
		tipoComplementoTallerB.setPrecio(new BigDecimal(15));
		tipoComplementoTallerB.setDescripcion("Permite asistir al Taller B impartido por Y");
		tipoComplementoTallerB.setEvento(eventoValenciaConnect);
		tipoComplementoTallerB.setImagen(null);
		tipoComplementoTallerB.setNombreImagen("");
		
		DdTipoComplemento tipoComplementoTallerC = new DdTipoComplemento();
		tipoComplementoTallerC.setNombre("Taller C");
		tipoComplementoTallerC.setPrecio(new BigDecimal(20));
		tipoComplementoTallerC.setDescripcion("Permite asistir al Taller C impartido por Z");
		tipoComplementoTallerC.setEvento(eventoValenciaConnect);
		tipoComplementoTallerC.setImagen(null);
		tipoComplementoTallerC.setNombreImagen("");
		
		try {
			dao.insertar(tipoComplementoTallerA);
			dao.insertar(tipoComplementoTallerB);
			dao.insertar(tipoComplementoTallerC);
			
			//Aquí actualizamos el Evento con sus tipos de complementos recién creados.
			eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerA);
			eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerB);
			eventoValenciaConnect.getDdTipoComplementos().add(tipoComplementoTallerC);
			dao.modificar(eventoValenciaConnect);
		} catch (Exception e) {
			System.out.println("Error al insertar los tipos de complementos");
			e.printStackTrace();
		}
		
		
		System.out.println("Acabamos de crear el modelo de datos de Valencia Connect");
		return;
	}
	
}
