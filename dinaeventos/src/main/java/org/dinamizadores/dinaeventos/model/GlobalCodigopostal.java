package org.dinamizadores.dinaeventos.model;
// Generated 07-jun-2016 22:29:03 by Hibernate Tools 4.3.1.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * GlobalCodigopostal generated by hbm2java
 */
@Entity
@Table(name = "global_codigopostal", catalog = "jbossforge")
@XmlRootElement
public class GlobalCodigopostal implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -197070583117547021L;
	private int idcodigopostal;
	private GlobalProvincia globalProvincia;
	private String codigo;
	private String localidad;
	private Set<Patrocinador> patrocinadors = new HashSet<Patrocinador>(0);
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	private Set<Evento> eventos = new HashSet<Evento>(0);
	private Set<Organizador> organizadors = new HashSet<Organizador>(0);

	public GlobalCodigopostal() {
	}

	public GlobalCodigopostal(int idcodigopostal,
			GlobalProvincia globalProvincia) {
		this.idcodigopostal = idcodigopostal;
		this.globalProvincia = globalProvincia;
	}
	public GlobalCodigopostal(int idcodigopostal,
			GlobalProvincia globalProvincia, String codigo, String localidad,
			Set<Patrocinador> patrocinadors, Set<Usuario> usuarios,
			Set<Evento> eventos, Set<Organizador> organizadors) {
		this.idcodigopostal = idcodigopostal;
		this.globalProvincia = globalProvincia;
		this.codigo = codigo;
		this.localidad = localidad;
		this.patrocinadors = patrocinadors;
		this.usuarios = usuarios;
		this.eventos = eventos;
		this.organizadors = organizadors;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idcodigopostal", unique = true, nullable = false)
	public int getIdcodigopostal() {
		return this.idcodigopostal;
	}

	public void setIdcodigopostal(int idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idprovincia", nullable = false)
	public GlobalProvincia getGlobalProvincia() {
		return this.globalProvincia;
	}

	public void setGlobalProvincia(GlobalProvincia globalProvincia) {
		this.globalProvincia = globalProvincia;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "globalCodigopostal")
	public Set<Patrocinador> getPatrocinadors() {
		return this.patrocinadors;
	}

	public void setPatrocinadors(Set<Patrocinador> patrocinadors) {
		this.patrocinadors = patrocinadors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "globalCodigopostal")
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "globalCodigopostal")
	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "globalCodigopostal")
	public Set<Organizador> getOrganizadors() {
		return this.organizadors;
	}

	public void setOrganizadors(Set<Organizador> organizadors) {
		this.organizadors = organizadors;
	}

}
