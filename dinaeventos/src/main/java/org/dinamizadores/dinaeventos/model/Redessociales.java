package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Redessociales generated by hbm2java
 */
@Entity
@Table(name = "redessociales", catalog = "jbossforge")
public class Redessociales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8114825636505298077L;
	private int idredessociales;
	private String enlace;

	public Redessociales() {
	}

	public Redessociales(int idredessociales) {
		this.idredessociales = idredessociales;
	}
	public Redessociales(int idredessociales, String enlace) {
		this.idredessociales = idredessociales;
		this.enlace = enlace;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idredessociales", unique = true, nullable = false)
	public int getIdredessociales() {
		return this.idredessociales;
	}

	public void setIdredessociales(int idredessociales) {
		this.idredessociales = idredessociales;
	}

	@Column(name = "enlace", length = 45)
	public String getEnlace() {
		return this.enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

}