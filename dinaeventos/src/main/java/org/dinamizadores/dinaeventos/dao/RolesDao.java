package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Roles;

/**
 * DAO for Roles
 */
@Stateless
public class RolesDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Roles entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Roles entity = em.find(Roles.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Roles findById(int id) {
		return em.find(Roles.class, id);
	}

	public Roles update(Roles entity) {
		return em.merge(entity);
	}

	public List<Roles> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Roles> findAllQuery = em.createQuery(
				"SELECT DISTINCT r FROM Roles r ORDER BY r.idrol", Roles.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
