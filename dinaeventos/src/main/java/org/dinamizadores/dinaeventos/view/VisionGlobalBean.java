package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.dinamizadores.dinaeventos.utiles.Constantes;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@ManagedBean(name="visionGlobal")
@ViewScoped
public class VisionGlobalBean implements Serializable{

	/** Atributo temporal para mostrar el número de entradas vendidas. */
	private int entradasVendidas;
	
	/** Atributo temporal. */
	private int entradasTotales;
	
	/** Atributo temporal. */
	private int entradasPapel;
	
	/** Atributo temporal. */
	private int entradasOnline;
	
	/** Atributo temporal. */
	private int entradasSMS;
	
	/** Atributo temporal. */
	private int cambiosNombre;
	
	/** Atributo temporal. */
	private float ingresosTotales;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		entradasVendidas = 25366;
		entradasTotales = 30000;
		entradasPapel = 13961;
		entradasOnline = 4417;
		entradasSMS = 6988;
		cambiosNombre = 529;
		ingresosTotales = (float) 304333.15;
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
	 * @return the entradasPapel
	 */
	public int getEntradasPapel() {
		return entradasPapel;
	}

	/**
	 * @param entradasPapel the entradasPapel to set
	 */
	public void setEntradasPapel(int entradasPapel) {
		this.entradasPapel = entradasPapel;
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
	 * @return the cambiosNombre
	 */
	public int getCambiosNombre() {
		return cambiosNombre;
	}

	/**
	 * @param cambiosNombre the cambiosNombre to set
	 */
	public void setCambiosNombre(int cambiosNombre) {
		this.cambiosNombre = cambiosNombre;
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
}
