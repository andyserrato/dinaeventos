package org.dinamizadores.dinaeventos.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Usuario;

public class EntradasCompleta {
	
	private Long idEntrada;
	
	private String numeroserie;
	
	private Date fechaVendida;
	
	private Long idTipoEntrada;
	
	private List<BigDecimal> cantidadEntradas;
	
	private String nombre;
	
	private String descripcion;
	
	private BigDecimal precio;
	
	private Usuario usuario;
	
	private List<ComplementoEntero> listaComplementos;
	
	
	public EntradasCompleta(){
		idTipoEntrada = null;
		cantidadEntradas = new ArrayList<BigDecimal>() ;
		precio = new BigDecimal(0);
		nombre = null;
		usuario = new Usuario();
		listaComplementos = new ArrayList<ComplementoEntero>();
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

	public List<ComplementoEntero> getListaComplementos() {
		return listaComplementos;
	}

	public void setListaComplementos(List<ComplementoEntero> listaComplementos) {
		this.listaComplementos = listaComplementos;
	}

	public Long getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(Long idEntrada) {
		this.idEntrada = idEntrada;
	}

	public Date getFechaVendida() {
		return fechaVendida;
	}

	public void setFechaVendida(Date fechaVendida) {
		this.fechaVendida = fechaVendida;
	}

	public String getNumeroserie() {
		return numeroserie;
	}

	public void setNumeroserie(String numeroserie) {
		this.numeroserie = numeroserie;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
