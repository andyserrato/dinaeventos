package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.dinamizadores.dinaeventos.model.Usuario;

/**
 * DAO for Usuario
 */
@Stateless
public class UsuarioDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	public void create(Usuario entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Usuario entity = em.find(Usuario.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Usuario findById(int id) {
		return em.find(Usuario.class, id);
	}

	public Usuario update(Usuario entity) {
		return em.merge(entity);
	}

	public List<Usuario> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Usuario> findAllQuery = em.createQuery(
				"SELECT DISTINCT u FROM Usuario u ORDER BY u.idusuario",
				Usuario.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}

	public Usuario login(String email, String password) {
		Usuario usuario = null;
		TypedQuery<Usuario> query = em.createQuery(
				"SELECT u FROM Usuario u WHERE u.email = :email",
				Usuario.class);
		// TODO [ANDY] Hash del password 
		try {
			usuario = query.setParameter("email", email).getSingleResult();
		} catch (Exception e) {
			// TODO [ANDY] Colocar login en el sistema
			System.err.println("Error: " + e.getMessage());
		}
		
		return usuario;
	}
}
