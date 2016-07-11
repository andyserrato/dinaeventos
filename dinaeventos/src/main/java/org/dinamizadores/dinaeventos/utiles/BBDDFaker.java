package org.dinamizadores.dinaeventos.utiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

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
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.model.Permisos;
import org.dinamizadores.dinaeventos.model.Redessociales;
import org.dinamizadores.dinaeventos.model.Roles;
import org.dinamizadores.dinaeventos.model.RrppJefes;
import org.dinamizadores.dinaeventos.model.RrppMinion;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.view.EventoBean;

import com.github.javafaker.Faker;

@Stateless
public class BBDDFaker {
	// TODO realizar las relaciones de rrppjefe con el evento
	// TODO realizar las relaciones de ddtipocomplemento con las entradas
	// TODO realizar relacion entre rrppjefe, rrppminion y evento
	/** Clase Faker para generar cosas random. */
	private Faker faker;
	private final Logger log = LogManager.getLogger(EventoBean.class);
	
	@PostConstruct
	public void init() {
		faker = new Faker(new Locale("es"));
	}
	
	/** Acceso a la capa DAO para persistir los datos. */
	@EJB
	private DAOGenerico dao;
	
	public DdFormapago crearFormaPago(){
		DdFormapago formaPago = new DdFormapago();
		
		formaPago.setNombre(faker.beer().name());
		
		System.out.println(formaPago.getNombre());
		
		return formaPago;
	}
	
	public DdSexo crearSexo(){
		DdSexo d = new DdSexo();
		
		d.setNombre(faker.color().name());
		
		return d;
	}
	
	public DdTipoComplemento crearTipoComplemento(int idevento){
		DdTipoComplemento d = new DdTipoComplemento();
		
		d.setDescripcion(faker.chuckNorris().fact());
		d.setNombre(faker.beer().name());
		d.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
		d.setIdEvento(idevento);
		// TODO no se puede agregar evento la clave marica foránea no me deja
		return d;
	}
	
	public DdTipoEntrada crearTipoEntrada(){
		DdTipoEntrada d = new DdTipoEntrada();
		
		d.setNombre(faker.book().title());
		d.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
		d.setCantidad(faker.number().numberBetween(100, 10000));
		d.setSobreVenta(Boolean.valueOf(faker.bool().bool()));
		d.setMaxPorPedido(Integer.valueOf(faker.number().numberBetween(1, 20)));
		d.setCanalDeVentas("online");
		d.setFechaInicioVenta(new Date());
		
		Calendar today = Calendar.getInstance(); 
		Calendar nextYearToday = today;
		Calendar tomorrow = today;
		tomorrow.add(Calendar.MONTH, 1);
		nextYearToday.add(Calendar.YEAR, 1);
		
//		d.setFechaFinVenta(faker.date().between(tomorrow.getTime(), nextYearToday.getTime()));
		d.setFechaFinVenta(nextYearToday.getTime());
		return d;
	}
	
	public DdTipoEvento crearTipoEvento(){
		DdTipoEvento d = new DdTipoEvento();
		
		d.setTipoEvento(faker.commerce().department());

		return d;
	}
	
	public DdTiposIva crearTiposIva(){
		DdTiposIva d = new DdTiposIva();
		
		d.setNombre(faker.lorem().fixedString(7));
		
		return d;
	}
	
	public Entrada crearEntrada(int idEvento, int idFormaPago, int idTipoEntrada, int idTipoIva, int idUsuario, DdTipoComplemento tipoComplemento){
		Entrada e = new Entrada();
		
		e.setActiva(true);
		e.setDentrofuera(false);
		e.setIdevento(idEvento);
		e.setIdformapago(idFormaPago);
		e.setIdtipoentrada(idTipoEntrada);
		e.setIdtipoiva(idTipoIva);
		e.setIdusuario(idUsuario);
		e.setNumeroserie(Long.toString(faker.number().randomNumber()));
		e.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
		e.setTicketgenerado(false);
		e.setValidada(false);
		e.setVendida(true);
		e.getDdTipoComplementos().add(tipoComplemento);
		e.setFechaVendida(new Date());
		e.setFechaValidada(new Date());
				
		return e;
	}
	
