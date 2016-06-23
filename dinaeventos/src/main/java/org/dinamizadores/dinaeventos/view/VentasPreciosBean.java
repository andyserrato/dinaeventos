package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.utiles.Constantes;

/**
 * @author Raúl "El niño maravilla" del Río
 *
 */
@Named("ventasPrecios")
@ViewScoped
public class VentasPreciosBean implements Serializable {

	/** LoginBean inyectado para poder recoger datos globales de la aplicación. */
	@Inject
	private LoginBean loginBean;
	
	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {}
	
	/**
	 * @param loginBean the loginBean to set
	 */
	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
}
