package org.dinamizadores.dinaeventos.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;

import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.view.DdTipoEntradaBean;

/**
 * DAO for Entrada
 */
@Loggable
@Stateless
public class EntradaDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Entrada entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Entrada entity = em.find(Entrada.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Entrada findById(int id) {
		return em.find(Entrada.class, id);
	}

	public Entrada findByNumeroSerie(String numeroSerie) {
		TypedQuery<Entrada> findByNumeroSerie = em.createQuery(
				"SELECT distinct e FROM Entrada e JOIN FETCH e.usuario JOIN FETCH e.ddTipoEntrada where e.numeroserie = :numeroSerie",
				Entrada.class);
		
		findByNumeroSerie.setParameter("numeroSerie", numeroSerie);
		
		
		if (!findByNumeroSerie.getResultList().isEmpty())
			return findByNumeroSerie.getResultList().get(0);
		else
			return null;
			
	}

	
	public Entrada update(Entrada entity) {
		return em.merge(entity);
	}
	
	public List<Entrada> findByField(String textoBusqueda){
		TypedQuery<Entrada> findByField = em.createQuery(
				"SELECT distinct e FROM Entrada e where e.usuario.dni LIKE :dni OR usuario.nombre LIKE :nombre" + 
				" OR usuario.apellidos LIKE :apellidos OR usuario.email LIKE :email",
				Entrada.class);
		
		findByField.setParameter("dni", "%" + textoBusqueda + "%");
		findByField.setParameter("nombre", "%" + textoBusqueda + "%");
		findByField.setParameter("apellidos", "%" + textoBusqueda + "%");
		findByField.setParameter("email", "%" + textoBusqueda + "%");

		
		return findByField.getResultList();
	}

	//Este método devuelve las entradas para un determinado evento
	public List<Entrada> listByEventID(int idEvento){
		TypedQuery<Entrada> findByEventID = em.createQuery(
				"SELECT distinct e FROM Entrada e JOIN FETCH e.usuario where e.evento.idevento = :idevento",
				Entrada.class);
		
		findByEventID.setParameter("idevento", idEvento);
		
		return findByEventID.getResultList();
	}
	
	public List<Entrada> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Entrada> findAllQuery = em.createQuery(
				"SELECT DISTINCT e FROM Entrada e ORDER BY e.identrada",
				Entrada.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	public List<DdTipoEntrada> listTipoEntrada(Integer idEvento) {
		TypedQuery<DdTipoEntrada> findAllQuery = em.createQuery(
				"SELECT DISTINCT te FROM DdTipoEntrada te, Evento e WHERE e.idevento = :idEvento ORDER BY te.idtipoentrada",
				DdTipoEntrada.class);
		findAllQuery.setParameter("idEvento", idEvento);
		
		return findAllQuery.getResultList();
	}
	
	public List<DdTipoComplemento> listTipoComplemento(Integer idEvento) {
		TypedQuery<DdTipoComplemento> findAllQuery = em.createQuery(
				"SELECT DISTINCT te FROM DdTipoComplemento te, Evento e WHERE e.idevento = :idEvento ORDER BY te.idTipoComplemento ",
				DdTipoComplemento.class);
		findAllQuery.setParameter("idEvento", idEvento);
		return findAllQuery.getResultList();
	}
	
	public Integer getEntradaDniEvento(Integer idUsuario, Integer idEvento, String dni) {
		TypedQuery<Integer> findAllQuery = em.createQuery(
				"SELECT e.identrada FROM Entrada e JOIN usuario u ON u.idusuario = e.idusuario WHERE e.idusuario = :idUsuario AND e.idevento = :idEvento and u.dni = :dni",
				Integer.class);
		findAllQuery.setParameter("idUsuario", idUsuario);
		findAllQuery.setParameter("dni", dni);
		findAllQuery.setParameter("idEvento", idEvento);
		
		return findAllQuery.getSingleResult();
	}
}
