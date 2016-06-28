package org.dinamizadores.dinaeventos.utiles;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.*;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

@Stateless
@Loggable
public class BBDDFaker {
	/** Clase Faker para generar cosas random. */
	private Faker faker;
	
	/** Acceso a la capa DAO para persistir los datos. */
	@EJB
	private DAOGenerico dao;
	
	public DdFormapago crearFormaPago(){
		DdFormapago formaPago = new DdFormapago();
		
		formaPago.setNombre(faker.beer().name());
		
		System.out.println(formaPago.getNombre());
		
		return formaPago;
	}
	
	public DdOrigenEntrada crearOrigenEntrada(){
		DdOrigenEntrada d = new DdOrigenEntrada();
		
		d.setNombre(faker.color().name());
		return d;
	}
	
	public DdPatrocinadorEvento crearPatrocinadorEvento(int idpatrocinador, int idevento){
		return new DdPatrocinadorEvento(new DdPatrocinadorEventoId(idpatrocinador, idevento));
	}
	
	public DdRrppEvento crearRRPPEvento(int idRRPP, int idEvento){
		return new DdRrppEvento(new DdRrppEventoId(idEvento, idRRPP));
	}
	
	public DdRrppJefeEntrada crearRRPPJefeEntrada(int idRRPPJefe, int idEntrada, int idRRPPMinion){
		return new DdRrppJefeEntrada(new DdRrppJefeEntradaId(idRRPPJefe, idEntrada), idRRPPMinion);
	}
	
	public DdSexo crearSexo(){
		DdSexo d = new DdSexo();
		
		d.setNombre(faker.color().name());
		
		return d;
	}
	
	public DdTipoComplemento crearTipoComplemento(){
		DdTipoComplemento d = new DdTipoComplemento();
		
		d.setDescripcion(faker.chuckNorris().fact());
		d.setNombre(faker.beer().name());
		d.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
		
		return d;
	}
	
