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
import org.dinamizadores.dinaeventos.model.Tipoentrada;

/**
 * 
 */
@Stateless
@Path("/tipoentradas")
public class TipoentradaEndpoint {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(Tipoentrada entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(TipoentradaEndpoint.class)
						.path(String.valueOf(entity.getIdtipoentrada()))
						.build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") int id) {
		Tipoentrada entity = em.find(Tipoentrada.class, id);
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
		TypedQuery<Tipoentrada> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT t FROM Tipoentrada t LEFT JOIN FETCH t.entradas WHERE t.idtipoentrada = :entityId ORDER BY t.idtipoentrada",
						Tipoentrada.class);
		findByIdQuery.setParameter("entityId", id);
		Tipoentrada entity;
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
	public List<Tipoentrada> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Tipoentrada> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT t FROM Tipoentrada t LEFT JOIN FETCH t.entradas ORDER BY t.idtipoentrada",
						Tipoentrada.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Tipoentrada> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") int id, Tipoentrada entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id != entity.getIdtipoentrada()) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(Tipoentrada.class, id) == null) {
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
