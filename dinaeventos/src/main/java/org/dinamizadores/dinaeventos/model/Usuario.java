package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", catalog = "jbossforge")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = -8141392932334002184L;
	private int idusuario;
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	private Date fechanac;
	private String direccion;
	private byte[] fotoperfil;
	private String fotoNombre;
	private String password;
	private String cuentacorriente;
	private String iban;
	private Boolean bloqueado;
	private Boolean activo;
	private Boolean cliente;
	private Date ultimologin;
	private Integer idsexo;
	private Integer idredessociales;
	private Integer idrol;
	private Roles roles;
	private Integer idcodigopostal;
	private GlobalCodigospostales codigoPostal;
	private String dni;

	public Usuario() {
		this.email = null;
	}

	public Usuario(int idusuario, String apellido1, String password) {
		this.idusuario = idusuario;
		this.apellidos = apellido1;
		this.password = password;
	}
	public Usuario(int idusuario, String nombre, String apellido,
			String apellido2, String email, String telefono, Date fechanac,
			String direccion, byte[] fotoperfil, String password,
			String cuentacorriente, String iban, Boolean bloqueado,
			Boolean activo, Date ultimologin, Integer idsexo,
			Integer idredessociales, Integer idrol, Integer idcodigopostal, String dni) {
		this.idusuario = idusuario;
		this.nombre = nombre;
		this.apellidos = apellido;
		this.email = email;
		this.telefono = telefono;
		this.fechanac = fechanac;
		this.direccion = direccion;
		this.fotoperfil = fotoperfil;
		this.password = password;
		this.cuentacorriente = cuentacorriente;
		this.iban = iban;
		this.bloqueado = bloqueado;
		this.activo = activo;
		this.ultimologin = ultimologin;
		this.idsexo = idsexo;
		this.idredessociales = idredessociales;
		this.idrol = idrol;
		this.idcodigopostal = idcodigopostal;
		this.dni = dni;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario", unique = true, nullable = false)
	public int getIdUsuario() {
		return this.idusuario;
	}

	public void setIdUsuario(int idusuario) {
		this.idusuario = idusuario;
	}

	@Column(name = "nombre", length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "apellido", length = 255)
	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefono", length = 9)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechanac")
	public Date getFechanac() {
		return this.fechanac;
	}

	public void setFechanac(Date fechanac) {
		this.fechanac = fechanac;
	}

	@Column(name = "direccion", length = 255)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Lob
	@Column(name = "fotoperfil")
	public byte[] getFotoperfil() {
		return this.fotoperfil;
	}

	public void setFotoperfil(byte[] fotoperfil) {
		this.fotoperfil = fotoperfil;
	}
	
	@Column(name = "fotonombre", length = 255)
	public String getFotoNombre() {
		return fotoNombre;
	}

	public void setFotoNombre(String fotonombre) {
		this.fotoNombre = fotonombre;
	}

	@Column(name = "password", length = 255)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Column(name = "bloqueado")
	public Boolean getBloqueado() {
		return this.bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Column(name = "activo")
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	@Column(name = "iscliente")
	public Boolean getCliente() {
		return cliente;
	}

	public void setCliente(Boolean cliente) {
		this.cliente = cliente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ultimologin")
	public Date getUltimologin() {
		return this.ultimologin;
	}

	public void setUltimologin(Date ultimologin) {
		this.ultimologin = ultimologin;
	}

	@Column(name = "idsexo")
	public Integer getIdsexo() {
		return this.idsexo;
	}

	public void setIdsexo(Integer idsexo) {
		this.idsexo = idsexo;
	}

	@Column(name = "idredessociales")
	public Integer getIdredessociales() {
		return this.idredessociales;
	}

	public void setIdredessociales(Integer idredessociales) {
		this.idredessociales = idredessociales;
	}

	@Column(name = "idrol")
	public Integer getIdrol() {
		return this.idrol;
	}

	public void setIdrol(Integer idrol) {
		this.idrol = idrol;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idrol", insertable = false, updatable = false)
    public Roles getRoles() {
        return this.roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

	@Column(name = "idcodigopostal")
	public Integer getIdcodigopostal() {
		return this.idcodigopostal;
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

	public void setIdcodigopostal(Integer idcodigopostal) {
		this.idcodigopostal = idcodigopostal;
	}

	@Column(name = "dni", length = 9)
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
}
