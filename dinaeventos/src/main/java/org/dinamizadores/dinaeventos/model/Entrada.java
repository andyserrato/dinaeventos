package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Entrada generated by hbm2java
 */
@Entity
@Table(name = "entrada", catalog = "jbossforge")
@javax.persistence.SequenceGenerator(
	    name="SEQ_ENTRADA",
	    sequenceName="entrada_sequence"
	)
public class Entrada implements java.io.Serializable {

	private static final long serialVersionUID = 5065833516933374107L;
	private int identrada;
	private String numeroserie;
	private Boolean validada;
	private Boolean ticketgenerado;
	private Float precio;
	private Boolean activa;
	private Integer idevento;
	private Integer idusuario; 
	private Integer idtipoiva;
	private Integer idformapago;
	private Integer idtipoentrada;
	private Integer idorigen;
	private Boolean dentrofuera;
	private Boolean vendida;
	private Date fechaValidada;
	private Date fechaVendida;

	public Entrada() {
	}

	public Entrada(int identrada) {
		this.identrada = identrada;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ENTRADA")
	@Column(name = "identrada", unique = true, nullable = false)
	public int getIdentrada() {
		return this.identrada;
	}

	public void setIdentrada(int identrada) {
		this.identrada = identrada;
	}

	@Column(name = "numeroserie", unique = true, length = 100)
	public String getNumeroserie() {
		return this.numeroserie;
	}

	public void setNumeroserie(String numeroserie) {
		this.numeroserie = numeroserie;
	}

	@Column(name = "validada")
	public Boolean getValidada() {
		return this.validada;
	}

	public void setValidada(Boolean validada) {
		this.validada = validada;
	}

	@Column(name = "ticketgenerado")
	public Boolean getTicketgenerado() {
		return this.ticketgenerado;
	}

	public void setTicketgenerado(Boolean ticketgenerado) {
		this.ticketgenerado = ticketgenerado;
	}

	@Column(name = "precio", precision = 12, scale = 0)
	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	@Column(name = "activa")
	public Boolean getActiva() {
		return this.activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	@Column(name = "idevento")
	public Integer getIdevento() {
		return this.idevento;
	}

	public void setIdevento(Integer idevento) {
		this.idevento = idevento;
	}

	@Column(name = "idusuario", nullable = true)
	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	@Column(name = "idtipoiva", nullable = true)
	public Integer getIdtipoiva() {
		return this.idtipoiva;
	}

	public void setIdtipoiva(Integer idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	@Column(name = "idformapago", nullable = true)
	public Integer getIdformapago() {
		return this.idformapago;
	}

	public void setIdformapago(Integer idformapago) {
		this.idformapago = idformapago;
	}

	@Column(name = "idtipoentrada", nullable = true)
	public Integer getIdtipoentrada() {
		return this.idtipoentrada;
	}

	public void setIdtipoentrada(Integer idtipoentrada) {
		this.idtipoentrada = idtipoentrada;
	}

	@Column(name = "idorigen", nullable = true)
	public Integer getIdorigen() {
		return this.idorigen;
	}

	public void setIdorigen(Integer idorigen) {
		this.idorigen = idorigen;
	}

	@Column(name = "dentrofuera")
	public Boolean getDentrofuera() {
		return this.dentrofuera;
	}

	public void setDentrofuera(Boolean dentrofuera) {
		this.dentrofuera = dentrofuera;
	}

	@Column(name = "vendida")
	public Boolean getVendida() {
		return this.vendida;
	}

	public void setVendida(Boolean vendida) {
		this.vendida = vendida;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechavalidada")
	public Date getFechaValidada() {
		return fechaValidada;
	}

	public void setFechaValidada(Date fechaValidada) {
		this.fechaValidada = fechaValidada;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechavendida")
	public Date getFechaVendida() {
		return fechaVendida;
	}

	public void setFechaVendida(Date fechaVendida) {
		this.fechaVendida = fechaVendida;
	}

}
