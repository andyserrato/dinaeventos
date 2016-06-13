package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Redessociales;

/**
 * DAO for Redessociales
 */
@Stateless
public class RedessocialesDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Redessociales entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Redessociales entity = em.find(Redessociales.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Redessociales findById(int id) {
		return em.find(Redessociales.class, id);
	}

	public Redessociales update(Redessociales entity) {
		return em.merge(entity);
	}

	public List<Redessociales> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Redessociales> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT r FROM Redessociales r ORDER BY r.idredessociales",
						Redessociales.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
