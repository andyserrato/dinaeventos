package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.RrppJefes;

/**
 * DAO for RrppJefes
 */
@Stateless
public class RrppJefesDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(RrppJefes entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		RrppJefes entity = em.find(RrppJefes.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public RrppJefes findById(int id) {
		return em.find(RrppJefes.class, id);
	}

	public RrppJefes update(RrppJefes entity) {
		return em.merge(entity);
	}

	public List<RrppJefes> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<RrppJefes> findAllQuery = em.createQuery(
				"SELECT DISTINCT r FROM RrppJefes r ORDER BY r.idrrppJefe",
				RrppJefes.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
