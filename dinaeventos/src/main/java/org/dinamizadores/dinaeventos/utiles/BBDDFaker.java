package org.dinamizadores.dinaeventos.utiles;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.dinamizadores.dinaeventos.model.*;

public class BBDDFaker {
	/** Clase Faker para generar cosas random. */
	private Faker f;
	
	public BBDDFaker(){
		f = new Faker();
	}
	
	public DdFormapago crearFormaPago(){
		DdFormapago formaPago = new DdFormapago();
		
		formaPago.setNombre(f.beer().name());
		
		System.out.println(formaPago.getNombre());
		
		return formaPago;
	}
	
	public DdOrigenEntrada crearOrigenEntrada(){
		DdOrigenEntrada d = new DdOrigenEntrada();
		
		d.setNombre(f.chuckNorris().fact());
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
		
		d.setNombre(f.color().name());
		
		return d;
	}
	
	public DdTipoComplemento crearTipoComplemento(){
		DdTipoComplemento d = new DdTipoComplemento();
		
		d.setDescripcion(f.chuckNorris().fact());
		d.setNombre(f.beer().name());
		d.setPrecio(BigDecimal.valueOf(f.number().randomDouble(2, 1, 100)));
		
		return d;
	}
	
	public DdTipoEntrada crearTipoEntrada(){
		DdTipoEntrada d = new DdTipoEntrada();
		
		d.setNombre(f.book().title());
		d.setPrecio(BigDecimal.valueOf(f.number().randomDouble(2, 1, 100)));
		
		return d;
	}
	
	public DdTipoEvento crearTipoEvento(){
		DdTipoEvento d = new DdTipoEvento();
		
		d.setTipoEvento(f.commerce().department());

		return d;
	}
	
	public DdTiposIva crearTiposIva(){
		DdTiposIva d = new DdTiposIva();
		
		d.setNombre(f.lorem().fixedString(7));
		
		return d;
	}
	
	public Entrada crearEntrada(int idEvento, int idFormaPago, int idOrigen, int idTipoEntrada, int idTipoIva, int idUsuario){
		Entrada e = new Entrada();
		
		e.setActiva(true);
		e.setDentrofuera(false);
		e.setIdevento(idEvento);
		e.setIdformapago(idFormaPago);
		e.setIdorigen(idOrigen);
		e.setIdtipoentrada(idTipoEntrada);
		e.setIdtipoiva(idTipoIva);
		e.setIdusuario(idUsuario);
		e.setNumeroserie(Long.toString(f.number().randomNumber()));
		e.setPrecio(Float.valueOf(Double.toString(f.number().randomDouble(2, 1, 100))));
		e.setTicketgenerado(false);
		e.setValidada(false);
		e.setVendida(false);
				
		return e;
	}
	
	public Evento crearEvento(int idCodigoPostal, int idOrganizador, int idTipoEvento){
		Evento e = new Evento();
		
		e.setAcitvo(true);
		e.setAforo(Integer.parseInt(Double.toString(f.number().randomDouble(0, 100, 25000))));
		e.setDescripcion(f.commerce().department());
		e.setEntradasDisponibles(e.getAforo());
		e.setFechaAlta(f.date().past(63072000, TimeUnit.SECONDS));
		e.setFechaIni(new Date(e.getFechaAlta().getTime() + 1296000000));
		e.setFechaFin(e.getFechaIni());
		e.setIdcodigopostal(idCodigoPostal);
		e.setIdorganizador(idOrganizador);
		e.setIdtipoevento(idTipoEvento);
		e.setLatitud(Integer.valueOf(f.address().latitude()));
		e.setLongitud(Integer.valueOf(f.address().longitude()));
		e.setNombre(f.superhero().name());
		e.setNombrelugar(f.address().cityName());
		
		return e;
	}
	
	public Organizadores crearOrganizadores(int idCodigoPostal, int idOrganizador){
		Organizadores o = new Organizadores();
		
		o.setCuentacorriente(f.finance().bic());
		o.setDescripcion(f.superhero().descriptor());
		o.setDireccion(f.address().firstName());
		o.setEmail(f.internet().emailAddress());
		
		//Con dos cojones jaajajajjaj
		o.setEnlacefb("http://www.pp.es");
		o.setEnlacetw("http://www.pp.es");
		
		o.setIban(f.finance().iban("ES"));
		o.setIdcodigopostal(idCodigoPostal);
		o.setIdorganizador(idOrganizador);
		o.setNombre(f.name().fullName());
		
		//No nos compliquemos, el usuario medio pondrá esta... putos usuarios...
		o.setPassword("123456");
		o.setTelefono(f.phoneNumber().cellPhone());
		
		return o;
	}
	