	public Evento crearEvento(int idCodigoPostal, int idOrganizador, int idTipoEvento){
		Evento e = new Evento();
		
		e.setActivo(true);
		e.setAforo(25000);
		e.setDescripcion(faker.commerce().department());
		e.setDireccion(faker.color().name());
		e.setEntradasDisponibles(e.getAforo());
		e.setFechaAlta(faker.date().past(63072000, TimeUnit.SECONDS));
		e.setFechaIni(new Date(e.getFechaAlta().getTime() + 1296000000));
		e.setFechaFin(e.getFechaIni());
		e.setIdcodigopostal(idCodigoPostal);
		e.setIdorganizador(idOrganizador);
		// TODO revisar por qué no se puede agregar un organizador
		e.setIdtipoevento(idTipoEvento);
		e.setLatitud(0);
		e.setLongitud(0);
		e.setNombre(faker.color().name());
		e.setNombrelugar(faker.address().state());
		
//		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//		InputStream is = classloader.getResourceAsStream("test.csv");
//		e.setLogo(new ByteArrayDataSource(is, "image/png"));
		return e;
	}
	
	public Organizadores crearOrganizadores(int idCodigoPostal){
		Organizadores o = new Organizadores();
		
		o.setNombre(faker.name().firstName());
		o.setDescripcion(faker.superhero().descriptor());
		
		Usuario usuario = new Usuario();
		
		usuario.setActivo(true);
		usuario.setApellidos(faker.name().lastName());
		usuario.setBloqueado(false);
//		u.setCuentacorriente(faker.finance().bic());
		usuario.setDireccion(faker.color().name());
		usuario.setDni(String.valueOf(faker.number().numberBetween(0, 99999999)));
		usuario.setEmail(faker.internet().emailAddress());
		System.out.println("email: " + usuario.getEmail());
		usuario.setFechanac(faker.date().past(10950, TimeUnit.DAYS));
		usuario.setFotoperfil(null);
		usuario.setIban(faker.finance().iban("ES"));
		usuario.setIdcodigopostal(idCodigoPostal);
//		u.setIdredessociales(idRedesSociales);
//		u.setIdrol(idRol);
		usuario.setIdsexo(faker.number().numberBetween(1,2));
		usuario.setNombre(faker.name().firstName());
		usuario.setPassword("123456");
		usuario.setTelefono("615931639");
		usuario.setUltimologin(new Date());
			
		o.setUsuario(usuario);
		
		return o;
	}
	
	public Patrocinadores crearPatrocinador(int idCodigoPostal, int idEvento){
		Patrocinadores p = new Patrocinadores();
		
//		p.setCuentacorriente(faker.finance().creditCard());
		p.setDireccion(faker.address().country());
		p.setEmail(faker.internet().emailAddress());
		p.setIban(faker.finance().iban("ES"));
		p.setIdcodigopostal(idCodigoPostal);
		p.setNombre(faker.name().firstName());
		p.setTelefono("663149759");
		p.setIdEvento(idEvento);
		// TODO problema foreign key
		return p;
	}
	
	public Permisos crearPermisos(){
		Permisos p = new Permisos();
		
		p.setNombre(faker.beer().name());
		// TODO asociar roles + roles_permisos + permisos
		return p;
	}
	
	public Redessociales crearRedSocial(){
		Redessociales redSocial = new Redessociales();
		
		redSocial.setIdUser(String.valueOf(faker.number().randomNumber(17, true)));
		redSocial.setEnlacePerfil("https:\\/\\/www.facebook.com\\/app_scoped_user_id\\/" + String.valueOf(faker.number().randomNumber(17, true)) + "\\/");
		redSocial.setToken(faker.finance().bic());
		redSocial.setEnlaceFoto("https:\\/\\/scontent.xx.fbcdn.net\\/v\\/t1.0-1\\/p50x50\\/10646650_10153778039437512_8141757141401914305_n.jpg?oh=51c634eaf86133df10fd29aac6d0cf36&oe=58094088");
		redSocial.setRedSocial("Facebook");
		return redSocial;
	}
	
