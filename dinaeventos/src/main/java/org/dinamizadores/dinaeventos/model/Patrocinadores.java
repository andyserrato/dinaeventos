package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Patrocinadores generated by hbm2java
 */
@Entity
@Table(name = "patrocinadores", catalog = "jbossforge")
public class Patrocinadores implements java.io.Serializable {

	private static final long serialVersionUID = 5489548481090492846L;
	private int idpatrocinador;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private String cuentacorriente;
	private String iban;
	private byte[] fotoLogo;
	private String logoNombre;
	private Integer idcodigopostal;
	private GlobalCodigospostales codigoPostal;
	

	public Patrocinadores() {
	}

	public Patrocinadores(int idpatrocinador) {
		this.idpatrocinador = idpatrocinador;
	}
	public Patrocinadores(int idpatrocinador, String nombre, String direccion,
			String telefono, String email, String cuentacorriente, String iban,
			Integer idcodigopostal) {
		this.idpatrocinador = idpatrocinador;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.cuentacorriente = cuentacorriente;
		this.iban = iban;
		this.idcodigopostal = idcodigopostal;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idpatrocinador", unique = true, nullable = false)
	public int getIdpatrocinador() {
		return this.idpatrocinador;
	}

	public void setIdpatrocinador(int idpatrocinador) {
		this.idpatrocinador = idpatrocinador;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "direccion", length = 255)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "telefono", length = 9)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "cuentacorriente", length = 20)
	public String getCuentacorriente() {
		return this.cuentacorriente;
	}

	public void setCuentacorriente(String cuentacorriente) {
		this.cuentacorriente = cuentacorriente;
	}

	@Column(name = "iban", length = 34)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	
	@Lob
	@Column(name = "fotologo")
	public byte[] getFotoperfil() {
		return this.fotoLogo;
	}

	public void setFotoperfil(byte[] fotoLogo) {
		this.fotoLogo = fotoLogo;
	}
	
	@Column(name = "logonombre", length = 255)
	public String getLogoNombre() {
		return logoNombre;
	}

	public void setLogoNombre(String logoNombre) {
		this.logoNombre = logoNombre;
	}

	@Column(name = "idcodigopostal")
	public Integer getIdcodigopostal() {
		return this.idcodigopostal;
	}

	public void setIdcodigopostal(Integer idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "idcodigopostal", insertable = false, updatable = false)
	@Transient
	public GlobalCodigospostales getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(GlobalCodigospostales codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

}
