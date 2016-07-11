/**
 * 
 */
package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
public class DdGenerico implements Serializable {

	/** UID. */
	private static final long serialVersionUID = 1L;

	/** Identificador del DdGenerico. */
	private int id;
	
	/** Descripción del DdGenerico. */
	private String descripcion;
	
	/** Cantidad del DdGenerico. */
	private int cantidad;
	
	/** Precio del DdGenerico. */
	private Float precio;
	
	/**
	 * Constructor por defecto
	 */
	public DdGenerico(){}
	
	public DdGenerico(BigInteger cantidad, String descripcion, int id){
		this.cantidad = cantidad.intValueExact();
		this.descripcion = descripcion;
		this.id = id;
	}
	
	public DdGenerico(BigInteger cantidad, String descripcion){
		this.cantidad = cantidad.intValueExact();
		this.descripcion = descripcion;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the precio
	 */
	public Float getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
}
