package org.dinamizadores.dinaeventos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
public class EventoDTO implements Serializable{
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	
	private int entradasVendidas;
	private int entradasTotales;
	private float ingresosTotales;
	private int entradasValidadasPapel;
	private int entradasOnline;
	private int entradasSMS;
	private int numHombres;
	private int numMujeres;
	
	/**
	 * Constructor por defecto.
	 */
	public EventoDTO(){}
	
	public EventoDTO(int entradasTotales, BigInteger entradasVendidas, BigInteger entradasValidadasPapel, BigInteger entradasOnline, BigDecimal ingresosTotales, BigInteger numHombres, BigInteger numMujeres){
		this.entradasTotales = entradasTotales;
		this.entradasVendidas = entradasVendidas.intValueExact();
		this.entradasValidadasPapel = entradasValidadasPapel.intValueExact();
		this.entradasOnline = entradasOnline.intValueExact();
		this.ingresosTotales = ingresosTotales.floatValue();
		this.numHombres = numHombres.intValueExact();
		this.numMujeres = numMujeres.intValueExact();
	}

	/**
	 * @return the entradasVendidas
	 */
	public int getEntradasVendidas() {
		return entradasVendidas;
	}

	/**
	 * @param entradasVendidas the entradasVendidas to set
	 */
	public void setEntradasVendidas(int entradasVendidas) {
		this.entradasVendidas = entradasVendidas;
	}

	/**
	 * @return the entradasTotales
	 */
	public int getEntradasTotales() {
		return entradasTotales;
	}

	/**
	 * @param entradasTotales the entradasTotales to set
	 */
	public void setEntradasTotales(int entradasTotales) {
		this.entradasTotales = entradasTotales;
	}

	/**
	 * @return the ingresosTotales
	 */
	public float getIngresosTotales() {
		return ingresosTotales;
	}

	/**
	 * @param ingresosTotales the ingresosTotales to set
	 */
	public void setIngresosTotales(float ingresosTotales) {
		this.ingresosTotales = ingresosTotales;
	}

	/**
	 * @return the entradasValidadasPapel
	 */
	public int getEntradasValidadasPapel() {
		return entradasValidadasPapel;
	}

	/**
	 * @param entradasValidadasPapel the entradasValidadasPapel to set
	 */
	public void setEntradasValidadasPapel(int entradasValidadasPapel) {
		this.entradasValidadasPapel = entradasValidadasPapel;
	}

	/**
	 * @return the entradasOnline
	 */
	public int getEntradasOnline() {
		return entradasOnline;
	}

	/**
	 * @param entradasOnline the entradasOnline to set
	 */
	public void setEntradasOnline(int entradasOnline) {
		this.entradasOnline = entradasOnline;
	}

	/**
	 * @return the entradasSMS
	 */
	public int getEntradasSMS() {
		return entradasSMS;
	}

	/**
	 * @param entradasSMS the entradasSMS to set
	 */
	public void setEntradasSMS(int entradasSMS) {
		this.entradasSMS = entradasSMS;
	}

	/**
	 * @return the numHombres
	 */
	public int getNumHombres() {
		return numHombres;
	}

	/**
	 * @param numHombres the numHombres to set
	 */
	public void setNumHombres(int numHombres) {
		this.numHombres = numHombres;
	}

	/**
	 * @return the numMujeres
	 */
	public int getNumMujeres() {
		return numMujeres;
	}

	/**
	 * @param numMujeres the numMujeres to set
	 */
	public void setNumMujeres(int numMujeres) {
		this.numMujeres = numMujeres;
	}
}