	public Roles crearRol(List<Permisos> permisos){
		Roles rol = new Roles();
		
		rol.setNombre(faker.color().name());
		rol.getPermisos().add(permisos.get(ThreadLocalRandom.current().nextInt(permisos.size())));
		return rol;
	}
	
	public RrppJefes crearRRPPJefes(int idOrganizador, int idUsuario){
		RrppJefes r = new RrppJefes();
		
		r.setCodigoPromocional(faker.hacker().verb());
//		r.setIdOrganizador(idOrganizador);
		r.setLimiteEntradas(0);
		r.setIdUsuario(idUsuario);
		
		return r;
	}
	
	public RrppMinion crearRRPPMinion(int idJefe, int idUsuario){
		RrppMinion r = new RrppMinion();
		
		r.setCoigoPromocional(faker.hacker().verb());
		r.setIdjefe(idJefe);
		r.setLimiteEntradas(0);
		r.setIdUsuario(idUsuario);
		
		return r;
	}
	
	public Usuario crearUsuario(int idCodigoPostal, int idRedSocial, int idRol, int idSexo){
		Usuario u = new Usuario();
		
		u.setActivo(true);
		u.setApellidos(faker.name().lastName());
		u.setBloqueado(false);
//		u.setCuentacorriente(faker.finance().bic());
		u.setDireccion(faker.color().name());
		u.setDni(String.valueOf(faker.number().numberBetween(0, 99999999)));
		u.setEmail(faker.internet().emailAddress());
		u.setFechanac(faker.date().past(10950, TimeUnit.DAYS));
		u.setFotoperfil(null);
		u.setIban(faker.finance().iban("ES"));
		u.setIdcodigopostal(idCodigoPostal);
		u.setIdredessociales(idRedSocial);
		u.setIdrol(idRol);
		u.setIdsexo(idSexo);
		u.setNombre(faker.name().firstName());
		u.setPassword("123456");
		u.setTelefono("615931639");
		u.setUltimologin(new Date());
		
		return u;
	}
	
	public void llenarBBDD() throws Exception{
		/**
		 * Para que está función funcione como se espera, se deberá tener cargadas ya la tablas de Provincias y la de codigos postales.
		 * Además, no debería tener ningún dato ninguna tabla para evitar posibles duplicados de claves.
		 */
		final int NUM_FORMAPAGO = 2;
		final int NUM_SEXO = 2;
		final int NUM_EVENTO = 2;
		final int NUM_TIPOEVENTO = 10;
		final int NUM_ORGANIZADORES = 2;
		final int NUM_TIPOCOMPLEMENTO = 5;
		final int NUM_PATROCINADORES = 2;
		final int NUM_TIPOENTRADA = 3;
		final int NUM_TIPOSIVA = 3;
		final int NUM_PERMISOS = 3;
		final int NUM_ROLES = 3;
		final int NUM_USUARIO = 30;
		final int NUM_ENTRADA = 50;
		final int NUM_REDESSOCIALES = 3;
		final int NUM_RRPPJEFE = 1;
		final int NUM_RRPPMINION = 3;
		
		final int NUM_CODIGOSPOSTALES = 53169; 
		
		log.debug("INICIO DEL LLENADO DE LA   BBDD");
		faker = new Faker();
		
		List<DdFormapago> formasDePago = new ArrayList<>();
		for(int i = 0; i < NUM_FORMAPAGO; i++){
			DdFormapago formaDePago = crearFormaPago();
			dao.insertar(formaDePago);
			formasDePago.add(formaDePago);
		}

		List<DdSexo> sexos = new ArrayList<>();
		for(int i = 0; i < NUM_SEXO; i++){
			DdSexo sexo = crearSexo();
			dao.insertar(sexo);
			sexos.add(sexo);
		}
		
		List<DdTipoEvento> tiposEventos = new ArrayList<>();
		for(int i = 0; i < NUM_TIPOEVENTO; i++){
			DdTipoEvento tipoEvento = crearTipoEvento(); 
			dao.insertar(tipoEvento);
			tiposEventos.add(tipoEvento);
		}
		
		List<Organizadores> organizadores =  new ArrayList<>();
		for(int i = 0; i < NUM_ORGANIZADORES; i++){
			Organizadores organizador = crearOrganizadores(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES));
			dao.insertar(organizador);
			organizadores.add(organizador);
		}
		
