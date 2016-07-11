/**
 * 
 */
package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Raúl "El niño maravilla" del Río
 */
public class EntradasTiempoDTO implements Serializable{

	/** UID. */
	private static final long serialVersionUID = -2278199670550466066L;

	/** Cantidad de entradas vendidas en una fecha concreta. */
	private int cantidad;
	
	/** Fecha en que se han vendido las entradas. */
	private Date fecha;
	
	/** Cantidad de dinero ganada en un día con la venta de entradas. */
	private float precio;
	
	/**
	 * Constructor por defecto
	 */
	public EntradasTiempoDTO(){}
	
	/**
	 * Constructor con parámetros.
	 * @param cantidad
	 * @param fecha
	 */
	public EntradasTiempoDTO(BigInteger cantidad, Date fecha){
		this.cantidad = cantidad.intValueExact();
		this.fecha = fecha;
	}
	
	/**
	 * Constructor con parámetros.
	 * @param precio
	 * @param fecha
	 */
	public EntradasTiempoDTO(BigDecimal precio, Date fecha){
		this.precio = precio.floatValue();
		this.fecha = fecha;
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the precio
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(float precio) {
		this.precio = precio;
	}
}
