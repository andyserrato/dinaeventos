package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Entrada;

/**
 * DAO for Entrada
 */
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
}
