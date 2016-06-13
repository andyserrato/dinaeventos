package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GlobalCodigopostales generated by hbm2java
 */
@Entity
@Table(name = "global_codigopostales", catalog = "jbossforge")
public class GlobalCodigopostales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8130280182270496969L;
	private int idcodigopostal;
	private String codigo;
	private String localidad;
	private Integer idprovincia;

	public GlobalCodigopostales() {
	}

	public GlobalCodigopostales(int idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}
	public GlobalCodigopostales(int idcodigopostal, String codigo,
			String localidad, Integer idprovincia) {
		this.idcodigopostal = idcodigopostal;
		this.codigo = codigo;
		this.localidad = localidad;
		this.idprovincia = idprovincia;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idcodigopostal", unique = true, nullable = false)
	public int getIdcodigopostal() {
		return this.idcodigopostal;
	}

	public void setIdcodigopostal(int idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}

	@Column(name = "codigo", length = 5)
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Column(name = "localidad", length = 45)
	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Column(name = "idprovincia")
	public Integer getIdprovincia() {
		return this.idprovincia;
	}

	public void setIdprovincia(Integer idprovincia) {
		this.idprovincia = idprovincia;
	}

}