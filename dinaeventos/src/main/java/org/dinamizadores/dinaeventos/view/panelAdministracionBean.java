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
@ManagedBean(name="panelAdministracion")
@ViewScoped
public class panelAdministracionBean implements Serializable {
	
	/** UID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {}

	/**
	 * Método que redirige a la página de Visión Global.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVisionGlobal(){
		return Constantes.Rutas.Administracion.VISION_GLOBAL;
	}
	
	/**
	 * Método que redirige a la página de Lugares de Origen.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irALugaresDeOrigen(){
		return Constantes.Rutas.Administracion.LUGARES_ORIGEN;
	}
	
	/**
	 * Método que redirige a la página de Venta por precios.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVentaPorPrecios(){
		return Constantes.Rutas.Administracion.VENTAS_PRECIOS;
	}
	
	/**
	 * Método que redirige a la página de Ventas en papel.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVentasEnPapel(){
		return Constantes.Rutas.Administracion.VENTAS_PAPEL;
	}
	
	/**
	 * Método que redirige a la página de Ventas físicas.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVentasFisicas(){
		return Constantes.Rutas.Administracion.VENTAS_FISICAS;
	}
	
	/**
	 * Método que redirige a la página de Ventas online.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVentasOnline(){
		return Constantes.Rutas.Administracion.VENTAS_ONLINE;
	}

	/**
	 * Método que redirige a la página de Ventas por tipo de entradas.
	 * 
	 * @return La dirección de dicha página.
	 */
	public String irAVentasTipoEntradas(){
		return Constantes.Rutas.Administracion.VENTAS_TIPO_ENTRADAS;
	}
}
