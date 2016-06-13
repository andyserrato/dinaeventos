package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.RrppMinion;

/**
 * DAO for RrppMinion
 */
@Stateless
public class RrppMinionDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(RrppMinion entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		RrppMinion entity = em.find(RrppMinion.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public RrppMinion findById(int id) {
		return em.find(RrppMinion.class, id);
	}

	public RrppMinion update(RrppMinion entity) {
		return em.merge(entity);
	}

	public List<RrppMinion> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<RrppMinion> findAllQuery = em.createQuery(
				"SELECT DISTINCT r FROM RrppMinion r ORDER BY r.idrrppMinion",
				RrppMinion.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
