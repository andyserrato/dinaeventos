package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

	public Entrada update(Entrada entity) {
		return em.merge(entity);
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
	
	public List<DdTipoEntrada> listTipoEntrada() {
		TypedQuery<DdTipoEntrada> findAllQuery = em.createQuery(
				"SELECT DISTINCT te FROM DdTipoEntrada te ORDER BY te.idtipoentrada",
				DdTipoEntrada.class);
		
		return findAllQuery.getResultList();
	}
	
	public List<DdTipoComplemento> listTipoComplemento() {
		TypedQuery<DdTipoComplemento> findAllQuery = em.createQuery(
				"SELECT DISTINCT te FROM DdTipoComplemento te ORDER BY te.idtipocomplemento",
				DdTipoComplemento.class);
		
		return findAllQuery.getResultList();
	}
}
