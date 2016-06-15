package org.dinamizadores.dinaevents.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class entradasCompleta {
	
	private Long idTipoEntrada;
	
	private List<BigDecimal> cantidadEntradas;
	
	private String nombre;
	
	private BigDecimal precio;
	
	public entradasCompleta(){
		idTipoEntrada = null;
		cantidadEntradas = new ArrayList<BigDecimal>() ;
		precio = new BigDecimal(0);
		nombre = null;
	}
	

	public Long getIdTipoEntrada() {
		return idTipoEntrada;
	}

	public void setIdTipoEntrada(Long idTipoEntrada) {
		this.idTipoEntrada = idTipoEntrada;
	}

	public List<BigDecimal> getCantidadEntradas() {
		return cantidadEntradas;
	}

	public void setCantidadEntradas(List<BigDecimal> cantidadEntradas) {
		this.cantidadEntradas = cantidadEntradas;
	}


	public BigDecimal getPrecio() {
		return precio;
	}


	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
