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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * RrppJefe generated by hbm2java
 */
@Entity
@Table(name = "rrpp_jefe", catalog = "jbossforge")
@XmlRootElement
public class RrppJefe implements java.io.Serializable {

	private static final long serialVersionUID = 8670004750613899350L;
	private int idrrppJefe;
	private Organizador organizador;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String telefono;
	private Integer limiteEntradas;
	private String codigoPromocional;
	private Set<RrppMinion> rrppMinions = new HashSet<RrppMinion>(0);
	private Set<RrppJefeHasEntrada> rrppJefeHasEntradas = new HashSet<RrppJefeHasEntrada>(
			0);
	private Set<Evento> eventos = new HashSet<Evento>(0);

	public RrppJefe() {
	}

	public RrppJefe(int idrrppJefe, Organizador organizador) {
		this.idrrppJefe = idrrppJefe;
		this.organizador = organizador;
	}
	public RrppJefe(int idrrppJefe, Organizador organizador, String nombre,
			String apellido1, String apellido2, String email, String telefono,
			Integer limiteEntradas, String codigoPromocional,
			Set<RrppMinion> rrppMinions,
			Set<RrppJefeHasEntrada> rrppJefeHasEntradas, Set<Evento> eventos) {
		this.idrrppJefe = idrrppJefe;
		this.organizador = organizador;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.telefono = telefono;
		this.limiteEntradas = limiteEntradas;
		this.codigoPromocional = codigoPromocional;
		this.rrppMinions = rrppMinions;
		this.rrppJefeHasEntradas = rrppJefeHasEntradas;
		this.eventos = eventos;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idrrpp_jefe", unique = true, nullable = false)
	public int getIdrrppJefe() {
		return this.idrrppJefe;
	}

	public void setIdrrppJefe(int idrrppJefe) {
		this.idrrppJefe = idrrppJefe;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idorganizador", nullable = false)
	public Organizador getOrganizador() {
		return this.organizador;
	}

	public void setOrganizador(Organizador organizador) {
		this.organizador = organizador;
	}

	@Column(name = "nombre", length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido1", length = 45)
	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "apellido2", length = 45)
	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefono", length = 45)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "limite_entradas")
	public Integer getLimiteEntradas() {
		return this.limiteEntradas;
	}

	public void setLimiteEntradas(Integer limiteEntradas) {
		this.limiteEntradas = limiteEntradas;
	}

	@Column(name = "codigo_promocional", length = 45)
	public String getCodigoPromocional() {
		return this.codigoPromocional;
	}

	public void setCodigoPromocional(String codigoPromocional) {
		this.codigoPromocional = codigoPromocional;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rrppJefe")
	public Set<RrppMinion> getRrppMinions() {
		return this.rrppMinions;
	}

	public void setRrppMinions(Set<RrppMinion> rrppMinions) {
		this.rrppMinions = rrppMinions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rrppJefe")
	public Set<RrppJefeHasEntrada> getRrppJefeHasEntradas() {
		return this.rrppJefeHasEntradas;
	}

	public void setRrppJefeHasEntradas(
			Set<RrppJefeHasEntrada> rrppJefeHasEntradas) {
		this.rrppJefeHasEntradas = rrppJefeHasEntradas;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "rrppJefes")
	public Set<Evento> getEventos() {
		return this.eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

}
