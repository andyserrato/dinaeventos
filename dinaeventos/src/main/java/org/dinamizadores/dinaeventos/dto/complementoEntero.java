package org.dinamizadores.dinaeventos.dto;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.dinamizadores.dinaeventos.model.DdTipoComplemento;


public class complementoEntero {

	
	private DdTipoComplemento complemento;
	private int cantidad;

	public complementoEntero() {
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
