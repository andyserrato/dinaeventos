package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.dto.DdGenerico;
import org.dinamizadores.dinaeventos.utiles.Constantes;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Named("lugaresOrigen")
@ViewScoped
public class LugaresOrigenBean implements Serializable {
	
	/** UID. */
	private static final long serialVersionUID = 676064455026372332L;

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;
	
	/** Lista ordenada por provincias de los lugares de origen de los compradores de entradas. */
	private ArrayList<DdGenerico> listaLugaresDeOrigen;
	
	/** Objeto EJB para comunicarse con la BBDD. */
	@EJB
	private EventoDao eventoDAO;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {
		float totalVendido = 0;
		
		//Posteriormente ese 1 'hardcodeado' se sacará del loginBean
		listaLugaresDeOrigen = eventoDAO.getLugaresDeOrigen(1);
		for(DdGenerico d: listaLugaresDeOrigen){
			totalVendido += (float) d.getCantidad();
		}
		for(DdGenerico d : listaLugaresDeOrigen){
			float f = ((float)d.getCantidad() / totalVendido) * 100;
			d.setPrecio(f);
		}
	}
	
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	/**
	 * @return the listaLugaresDeOrigen
	 */
	public ArrayList<DdGenerico> getListaLugaresDeOrigen() {
		return listaLugaresDeOrigen;
	}

	/**
	 * @param listaLugaresDeOrigen the listaLugaresDeOrigen to set
	 */
	public void setListaLugaresDeOrigen(ArrayList<DdGenerico> listaLugaresDeOrigen) {
		this.listaLugaresDeOrigen = listaLugaresDeOrigen;
	}
}
