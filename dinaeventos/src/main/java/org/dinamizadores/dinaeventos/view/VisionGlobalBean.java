package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.DdFormapago;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Named("visionGlobal")
@ViewScoped
public class VisionGlobalBean implements Serializable{
	
	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;

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
	
	@EJB
	private DAOGenerico dao;
	private BBDDFaker bd;
	
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
		
//		dao = new DAOGenerico();
		bd = new BBDDFaker();
		
		DdFormapago p = bd.crearFormaPago();
		
		if(p == null){
			System.out.println("fuck");
		} else{
		
			try {
				dao.insertar(p);
			} catch (Exception e) {
				System.out.println("Pues YOLO bitches!");
				e.printStackTrace();
			}
		}
		
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
	
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
