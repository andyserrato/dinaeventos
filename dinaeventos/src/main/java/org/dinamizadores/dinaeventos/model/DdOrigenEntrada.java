package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DdOrigenEntrada generated by hbm2java
 */
@Entity
@Table(name = "dd_origen_entrada", catalog = "jbossforge")
public class DdOrigenEntrada implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3632791777583537881L;
	private int idorigenEntrada;
	private String nombre;

	public DdOrigenEntrada() {
	}

	public DdOrigenEntrada(int idorigenEntrada, String nombre) {
		this.idorigenEntrada = idorigenEntrada;
		this.nombre = nombre;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idorigen_entrada", unique = true, nullable = false)
	public int getIdorigenEntrada() {
		return this.idorigenEntrada;
	}

	public void setIdorigenEntrada(int idorigenEntrada) {
		this.idorigenEntrada = idorigenEntrada;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
