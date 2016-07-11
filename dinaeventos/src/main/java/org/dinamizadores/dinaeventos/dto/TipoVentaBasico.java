/**
 * 
 */
package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Raúl "El niño maravilla" del Río
 */
public class TipoVentaBasico implements Serializable {

	/** UID. */
	private static final long serialVersionUID = 804915167876031353L;

	/** Precio de la entrada. */
	private float precio;
	
	/** Cantidad de entradas vendidas con este precio. */
	private int cantidad;
	
	/**
	 * Constructor por defecto.
	 */
	public TipoVentaBasico(){}
	
	/**
	 * Constructor con parámetros.
	 * @param precio EL precio.
	 * @param cantidad La cantidad.
	 */
	public TipoVentaBasico(BigDecimal precio, BigInteger cantidad){
		this.precio = precio.floatValue();
		this.cantidad = cantidad.intValueExact();
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
}