	public DdTipoEntrada crearTipoEntrada(){
		DdTipoEntrada d = new DdTipoEntrada();
		
		d.setNombre(faker.book().title());
		d.setPrecio(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
		
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
	
	public Entrada crearEntrada(int idEvento, int idFormaPago, int idOrigen, int idTipoEntrada, int idTipoIva, int idUsuario){
		Entrada e = new Entrada();
		
		e.setActiva(true);
		e.setDentrofuera(false);
//		e.setIdevento(idEvento);
//		e.setIdformapago(idFormaPago);
//		e.setIdorigen(idOrigen);
//		e.setIdtipoentrada(idTipoEntrada);
//		e.setIdtipoiva(idTipoIva);
//		e.setIdusuario(idUsuario);
		e.setNumeroserie(Long.toString(faker.number().randomNumber()));
		e.setPrecio(Float.valueOf(Double.toString(faker.number().randomDouble(2, 1, 100))));
		e.setTicketgenerado(false);
		e.setValidada(false);
		e.setVendida(true);
				
		return e;
	}
	
	public Evento crearEvento(int idCodigoPostal, int idOrganizador, int idTipoEvento){
		Evento e = new Evento();
		
		e.setActivo(true);
		e.setAforo(25000);
		e.setDescripcion(faker.commerce().department());
		e.setEntradasDisponibles(e.getAforo());
		e.setFechaAlta(faker.date().past(63072000, TimeUnit.SECONDS));
		e.setFechaIni(new Date(e.getFechaAlta().getTime() + 1296000000));
		e.setFechaFin(e.getFechaIni());
		e.setIdcodigopostal(idCodigoPostal);
		e.setIdorganizador(idOrganizador);
		e.setIdtipoevento(idTipoEvento);
		e.setLatitud(0);
		e.setLongitud(0);
		e.setNombre(faker.color().name());
		e.setNombrelugar(faker.address().state());
		
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
	
	public Patrocinadores crearPatrocinador(int idCodigoPostal){
		Patrocinadores p = new Patrocinadores();
		
//		p.setCuentacorriente(faker.finance().creditCard());
		p.setDireccion(faker.address().country());
		p.setEmail(faker.internet().emailAddress());
		p.setIban(faker.finance().iban("ES"));
		p.setIdcodigopostal(idCodigoPostal);
		p.setNombre(faker.name().firstName());
		p.setTelefono("663149759");
		
		return p;
	}
	
	public Permisos crearPermisos(){
		Permisos p = new Permisos();
		
		p.setNombre(faker.beer().name());
		
		return p;
	}
	
	public Redessociales crearRedSocial(){
		Redessociales r = new Redessociales();
		
		
		return r;
	}
	
	public Roles crearRol(){
		Roles r = new Roles();
		
		r.setNombre(faker.color().name());
		
		return r;
	}
	
	public RolesPermisos crearRolesPermisos(int idRol, int idPermisos){
		return new RolesPermisos(new RolesPermisosId(idRol, idPermisos));
	}
	
	public RrppJefes crearRRPPJefes(int idOrganizador, int idUsuario){
		RrppJefes r = new RrppJefes();
		
		r.setCodigoPromocional(faker.hacker().verb());
		r.setIdOrganizador(idOrganizador);
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
	
	public Usuario crearUsuario(int idCodigoPostal, int idRedesSociales, int idRol, int idSexo){
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
		u.setIdredessociales(idRedesSociales);
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
		final int NUM_ORIGENENTRADA = 3;
		final int NUM_SEXO = 3;
		final int NUM_EVENTO = 2;
		
		final int NUM_TIPOCOMPLEMENTO = 3;
		final int NUM_TIPOENTRADA = 3;
		final int NUM_TIPOEVENTO = 10;
		final int NUM_TIPOSIVA = 3;
		final int NUM_ENTRADA = 50;
		final int NUM_ORGANIZADORES = 2;
		final int NUM_PATROCINADORES = 2;
		final int NUM_PERMISOS= 3;
		final int NUM_REDESSOCIALES = 3;
		final int NUM_ROLES = 3;
		final int NUM_RRPPJEFE = 1;
		final int NUM_RRPPMINION = 3;
		final int NUM_USUARIO = 30;
		final int NUM_CODIGOSPOSTALES = 53169; 
		
		System.out.println("INICIO DEL LLENADO DE LA   BBDD");
		faker = new Faker();
		
		for(int i = 0; i < NUM_FORMAPAGO; i++){
			dao.insertar(crearFormaPago());
		}
		
		for(int i = 0; i < NUM_ORIGENENTRADA; i++){
			dao.insertar(crearOrigenEntrada());
		}
		
		for(int i = 0; i < NUM_SEXO; i++){
			dao.insertar(crearSexo());
		}
		
		for(int i = 0; i < NUM_TIPOEVENTO; i++){
			dao.insertar(crearTipoEvento());
		}
		
		for(int i = 0; i < NUM_ORGANIZADORES; i++){
			dao.insertar(crearOrganizadores(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES)));
		}
		
//		for(int i = 0; i < NUM_EVENTO; i++){
//			dao.insertar(crearEvento(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), faker.number().numberBetween(1, NUM_ORGANIZADORES), faker.number().numberBetween(1, NUM_TIPOEVENTO)));
//		}
		
//		for(int i = 0; i < NUM_TIPOCOMPLEMENTO; i++){
//			dao.insertar(crearTipoComplemento());
//		}
//		
//		for(int i = 0; i < NUM_TIPOENTRADA; i++){
//			dao.insertar(crearTipoEntrada());
//		}
//		
//		
//		for(int i = 0; i < NUM_TIPOSIVA; i++){
//			dao.insertar(crearTiposIva());
//		}
//		
//		for(int i = 0; i < NUM_PERMISOS; i++){
//			dao.insertar(crearPermisos());
//		}
//		
//		for(int i = 0; i < NUM_ROLES; i++){
//			dao.insertar(crearRol());
//		}
//		
//		for(int i = 0; i < NUM_REDESSOCIALES; i++){
//			dao.insertar(crearRedSocial());
//		}
//		

//		
//		for(int i = 0; i < NUM_PATROCINADORES; i++){
//			dao.insertar(crearPatrocinador(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES)));
//		}
//		
//		for(int i = 0; i < NUM_USUARIO; i++){
//			dao.insertar(crearUsuario(faker.number().numberBetween(1, NUM_CODIGOSPOSTALES), faker.number().numberBetween(1, NUM_REDESSOCIALES), faker.number().numberBetween(1, NUM_ROLES), faker.number().numberBetween(1, NUM_SEXO)));
//		}
//		

		
//		for(int i = 0; i < NUM_ENTRADA; i++){ 
//			dao.insertar(crearEntrada(faker.number().numberBetween(1, NUM_EVENTO), faker.number().numberBetween(1, NUM_FORMAPAGO), faker.number().numberBetween(1, NUM_ORIGENENTRADA), faker.number().numberBetween(1, NUM_TIPOENTRADA), faker.number().numberBetween(1, NUM_TIPOSIVA), faker.number().numberBetween(1, NUM_USUARIO)));
//		}
//		
//		for(int i = 0; i < NUM_RRPPJEFE; i++){
//			dao.insertar(crearRRPPJefes(faker.number().numberBetween(1, NUM_ORGANIZADORES), faker.number().numberBetween(1, NUM_USUARIO)));
//		}
//		
//		for(int i = 0; i < NUM_RRPPMINION; i++){
//			dao.insertar(crearRRPPMinion(NUM_RRPPJEFE, faker.number().numberBetween(1, NUM_USUARIO)));
//		}
//		
//		for(int i = 0; i < NUM_ENTRADA; i++){
//			dao.insertar(crearRRPPJefeEntrada(NUM_RRPPJEFE, i, NUM_RRPPMINION));
//		}
//		
//		for(int i = 1; i <= NUM_EVENTO; i++){
//			dao.insertar(crearRRPPEvento(NUM_RRPPJEFE, i));
//		}
//		
//		for(int i = 0; i < NUM_EVENTO; i++){
//			dao.insertar(crearPatrocinadorEvento(faker.number().numberBetween(1, NUM_PATROCINADORES), i));
//		}
//		
//		for(int i = 0; i < NUM_ROLES; i++){
//			dao.insertar(crearRolesPermisos(i, faker.number().numberBetween(1, NUM_PERMISOS)));
//		}
		
		System.out.println("FIN DEL LLENADO DE BBDD");
		
		return;
	}
	
}
