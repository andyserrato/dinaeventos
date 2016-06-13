package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Organizadores;

/**
 * DAO for Organizadores
 */
@Stateless
public class OrganizadoresDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Organizadores entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Organizadores entity = em.find(Organizadores.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Organizadores findById(int id) {
		return em.find(Organizadores.class, id);
	}

	public Organizadores update(Organizadores entity) {
		return em.merge(entity);
	}

	public List<Organizadores> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Organizadores> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT o FROM Organizadores o ORDER BY o.idorganizador",
						Organizadores.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
