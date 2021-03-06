package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * GlobalProvincias generated by hbm2java
 */
@Entity
@Table(name = "global_provincias", catalog = "jbossforge")
public class GlobalProvincias implements java.io.Serializable {

	private static final long serialVersionUID = -217799023124393281L;
	private int idprovincia;
	private String nombre;

	public GlobalProvincias() {
	}

	public GlobalProvincias(int idprovincia) {
		this.idprovincia = idprovincia;
	}
	public GlobalProvincias(int idprovincia, String nombre) {
		this.idprovincia = idprovincia;
		this.nombre = nombre;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idprovincia", unique = true, nullable = false)
	public int getIdprovincia() {
		return this.idprovincia;
	}

	public void setIdprovincia(int idprovincia) {
		this.idprovincia = idprovincia;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
