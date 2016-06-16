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
@ManagedBean(name="lugaresOrigen")
@ViewScoped
public class LugaresOrigenBean implements Serializable {

	/**
	 * Constructor por defecto
	 */
	@PostConstruct
	public void init() {}
}
