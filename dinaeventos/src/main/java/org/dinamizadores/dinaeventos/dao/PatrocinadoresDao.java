package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Patrocinadores;

/**
 * DAO for Patrocinadores
 */
@Stateless
public class PatrocinadoresDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Patrocinadores entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Patrocinadores entity = em.find(Patrocinadores.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Patrocinadores findById(int id) {
		return em.find(Patrocinadores.class, id);
	}

	public Patrocinadores update(Patrocinadores entity) {
		return em.merge(entity);
	}

	public List<Patrocinadores> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Patrocinadores> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM Patrocinadores p ORDER BY p.idpatrocinador",
						Patrocinadores.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
