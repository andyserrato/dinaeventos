package org.dinamizadores.dinaeventos.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.dto.DdGenerico;
import org.dinamizadores.dinaeventos.dto.EntradasTiempoDTO;
import org.dinamizadores.dinaeventos.dto.EventoDTO;
import org.dinamizadores.dinaeventos.dto.TipoVentaBasico;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

/**
 * DAO for Evento
 */
@Stateless
public class EventoDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;
	private final Logger log = LogManager.getLogger(EventoDao.class);
	@EJB
	UsuarioDao usuarioDao;
	public void create(Evento entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Evento entity = em.find(Evento.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Evento findById(int id) {
		return em.find(Evento.class, id);
	}

	public Evento update(Evento entity) {
		return em.merge(entity);
	}

	public List<Evento> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Evento> findAllQuery = em.createQuery(
				"SELECT DISTINCT e FROM Evento e ORDER BY e.idevento",
				Evento.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	@Loggable
	public Evento crearEvento(Evento evento) {
		log.debug("Persistimos el evento");
		evento.setCodigoPostal(null);
		em.persist(evento);
		log.debug("Evento persistido id: " + evento.getIdevento());
		
		log.debug("Colocamos a cada patrocinador el id del evento");
		for (Patrocinadores patrocinador : evento.getPatrocinadores()) {
			patrocinador.setIdEvento(evento.getIdevento());
			log.debug("Persistimos el patrocinador");
			em.persist(patrocinador);
			log.debug("Patrocinador id : " + patrocinador.getIdpatrocinador());
			
		}
		
		log.debug("Persistimos Tipo Complemento");
		for (DdTipoComplemento complemento : evento.getDdTipoComplementos()) {
			complemento.setIdEvento(evento.getIdevento());
			log.debug("Persistimos el complemento");
			em.persist(complemento);
			log.debug("Complemento id: " + complemento.getIdTipoComplemento());
			
		}
		
		log.debug("Persistimos Tipo entrada");
		for (DdTipoEntrada tipoEntrada : evento.getDdTipoEntradas()) {
			tipoEntrada.setIdEvento(evento.getIdevento());
			log.debug("Persistimos el tipo de entrada");
			em.persist(tipoEntrada);
			log.debug("Tipo de entrada id: " + tipoEntrada.getIdtipoentrada());
		}
		
		return evento;
	}
	
	public EventoDTO getVisionGlobal(int idEvento){
		EventoDTO evento = null;
		Query q = em.createNativeQuery("SELECT e.aforo AS entradasTotales, (SELECT count(*) FROM entrada EN WHERE EN.idevento = e.idevento AND EN.vendida = TRUE) AS entradasVendidas, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.validada = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'papel') AS entradasValidadasPapel, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'online') AS entradasOnline, (SELECT sum(DTE.precio) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idtipoentrada = DTE.idtipoentrada AND EN.vendida = TRUE) AS ingresosTotales, (SELECT count(*) FROM entrada EN, usuario U WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.vendida = TRUE AND U.idsexo = 1) AS numHombres, (SELECT count(*) FROM entrada EN, usuario U WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.vendida = TRUE AND U.idsexo = 2) AS numMujeres FROM evento e WHERE e.idevento = " + idEvento, "Evento2EventoDTO");
		evento = (EventoDTO) q.getSingleResult();
		
		return evento;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasTiempoDTO> getNumEntradasTiempo(int idEvento){
		ArrayList<EntradasTiempoDTO> lista = null;
		Query q = em.createNativeQuery("SELECT count(*) AS cantidad, E.fechavendida AS fecha FROM entrada E WHERE E.idevento = " + idEvento + " AND E.vendida = true GROUP BY E.fechavendida", "getEntradasTiempoDTO");
		
		lista = (ArrayList<EntradasTiempoDTO>) q.getResultList();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DdGenerico> getLugaresDeOrigen(int idEvento){
		ArrayList<DdGenerico> lista = null;
		
		Query q = em.createNativeQuery("SELECT count(GP.idprovincia) AS cantidad, GP.nombre AS descripcion, GP.idprovincia AS id FROM entrada E, usuario U, global_codigospostales GCP, global_provincias GP WHERE E.idevento = " + idEvento + " AND E.idusuario = U.idusuario AND GCP.idcodigopostal = U.idcodigopostal AND GCP.idprovincia = GP.idprovincia GROUP BY GP.idprovincia ORDER BY count(GP.idprovincia) DESC, GP.nombre", "getLugaresDeOrigen");
		
		lista = (ArrayList<DdGenerico>) q.getResultList();
		
		return lista;
	}
	
	public int getNumeroVentasPorRangoEdad(int idEvento, int edadMinima, int edadMaxima){
		int numeroVentas = 0;
		
		Query q = em.createNativeQuery("SELECT count(*) FROM entrada E, usuario U WHERE E.idevento = " + idEvento + " AND E.idusuario = U.idusuario AND timestampdiff(YEAR, U.fechanac, CURDATE()) >= " + edadMinima +" AND timestampdiff(YEAR, U.fechanac, CURDATE()) <= " + edadMaxima);
		
		numeroVentas = ((BigInteger) q.getSingleResult()).intValueExact();
		
		return numeroVentas;
	}
	
	public EventoDTO getVentasOnline(int idEvento){
		EventoDTO evento = null;
		Query q = em.createNativeQuery("SELECT e.aforo AS entradasTotales, (SELECT count(*) FROM entrada EN WHERE EN.idevento = e.idevento AND EN.vendida = TRUE) AS entradasVendidas, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.validada = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'papel') AS entradasValidadasPapel, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'online') AS entradasOnline, (SELECT sum(DTE.precio) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idtipoentrada = DTE.idtipoentrada AND EN.vendida = TRUE AND DTE.canaldeventas LIKE 'online') AS ingresosTotales, (SELECT count(*) FROM entrada EN, usuario U, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.idtipoentrada = DTE.idtipoentrada AND EN.vendida = TRUE AND U.idsexo = 1 AND DTE.canaldeventas LIKE 'online') AS numHombres, (SELECT count(*) FROM entrada EN, usuario U, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.idtipoentrada = DTE.idtipoentrada AND EN.vendida = TRUE AND U.idsexo = 2 AND DTE.canaldeventas LIKE 'online') AS numMujeres FROM evento e	WHERE e.idevento = " + idEvento, "Evento2EventoDTO");
		evento = (EventoDTO) q.getSingleResult();
		
		return evento;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasTiempoDTO> getNumEntradasTiempoVentasOnline(int idEvento){
		ArrayList<EntradasTiempoDTO> lista = null;
		Query q = em.createNativeQuery("SELECT count(*) AS cantidad, E.fechavendida AS fecha FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.vendida = true AND E.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'online' GROUP BY E.fechavendida", "getEntradasTiempoDTO");
		
		lista = (ArrayList<EntradasTiempoDTO>) q.getResultList();
		
		return lista;
	}
	
	public int getNumeroVentasPorRangoEdadVentasOnline(int idEvento, int edadMinima, int edadMaxima){
		int numeroVentas = 0;
		
		Query q = em.createNativeQuery("SELECT count(*) FROM entrada E, usuario U, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idusuario = U.idusuario AND DTE.canaldeventas LIKE 'online'  AND timestampdiff(YEAR, U.fechanac, CURDATE()) >= " + edadMinima +" AND timestampdiff(YEAR, U.fechanac, CURDATE()) <= " + edadMaxima);
		
		numeroVentas = ((BigInteger) q.getSingleResult()).intValueExact();
		
		return numeroVentas;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> getTiposEntradaByEvento(int idEvento){
		List<String> lista = Collections.emptyList();
		
		Query q = em.createNativeQuery("SELECT DISTINCT DTE.nombre AS nombre FROM dd_tipo_entrada DTE WHERE DTE.idevento = " + idEvento + " GROUP BY DTE.nombre");
		
		lista = (List<String>) q.getResultList();
		
		return (ArrayList<String>) lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasTiempoDTO> getNumEntradasTiempoPorTipoEntrada(int idEvento, String nombreTipoEntrada){
		List<EntradasTiempoDTO> lista = Collections.emptyList();
		Query q = em.createNativeQuery("SELECT count(*) AS cantidad, E.fechavendida AS fecha FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true AND DTE.nombre LIKE '" + nombreTipoEntrada + "' GROUP BY E.fechavendida, DTE.nombre", "getEntradasTiempoDTO");
		
		lista = (ArrayList<EntradasTiempoDTO>) q.getResultList();
		
		return (ArrayList<EntradasTiempoDTO>) lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DdGenerico> getTiposDeEntradaVendidas(int idEvento){
		ArrayList<DdGenerico> lista = null;
		
		Query q = em.createNativeQuery("SELECT count(*) AS cantidad, DTE.nombre AS descripcion FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true GROUP BY DTE.nombre", "getTiposEntradaVendidas");
		
		lista = (ArrayList<DdGenerico>) q.getResultList();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasTiempoDTO> getIngresosPorTiempo(int idEvento) {
		ArrayList<EntradasTiempoDTO> lista = null;
		
		Query q = em.createNativeQuery("SELECT sum(DTE.precio) AS precio, E.fechavendida AS fecha FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true GROUP BY E.fechavendida", "getIngresosPorTiempo");
		
		lista = (ArrayList<EntradasTiempoDTO>) q.getResultList();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoVentaBasico> getTiposVentaBasico(int idEvento, String tipoEntrada) {
		ArrayList<TipoVentaBasico> lista = null;
		
		Query q = em.createNativeQuery("SELECT DTE.precio AS precio, count(*) AS cantidad FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true AND DTE.nombre LIKE '" + tipoEntrada + "' GROUP BY DTE.precio", "getTiposVentaBasico");
		
		lista = (ArrayList<TipoVentaBasico>) q.getResultList();
		
		return lista;
	}
	
	public EventoDTO getVentasPapel(int idEvento){
		EventoDTO evento = null;
		Query q = em.createNativeQuery("SELECT e.aforo AS entradasTotales, (SELECT count(*) FROM entrada EN WHERE EN.idevento = e.idevento AND EN.vendida = TRUE) AS entradasVendidas, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.validada = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'papel') AS entradasValidadasPapel, (SELECT count(*) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.vendida = TRUE AND EN.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'online') AS entradasOnline, (SELECT sum(DTE.precio) FROM entrada EN, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idtipoentrada = DTE.idtipoentrada AND EN.validada = TRUE AND EN.vendida = TRUE AND DTE.canaldeventas LIKE 'papel') AS ingresosTotales, (SELECT count(*) FROM entrada EN, usuario U, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.vendida = TRUE AND U.idsexo = 1 AND EN.idtipoentrada = DTE.idtipoentrada AND EN.validada = TRUE AND DTE.canaldeventas LIKE 'papel') AS numHombres, (SELECT count(*) FROM entrada EN, usuario U, dd_tipo_entrada DTE WHERE EN.idevento = e.idevento AND EN.idusuario = U.idusuario AND EN.vendida = TRUE AND U.idsexo = 2 AND EN.idtipoentrada = DTE.idtipoentrada AND EN.validada = TRUE AND DTE.canaldeventas LIKE 'papel') AS numMujeres FROM evento e WHERE e.idevento = " + idEvento, "Evento2EventoDTO");
		evento = (EventoDTO) q.getSingleResult();
		
		return evento;
	}
	
	public int getNumeroVentasPapelPorRangoEdad(int idEvento, int edadMinima, int edadMaxima){
		int numeroVentas = 0;
		
		Query q = em.createNativeQuery("SELECT count(*) FROM entrada E, usuario U, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idusuario = U.idusuario AND E.validada = TRUE AND E.vendida = TRUE AND E.idtipoentrada = DTE.idtipoentrada AND DTE.canaldeventas LIKE 'papel' AND timestampdiff(YEAR, U.fechanac, CURDATE()) >= " + edadMinima +" AND timestampdiff(YEAR, U.fechanac, CURDATE()) <= " + edadMaxima);
		
		numeroVentas = ((BigInteger) q.getSingleResult()).intValueExact();
		
		return numeroVentas;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoVentaBasico> getTiposVentaPapelBasico(int idEvento, String tipoEntrada) {
		ArrayList<TipoVentaBasico> lista = null;
		
		Query q = em.createNativeQuery("SELECT DTE.precio AS precio, count(*) AS cantidad FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true AND E.validada = true AND DTE.canaldeventas LIKE 'papel' AND DTE.nombre LIKE '" + tipoEntrada + "' GROUP BY DTE.precio", "getTiposVentaBasico");
		
		lista = (ArrayList<TipoVentaBasico>) q.getResultList();
		
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasTiempoDTO> getIngresosPapelPorTiempo(int idEvento) {
		ArrayList<EntradasTiempoDTO> lista = null;
		
		Query q = em.createNativeQuery("SELECT sum(DTE.precio) AS precio, E.fechavendida AS fecha FROM entrada E, dd_tipo_entrada DTE WHERE E.idevento = " + idEvento + " AND E.idtipoentrada = DTE.idtipoentrada AND E.vendida = true AND E.validada = true AND DTE.canaldeventas LIKE 'papel' GROUP BY E.fechavendida", "getIngresosPorTiempo");
		
		lista = (ArrayList<EntradasTiempoDTO>) q.getResultList();
		
		return lista;
	}
	
	@Loggable
	public List<Evento> getEventosByOrganizadorIdThroughUsuarioId(int idUser) {
		Organizadores organizador = usuarioDao.getOrganizadorByUserId(idUser);
		log.debug("Nombre organizador: " + organizador.getIdorganizador());
		
		TypedQuery<Evento> findEventosByIdOrganizador = em.createQuery(
				"SELECT DISTINCT e FROM Evento e JOIN FETCH e.codigoPostal WHERE e.idorganizador = :idorganizador ORDER BY e.fechaIni DESC",
				Evento.class);
		
		findEventosByIdOrganizador.setParameter("idorganizador", organizador.getIdorganizador());
		
		return findEventosByIdOrganizador.getResultList();
	}
	
	@Loggable
	public Evento getEventoByName(String name) throws Exception {
		log.debug("Buscamos el evento de nombre: " + name);
		Evento evento = null;
		
		try {
			TypedQuery<Evento> findEventoByName = em.createQuery(
					"SELECT DISTINCT e FROM Evento e JOIN FETCH e.codigoPostal WHERE e.nombre = :name ORDER BY e.fechaIni DESC",
					Evento.class);
			
			findEventoByName.setParameter("name", name);
			evento = findEventoByName.getSingleResult();
		} catch (NoResultException nre) {
			log.debug("Evento no encontrado 'EventoDao.getEventoByName': '{}'", name);
		} catch (NonUniqueResultException nure) {
			throw new Exception("La consulta 'EventoDao.getEventoByName' debe devolver un único registro", nure);
		} catch (Exception e) {
			throw new Exception("Error en la consulta 'EventoDao.getEventoByName'", e);
		}
		
		return evento;
	}
	
	
	
}
