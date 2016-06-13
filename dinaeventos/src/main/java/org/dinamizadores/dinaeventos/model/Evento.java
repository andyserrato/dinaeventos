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

/**
 * Evento generated by hbm2java
 */
@Entity
@Table(name = "evento", catalog = "jbossforge")
public class Evento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1044399576691985644L;
	private int idevento;
	private String nombre;
	private String nombrelugar;
	private String direccion;
	private Date fechaIni;
	private Date fechaFin;
	private byte[] logo;
	private String descripcion;
	private int aforo;
	private Integer latitud;
	private Integer longitud;
	private Integer entradasDisponibles;
	private Integer idcodigopostal;
	private Integer idtipoevento;
	private Integer idorganizador;
	private Date fechaAlta;
	private Boolean acitvo;

	public Evento() {
	}

	public Evento(int idevento, String nombre, Date fechaIni, Date fechaFin,
			int aforo) {
		this.idevento = idevento;
		this.nombre = nombre;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.aforo = aforo;
	}
	public Evento(int idevento, String nombre, String nombrelugar,
			String direccion, Date fechaIni, Date fechaFin, byte[] logo,
			String descripcion, int aforo, Integer latitud, Integer longitud,
			Integer entradasDisponibles, Integer idcodigopostal,
			Integer idtipoevento, Integer idorganizador, Date fechaAlta,
			Boolean acitvo) {
		this.idevento = idevento;
		this.nombre = nombre;
		this.nombrelugar = nombrelugar;
		this.direccion = direccion;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
		this.logo = logo;
		this.descripcion = descripcion;
		this.aforo = aforo;
		this.latitud = latitud;
		this.longitud = longitud;
		this.entradasDisponibles = entradasDisponibles;
		this.idcodigopostal = idcodigopostal;
		this.idtipoevento = idtipoevento;
		this.idorganizador = idorganizador;
		this.fechaAlta = fechaAlta;
		this.acitvo = acitvo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idevento", unique = true, nullable = false)
	public int getIdevento() {
		return this.idevento;
	}

	public void setIdevento(int idevento) {
		this.idevento = idevento;
	}

	@Column(name = "nombre", nullable = false, length = 75)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "nombrelugar", length = 60)
	public String getNombrelugar() {
		return this.nombrelugar;
	}

	public void setNombrelugar(String nombrelugar) {
		this.nombrelugar = nombrelugar;
	}

	@Column(name = "direccion", length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_ini", nullable = false, length = 19)
	public Date getFechaIni() {
		return this.fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin", nullable = false, length = 19)
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "logo")
	public byte[] getLogo() {
		return this.logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	@Column(name = "descripcion", length = 140)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "aforo", nullable = false)
	public int getAforo() {
		return this.aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	@Column(name = "latitud")
	public Integer getLatitud() {
		return this.latitud;
	}

	public void setLatitud(Integer latitud) {
		this.latitud = latitud;
	}

	@Column(name = "longitud")
	public Integer getLongitud() {
		return this.longitud;
	}

	public void setLongitud(Integer longitud) {
		this.longitud = longitud;
	}

	@Column(name = "entradas_disponibles")
	public Integer getEntradasDisponibles() {
		return this.entradasDisponibles;
	}

	public void setEntradasDisponibles(Integer entradasDisponibles) {
		this.entradasDisponibles = entradasDisponibles;
	}

	@Column(name = "idcodigopostal")
	public Integer getIdcodigopostal() {
		return this.idcodigopostal;
	}

	public void setIdcodigopostal(Integer idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}

	@Column(name = "idtipoevento")
	public Integer getIdtipoevento() {
		return this.idtipoevento;
	}

	public void setIdtipoevento(Integer idtipoevento) {
		this.idtipoevento = idtipoevento;
	}

	@Column(name = "idorganizador")
	public Integer getIdorganizador() {
		return this.idorganizador;
	}

	public void setIdorganizador(Integer idorganizador) {
		this.idorganizador = idorganizador;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_alta", length = 19)
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Column(name = "acitvo")
	public Boolean getAcitvo() {
		return this.acitvo;
	}

	public void setAcitvo(Boolean acitvo) {
		this.acitvo = acitvo;
	}

}