		List<Evento> eventos = new ArrayList<>();
		for(int i = 0; i < NUM_EVENTO; i++){
			int idOrganizador = organizadores.get(ThreadLocalRandom.current().nextInt(organizadores.size())).getIdorganizador();
			int idTipoEvento = tiposEventos.get(ThreadLocalRandom.current().nextInt(tiposEventos.size())).getIdTipoEvento();
			Evento evento = crearEvento(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), idOrganizador, idTipoEvento);
			dao.insertar(evento);
			eventos.add(evento);
		}
		
		List<DdTipoComplemento> complementos = new ArrayList<>();
		for(int i = 0; i < NUM_TIPOCOMPLEMENTO; i++){
			int idEvento = eventos.get(ThreadLocalRandom.current().nextInt(eventos.size())).getIdevento();
			DdTipoComplemento complemento = crearTipoComplemento(idEvento);
			dao.insertar(complemento);
			complementos.add(complemento);
		}
		
		List<Patrocinadores> patrocinadores = new ArrayList<>();
		for(int i = 0; i < NUM_PATROCINADORES; i++){
			// TODO esto se puede cambiar por una consulta para traerse todos los ids en una lista y hacer un random de esa lista
			int idEvento = eventos.get(ThreadLocalRandom.current().nextInt(eventos.size())).getIdevento();
			Patrocinadores patrocinador = crearPatrocinador(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), idEvento); 
			dao.insertar(patrocinador);
			patrocinadores.add(patrocinador);
		}
		
		List<DdTipoEntrada> tiposDeEntrada = new ArrayList<>();
		for(int i = 0; i < NUM_TIPOENTRADA; i++){
			DdTipoEntrada tipoEntrada = crearTipoEntrada(); 
			dao.insertar(tipoEntrada);
			tiposDeEntrada.add(tipoEntrada);
		}
		
		List<DdTiposIva> tiposDeIva = new ArrayList<>();
		for(int i = 0; i < NUM_TIPOSIVA; i++){
			DdTiposIva tipoDeIva = crearTiposIva(); 
			dao.insertar(tipoDeIva);
			tiposDeIva.add(tipoDeIva);
		}
		
		List<Permisos> permisos = new ArrayList<>();
		for(int i = 0; i < NUM_PERMISOS; i++){
			Permisos permiso = crearPermisos(); 
			dao.insertar(permiso);
			permisos.add(permiso);
		}
		
		List<Roles> roles = new ArrayList<>();
		for(int i = 0; i < NUM_ROLES; i++){
			Roles rol = crearRol(permisos);
			dao.insertar(rol);
			roles.add(rol);
		}
		
		List<Redessociales> redesSociales = new ArrayList<>();
		for(int i = 0; i < NUM_REDESSOCIALES; i++){
			Redessociales redSocial = crearRedSocial(); 
			dao.insertar(redSocial);
			redesSociales.add(redSocial);
		}
		
		List<Usuario> usuarios = new ArrayList<>();
		for(int i = 0; i < NUM_USUARIO; i++){
			int idRedSocial = redesSociales.get(ThreadLocalRandom.current().nextInt(redesSociales.size())).getIdredessociales();
			int idRol = roles.get(ThreadLocalRandom.current().nextInt(roles.size())).getIdrol();
			int idSexo = sexos.get(ThreadLocalRandom.current().nextInt(sexos.size())).getIdsexo();
			
			Usuario usuario = crearUsuario(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), idRedSocial, idRol, idSexo);
			dao.insertar(usuario);
			usuarios.add(usuario);
		}
		
		List<Entrada> entradas = new ArrayList<>();
		for(int i = 0; i < NUM_ENTRADA; i++){ 
			int idEvento = eventos.get(ThreadLocalRandom.current().nextInt(eventos.size())).getIdevento();
			int idFormaPago = formasDePago.get(ThreadLocalRandom.current().nextInt(formasDePago.size())).getIdformapago();
//			int idOrigenEntrada = origenDeEntradas.get(ThreadLocalRandom.current().nextInt(origenDeEntradas.size())).getIdorigenEntrada();
			int idTipoEntrada = tiposDeEntrada.get(ThreadLocalRandom.current().nextInt(tiposDeEntrada.size())).getIdtipoentrada();
			int idTiposIva = tiposDeIva.get(ThreadLocalRandom.current().nextInt(tiposDeIva.size())).getIdtipoiva();
			// TODO verificar que estos usuarios no están vinculados con otras entidades
			int idUsuario = usuarios.get(ThreadLocalRandom.current().nextInt(usuarios.size())).getIdUsuario();
			
			Entrada entrada = crearEntrada(idEvento, idFormaPago, idTipoEntrada, idTiposIva, idUsuario, complementos.get(ThreadLocalRandom.current().nextInt(complementos.size()))); 
			dao.insertar(entrada);
			entradas.add(entrada);
		}
		
		List<Usuario> usuariosRrppJefe = new ArrayList<>();
		for(int i = 0; i < NUM_USUARIO; i++){
			int idRedSocial = redesSociales.get(ThreadLocalRandom.current().nextInt(redesSociales.size())).getIdredessociales();
			int idRol = roles.get(ThreadLocalRandom.current().nextInt(roles.size())).getIdrol();
			int idSexo = sexos.get(ThreadLocalRandom.current().nextInt(sexos.size())).getIdsexo();
			
			Usuario usuario = crearUsuario(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), idRedSocial, idRol, idSexo);
			dao.insertar(usuario);
			usuariosRrppJefe.add(usuario);
		}
		
		List<RrppJefes> rrppJefes = new ArrayList<>();
		for(int i = 0; i < NUM_RRPPJEFE; i++){
			int idOrganizador = organizadores.get(ThreadLocalRandom.current().nextInt(organizadores.size())).getIdorganizador();
			int idUsuario = usuariosRrppJefe.get(ThreadLocalRandom.current().nextInt(usuariosRrppJefe.size())).getIdUsuario();
			RrppJefes rrppJefe = crearRRPPJefes(idOrganizador, idUsuario);  
			dao.insertar(rrppJefe);
			rrppJefes.add(rrppJefe);
		}
		
		List<Usuario> usuariosRrppMinion = new ArrayList<>();
		for(int i = 0; i < NUM_USUARIO; i++){
			int idRedSocial = redesSociales.get(ThreadLocalRandom.current().nextInt(redesSociales.size())).getIdredessociales();
			int idRol = roles.get(ThreadLocalRandom.current().nextInt(roles.size())).getIdrol();
			int idSexo = sexos.get(ThreadLocalRandom.current().nextInt(sexos.size())).getIdsexo();
			
			Usuario usuario = crearUsuario(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), idRedSocial, idRol, idSexo);
			dao.insertar(usuario);
			usuariosRrppMinion.add(usuario);
		}
		
		List<RrppMinion> rrppMinions = new ArrayList<>();
		for(int i = 0; i < NUM_RRPPMINION; i++){
			int idRrppJefe = rrppJefes.get(ThreadLocalRandom.current().nextInt(rrppJefes.size())).getIdrrppJefe();
			int idUsuario = usuariosRrppMinion.get(ThreadLocalRandom.current().nextInt(usuariosRrppMinion.size())).getIdUsuario();
			RrppMinion rrppMinion = crearRRPPMinion(idRrppJefe, idUsuario); 
			dao.insertar(rrppMinion);
			rrppMinions.add(rrppMinion);
		}

				
//		for(int i = 0; i < NUM_ENTRADA; i++){
//			dao.insertar(crearRRPPJefeEntrada(NUM_RRPPJEFE, i, NUM_RRPPMINION));
//		}
//		
//		for(int i = 1; i <= NUM_EVENTO; i++){
//			dao.insertar(crearRRPPEvento(NUM_RRPPJEFE, i));
//		}
//		


		
		System.out.println("FIN DEL LLENADO DE BBDD");
		
		return;
	}
	
}
