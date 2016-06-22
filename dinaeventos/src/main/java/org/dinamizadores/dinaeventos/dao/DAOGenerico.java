package org.dinamizadores.dinaeventos.dao;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

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
	
	/** Inicializador del DAO. */
	@PostConstruct
	public void init(){
		return;
	}
	
	/**
	 * Realiza una consulta para un ID y entidad JPA determinados.
	 * 
	 * @param clase Clase de la entidad que se quiere consultar.
	 * @param <K> Tipo de la clase.
	 * @param id Identificador de la entidad a consultar.
	 * @return Objeto consultado.
	 * @throws FatalException Lanza excepción en caso de error.
	 */
	public <K> Object consultar(Class<K> clase, int id) throws Exception {
		Object entidad = null;
		
		try{
			entidad = em.find(clase, id);
		} catch (Exception e){
			throw new Exception("Error consultando la entidad con ID=" + id + " de la clase " + clase.getName());
		}
		
		return entidad;
	}
	
	/**
	 * Inserta una entidad JPA en la BBDD.
	 * 
	 * @param <K> Tipo de dato de la entidad.
	 * @param entidad Objeto a insertar.
	 * @return Objeto insertado.
	 * @throws FatalException Lanza excepción en caso de error.
	 */
	public <K> Object insertar(K entidad) throws Exception {
		try{
			em.persist(entidad);
			em.flush();
		} catch (PersistenceException p){
			p.printStackTrace();
			throw new Exception("Error insertando la entidad " + entidad.toString());
		} catch (NullPointerException n){
			n.printStackTrace();
			throw new Exception("Error insertando la entidad " + entidad.toString());
		}
		
		return entidad;
	}
	

	/**
	 * Modifica una entidad existente en la BBDD.
	 * 
	 * @param <K> Tipo de dato de la entidad.
	 * @param entidad Objeto a modificar.
	 * @return Objeto modificado.
	 * @throws FatalException Lanza excepción en caso de error.
	 */
	public <K> Object modificar(K entidad) throws Exception {
		try{
			em.merge(entidad);
			em.flush();
		} catch (PersistenceException p){
			throw new Exception("Error modificando la entidad " + entidad.toString());
		} catch (Exception e){
			throw new Exception("Error modificando la entidad " + entidad.toString());
		}
		
		return entidad;
	}
	
	/**
	 * Elimina una entidad JPA de la BBDD.
	 * 
	 * @param <K> Tipo de dato de la entidad.
	 * @param entidad Objeto a eliminar.
	 * @throws FatalException Lanza excepción en caso de error.
	 */
	public <K> void borrar(K entidad) throws Exception {
		try{
			em.remove(em.merge(entidad));
			em.flush();
		} catch (PersistenceException p){
			throw new Exception("Error borrando la entidad " + entidad.toString());
		} catch (Exception e){
			throw new Exception("Error borrando la entidad " + entidad.toString());
		}
		
		return;
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