	public Patrocinadores crearPatrocinador(int idCodigoPostal){
		Patrocinadores p = new Patrocinadores();
		
		p.setCuentacorriente(f.finance().creditCard());
		p.setDireccion(f.address().streetAddress());
		p.setEmail(f.internet().emailAddress());
		p.setIban(f.finance().iban("ES"));
		p.setIdcodigopostal(idCodigoPostal);
		p.setNombre(f.name().fullName());
		p.setTelefono(f.phoneNumber().cellPhone());
		
		return p;
	}
	
	public Permisos crearPermisos(){
		Permisos p = new Permisos();
		
		p.setNombre(f.beer().name());
		
		return p;
	}
	
	public Redessociales crearRedSocial(){
		Redessociales r = new Redessociales();
		
		
		return r;
	}
	
	public Roles crearRol(){
		Roles r = new Roles();
		
		r.setNombre(f.university().name());
		
		return r;
	}
	
	public RolesPermisos crearRolesPermisos(int idRol, int idPermisos){
		return new RolesPermisos(new RolesPermisosId(idRol, idPermisos));
	}
	
	public RrppJefes crearRRPPJefes(int idOrganizador){
		RrppJefes r = new RrppJefes();
		
		/*r.setApellido1(f.name().firstName());
		r.setApellido2(f.name().lastName());
		r.setCodigoPromocional(f.hacker().verb());
		r.setEmail(f.internet().emailAddress());
		r.setIdorganizador(idOrganizador);
		r.setLimiteEntradas(0);
		r.setNombre(f.name().nameWithMiddle());
		r.setTelefono(f.phoneNumber().cellPhone());*/
		
		return r;
	}
	
	public RrppMinion crearRRPPMinion(int idJefe){
		RrppMinion r = new RrppMinion();
		
		/*r.setApellido1(f.name().firstName());
		r.setApellido2(f.name().lastName());
		r.setCoigoPromocional(f.hacker().verb());
		r.setEmail(f.internet().emailAddress());
		r.setIdjefe(idJefe);
		r.setLimiteEntradas(0);
		r.setNombre(f.name().nameWithMiddle());
		r.setTelefono(f.phoneNumber().cellPhone());*/
		
		return r;
	}
	
	public Usuario crearUsuario(int idCodigoPostal, int idRedesSociales, int idRol, int idSexo){
		Usuario u = new Usuario();
		
		u.setActivo(true);
		u.setApellido1(f.name().lastName());
		u.setBloqueado(false);
		u.setCuentacorriente(f.finance().creditCard());
		u.setDireccion(f.address().streetAddress());
		u.setDni(String.valueOf(f.number().numberBetween(0, 99999999)));
		u.setEmail(f.internet().emailAddress());
		u.setFechanac(f.date().past(10950, TimeUnit.DAYS));
		u.setFotoperfil(null);
		u.setIban(f.finance().iban("ES"));
		u.setIdcodigopostal(idCodigoPostal);
		u.setIdredessociales(idRedesSociales);
		u.setIdrol(idRol);
		u.setIdsexo(idSexo);
		u.setNombre(f.name().firstName());
		u.setPassword("123456");
		u.setTelefono(f.phoneNumber().cellPhone());
		u.setUltimologin(new Date());
		
		return u;
	}
	
	public void llenarBBDD(){
		final int NUM_FORMAPAGO = 3;
		final int NUM_ORIGENENTRADA = 3;
		final int NUM_SEXO = 3;
		final int NUM_TIPOCOMPLEMENTO = 3;
		final int NUM_TIPOENTRADA = 3;
		final int NUM_TIPOEVENTO = 3;
		final int NUM_TIPOSIVA = 3;
		final int NUM_ENTRADA = 3;
		final int NUM_EVENTO = 3;
		final int NUM_ORGANIZADORES = 3;
		final int NUM_PATROCINADORES = 3;
		final int NUM_PERMISOS= 3;
		final int NUM_REDESSOCIALES = 3;
		final int NUM_ROLES = 3;
		final int NUM_RRPPJEFE = 3;
		final int NUM_RRPPMINION = 3;
		final int NUM_USUARIO = 3;
		
		for(int i = 0; i < NUM_FORMAPAGO; i++){
			crearFormaPago();
		}
	}
	
}
