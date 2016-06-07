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
import org.dinamizadores.dinaeventos.model.RrppJefe;

/**
 * 
 */
@Stateless
@Path("/rrppjeves")
public class RrppJefeEndpoint {
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(RrppJefe entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(RrppJefeEndpoint.class)
						.path(String.valueOf(entity.getIdrrppJefe())).build())
				.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") int id) {
		RrppJefe entity = em.find(RrppJefe.class, id);
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
		TypedQuery<RrppJefe> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT r FROM RrppJefe r LEFT JOIN FETCH r.organizador LEFT JOIN FETCH r.rrppMinions LEFT JOIN FETCH r.ddRrppJefeEntradas LEFT JOIN FETCH r.eventos WHERE r.idrrppJefe = :entityId ORDER BY r.idrrppJefe",
						RrppJefe.class);
		findByIdQuery.setParameter("entityId", id);
		RrppJefe entity;
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
	public List<RrppJefe> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<RrppJefe> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT r FROM RrppJefe r LEFT JOIN FETCH r.organizador LEFT JOIN FETCH r.rrppMinions LEFT JOIN FETCH r.ddRrppJefeEntradas LEFT JOIN FETCH r.eventos ORDER BY r.idrrppJefe",
						RrppJefe.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<RrppJefe> results = findAllQuery.getResultList();
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") int id, RrppJefe entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id != entity.getIdrrppJefe()) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(RrppJefe.class, id) == null) {
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
