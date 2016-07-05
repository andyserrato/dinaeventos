package org.dinamizadores.dinaeventos.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;


/**
 * DAO Genérico para las operaciones básicas contra la BBDD
 * 
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Stateless
public class DAOGenerico {
	/** Unidad de persistencia. */
	@PersistenceContext(unitName = "dinaeventos-persistence-unit")
	private EntityManager em;
	
	public <K> Object consultar(Class<K> clase, int id) throws Exception {
		Object entidad = null;
		
		try{
			entidad = em.find(clase, id);
		} catch (Exception e){
			throw new Exception("Error consssultando la entidad con ID=" + id + " de la clase " + clase.getName());
		}
		
		return entidad;
	}
	
	public <K> Object insertar(K entidad) throws Exception {
		try{
			em.persist(entidad);
		} catch (PersistenceException p){
			throw new Exception("Error insertando la entidad ");
		} catch (NullPointerException n){
			throw new Exception("Error insertando la entidad ");
		}
		
		return entidad;
	}
	
	public <K> Object modificar(K entidad) throws Exception {
		try{
			em.merge(entidad);
		} catch (PersistenceException p){
			throw new Exception("Error modificando la entidad " + entidad.toString());
		} catch (Exception e){
			throw new Exception("Error modificando la entidad " + entidad.toString());
		}
		
		return entidad;
	}
	
	public <K> void borrar(K entidad) throws Exception {
		try{
			em.remove(em.merge(entidad));
		} catch (PersistenceException p){
			throw new Exception("Error borrando la entidad " + entidad.toString());
		} catch (Exception e){
			throw new Exception("Error borrando la entidad " + entidad.toString());
		}
	}
	
	public <K> Object copiar(K entidad) throws Exception {
		try{
			em.detach(entidad);
		} catch (Exception e) {
			throw new Exception("Error copiando la entidad " + entidad.toString());
		}
		
		return entidad;
	}
}
