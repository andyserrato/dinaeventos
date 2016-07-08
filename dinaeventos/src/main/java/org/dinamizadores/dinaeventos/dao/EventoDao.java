package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Patrocinadores;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

/**
 * DAO for Evento
 */
@Stateless
public class EventoDao {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;
	private final Logger log = LogManager.getLogger(EventoDao.class);
	
	public void create(Evento entity) {
		em.persist(entity);
	}

	public void deleteById(int id) {
		Evento entity = em.find(Evento.class, id);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public Evento findById(int id) {
		return em.find(Evento.class, id);
	}

	public Evento update(Evento entity) {
		return em.merge(entity);
	}

	public List<Evento> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Evento> findAllQuery = em.createQuery(
				"SELECT DISTINCT e FROM Evento e ORDER BY e.idevento",
				Evento.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	@Loggable
	public Evento crearEvento(Evento evento) {
		log.debug("Persistimos el evento");
		em.persist(evento);
		log.debug("Evento persistido id: " + evento.getIdevento());
		log.debug("Colocamos a cada patrocinador el id del evento");
		for (Patrocinadores patrocinador : evento.getPatrocinadores()) {
			log.debug("Probamos con el id");
			patrocinador.setIdEvento(evento.getIdevento());
			log.debug("Persistimos el patrocinador");
			em.persist(patrocinador);
			log.debug("Patrocinador id : " + patrocinador.getIdpatrocinador());
			
		}
		
		return evento;
	}
}
