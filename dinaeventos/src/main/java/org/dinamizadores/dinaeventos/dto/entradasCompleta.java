package org.dinamizadores.dinaeventos.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Usuario;

public class entradasCompleta {
	
	private Long idEntrada;
	
	private Long idTipoEntrada;
	
	private List<BigDecimal> cantidadEntradas;
	
	private String nombre;
	
	private BigDecimal precio;
	
	private Usuario usuario;
	
	private List<complementoEntero> listaComplementos;
	
	
	public entradasCompleta(){
		idTipoEntrada = null;
		cantidadEntradas = new ArrayList<BigDecimal>() ;
		precio = new BigDecimal(0);
		nombre = null;
		usuario = new Usuario();
		listaComplementos = new ArrayList<complementoEntero>();
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<complementoEntero> getListaComplementos() {
		return listaComplementos;
	}

	public void setListaComplementos(List<complementoEntero> listaComplementos) {
		this.listaComplementos = listaComplementos;
	}

	public Long getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
	}

}
