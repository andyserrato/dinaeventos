package org.dinamizadores.dinaeventos.dto;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import org.dinamizadores.dinaeventos.model.DdTipoComplemento;


public class ComplementoEntero {

	
	private DdTipoComplemento complemento;
	private int cantidad;

	public ComplementoEntero() {
		cantidad = 0;
		complemento = new DdTipoComplemento();
	}

	
	public int getCantidad() {
		return cantidad;
	}



	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public DdTipoComplemento getComplemento() {
		return complemento;
	}



	public void setComplemento(DdTipoComplemento complemento) {
		this.complemento = complemento;
	}

}
