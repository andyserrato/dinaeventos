package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.model.Organizadores;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

/**
 * DAO for Organizadores
 */
@Stateless
public class OrganizadoresDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;
	private static final Logger log = LogManager.getLogger(OrganizadoresDao.class);
	
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
	
	@Loggable
	public Organizadores getOrganizadorByIdUsuario(int idUsuario) throws Exception {
		TypedQuery<Organizadores> findOrganizadorByUsuario = em.createQuery(
				"SELECT DISTINCT o FROM Organizadores o WHERE o.idusuario = :idUsuario", Organizadores.class);

		Organizadores organizador = null;

		try {
			findOrganizadorByUsuario.setParameter(":idusuario", idUsuario);
			organizador = findOrganizadorByUsuario.getSingleResult();
		} catch (NoResultException nre) {
			log.debug("Organizador no encontrado 'OrganizadoresDao.getOrganizadorByIdUsuario': idUsuario: " + idUsuario);
		} catch (NonUniqueResultException nure) {
			throw new Exception("La consulta 'UsuarioGrupoTram.getUsuarioGrupoTramInactivo' debe devolver un único registro", nure);
		} catch (Exception e) {
			throw new Exception("Error en la consulta 'UsuarioGrupoTram.getUsuarioGrupoTramInactivo'", e);
		}
		
		return organizador;
	}
}
