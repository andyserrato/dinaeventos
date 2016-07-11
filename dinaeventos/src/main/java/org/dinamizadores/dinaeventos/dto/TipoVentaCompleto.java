/**
 * 
 */
package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Raúl "El niño maravilla" del Río
 */
public class TipoVentaCompleto implements Serializable {
	
	/** UID. */
	private static final long serialVersionUID = 5395583418510865646L;

	/** Nombre del tipo de entrada a almacenar. */
	public String nombreTipoEntrada;
	
	/** Total de dinero ingresado con las ventas por este tipo de entrada. */
	public float total;
	
	/** Lista con las ventas para este tipo de entrada agrupadas por precio. */
	public ArrayList<TipoVentaBasico> ventas;
	
	/**
	 * Constructor por defecto
	 */
	public TipoVentaCompleto() {}

	/**
	 * @return the nombreTipoEntrada
	 */
	public String getNombreTipoEntrada() {
		return nombreTipoEntrada;
	}

	/**
	 * @param nombreTipoEntrada the nombreTipoEntrada to set
	 */
	public void setNombreTipoEntrada(String nombreTipoEntrada) {
		this.nombreTipoEntrada = nombreTipoEntrada;
	}

	/**
	 * @return the total
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(float total) {
		this.total = total;
	}

	/**
	 * @return the ventas
	 */
	public ArrayList<TipoVentaBasico> getVentas() {
		return ventas;
	}

	/**
	 * @param ventas the ventas to set
	 */
	public void setVentas(ArrayList<TipoVentaBasico> ventas) {
		this.ventas = ventas;
	}
}
