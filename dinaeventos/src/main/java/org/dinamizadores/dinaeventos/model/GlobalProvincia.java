package org.dinamizadores.dinaeventos.model;
// Generated 06-jun-2016 19:44:44 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * GlobalProvincia generated by hbm2java
 */
@Entity
@Table(name = "global_provincia", catalog = "jbossforge")
@XmlRootElement
public class GlobalProvincia implements java.io.Serializable {

	private static final long serialVersionUID = 7083099005459280980L;
	private int idprovincia;
	private String nombre;
	private Set<GlobalCodigopostal> globalCodigopostals = new HashSet<GlobalCodigopostal>(
			0);

	public GlobalProvincia() {
	}

	public GlobalProvincia(int idprovincia) {
		this.idprovincia = idprovincia;
	}
	public GlobalProvincia(int idprovincia, String nombre,
			Set<GlobalCodigopostal> globalCodigopostals) {
		this.idprovincia = idprovincia;
		this.nombre = nombre;
		this.globalCodigopostals = globalCodigopostals;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "globalProvincia")
	public Set<GlobalCodigopostal> getGlobalCodigopostals() {
		return this.globalCodigopostals;
	}

	public void setGlobalCodigopostals(
			Set<GlobalCodigopostal> globalCodigopostals) {
		this.globalCodigopostals = globalCodigopostals;
	}

}
