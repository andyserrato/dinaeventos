package org.dinamizadores.dinaeventos.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.dinamizadores.dinaeventos.model.Patrocinador;

/**
 * 
 */
@Stateless
@Path("/patrocinadors")
public class PatrocinadorEndpoint {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Patrocinador entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(PatrocinadorEndpoint.class)
						.path(String.valueOf(entity.getIdpatrocinador()))
						.build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") int id) {
		Patrocinador entity = em.find(Patrocinador.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") int id) {
		TypedQuery<Patrocinador> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM Patrocinador p LEFT JOIN FETCH p.globalCodigopostal LEFT JOIN FETCH p.eventos WHERE p.idpatrocinador = :entityId ORDER BY p.idpatrocinador",
						Patrocinador.class);
		findByIdQuery.setParameter("entityId", id);
		Patrocinador entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public List<Patrocinador> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Patrocinador> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT p FROM Patrocinador p LEFT JOIN FETCH p.globalCodigopostal LEFT JOIN FETCH p.eventos ORDER BY p.idpatrocinador",
						Patrocinador.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Patrocinador> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") int id, Patrocinador entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id != entity.getIdpatrocinador()) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Patrocinador.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
}
