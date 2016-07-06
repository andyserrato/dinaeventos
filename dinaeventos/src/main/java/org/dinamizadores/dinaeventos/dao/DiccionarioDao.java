package org.dinamizadores.dinaeventos.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.dinamizadores.dinaeventos.model.DdSexo;
import org.dinamizadores.dinaeventos.model.DdTipoEvento;
import org.dinamizadores.dinaeventos.model.GlobalCodigospostales;
import org.dinamizadores.dinaeventos.model.GlobalProvincias;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

@Stateless
@Loggable
public class DiccionarioDao extends DAOGenerico{
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;
	
	public List<DdSexo> getDdSexos() {
		TypedQuery<DdSexo> findAllQuery = em.createQuery(
				"SELECT sexo FROM DdSexo sexo ORDER BY sexo.idsexo",
				DdSexo.class);
		
		return findAllQuery.getResultList();
	}
	
	public List<DdTipoEvento> getDdTiposDeEvento() {
		TypedQuery<DdTipoEvento> findAllQuery = em.createQuery(
				"SELECT te FROM DdTipoEvento te ORDER BY te.idTipoEvento",
				DdTipoEvento.class);
		
		return findAllQuery.getResultList();
	}
	
	public List<GlobalProvincias> getDdGlobalProvincias() {
		TypedQuery<GlobalProvincias> findAllQuery = em.createQuery(
				"SELECT provincias FROM GlobalProvincias provincias ORDER BY provincias.idprovincia",
				GlobalProvincias.class);
		
		return findAllQuery.getResultList();
	}

	public List<GlobalCodigospostales> actualizaLocalidadesByCP(String idCodigoPostal) {
		TypedQuery<GlobalCodigospostales> findLocalidadesByCP = em.createQuery(
				"SELECT codp FROM GlobalCodigospostales codp WHERE codp.codigo LIKE :idCodigoPostal ORDER BY codp.localidad",
				GlobalCodigospostales.class);
		
		findLocalidadesByCP.setParameter("idCodigoPostal", idCodigoPostal);
		
		return findLocalidadesByCP.getResultList();
	}
	
	
}
