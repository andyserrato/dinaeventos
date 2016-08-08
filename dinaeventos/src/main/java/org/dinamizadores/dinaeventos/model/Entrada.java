package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entrada generated by hbm2java
 */
@Entity
@Table(name = "entrada", catalog = "jbossforge")
public class Entrada implements java.io.Serializable {

	private static final long serialVersionUID = 5065833516933374107L;

	private int identrada;

	private String numeroserie;

	private Boolean validada;

	private Boolean ticketgenerado;

	private BigDecimal precio;

	private Boolean activa;

	private Integer idevento;

	private Evento evento;

	private Integer idusuario;

	private Usuario usuario;

	private Integer idtipoiva;

	private DdTiposIva ddTiposIva;

	private Integer idformapago;

	private DdFormapago ddFormapago;

	private Integer idtipoentrada;

	private DdTipoEntrada ddTipoEntrada;

	private Boolean dentrofuera;

	private Boolean vendida;

	private Date fechaValidada;

	private Date fechaVendida;

	private Integer nombreCambiadoTimes;

	private String idTransaccionPayPal;

	private Set<DdRrppJefeEntrada> ddRrppJefeEntradas = new HashSet<DdRrppJefeEntrada>(0);

	// TODO no se mapea con id, sólo con entidad, verificar
	private Set<EntradaComplemento> entradaComplementos = new HashSet<EntradaComplemento>(0);
	// TODO probar la relación many to many que no se hace con ID

	public Entrada() {}

	public Entrada(int identrada) {
		this.identrada = identrada;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identrada", unique = true, nullable = false)
	public int getIdentrada() {
		return this.identrada;
	}

	public void setIdentrada(int identrada) {
		this.identrada = identrada;
	}

	@Column(name = "numeroserie", unique = true, length = 100)
	public String getNumeroserie() {
		return this.numeroserie;
	}

	public void setNumeroserie(String numeroserie) {
		this.numeroserie = numeroserie;
	}

	@Column(name = "validada")
	public Boolean getValidada() {
		return this.validada;
	}

	public void setValidada(Boolean validada) {
		this.validada = validada;
	}

	@Column(name = "ticketgenerado")
	public Boolean getTicketgenerado() {
		return this.ticketgenerado;
	}

	public void setTicketgenerado(Boolean ticketgenerado) {
		this.ticketgenerado = ticketgenerado;
	}

	@Column(name = "precio", precision = 12, scale = 2)
	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	@Column(name = "activa")
	public Boolean getActiva() {
		return this.activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	@Column(name = "idevento")
	public Integer getIdevento() {
		return this.idevento;
	}

	public void setIdevento(Integer idevento) {
		this.idevento = idevento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idevento", insertable = false, updatable = false)
	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Column(name = "idusuario", nullable = true)
	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario", insertable = false, updatable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "idtipoiva", nullable = true)
	public Integer getIdtipoiva() {
		return this.idtipoiva;
	}

	public void setIdtipoiva(Integer idtipoiva) {
		this.idtipoiva = idtipoiva;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoiva", insertable = false, updatable = false)
	public DdTiposIva getDdTiposIva() {
		return this.ddTiposIva;
	}

	public void setDdTiposIva(DdTiposIva ddTiposIva) {
		this.ddTiposIva = ddTiposIva;
	}

	@Column(name = "idformapago", nullable = true)
	public Integer getIdformapago() {
		return this.idformapago;
	}

	public void setIdformapago(Integer idformapago) {
		this.idformapago = idformapago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idformapago", insertable = false, updatable = false)
	public DdFormapago getDdFormapago() {
		return this.ddFormapago;
	}

	public void setDdFormapago(DdFormapago ddFormapago) {
		this.ddFormapago = ddFormapago;
	}

	@Column(name = "idtipoentrada", nullable = true)
	public Integer getIdtipoentrada() {
		return this.idtipoentrada;
	}

	public void setIdtipoentrada(Integer idtipoentrada) {
		this.idtipoentrada = idtipoentrada;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoentrada", insertable = false, updatable = false)
	public DdTipoEntrada getDdTipoEntrada() {
		return this.ddTipoEntrada;
	}

	public void setDdTipoEntrada(DdTipoEntrada ddTipoEntrada) {
		this.ddTipoEntrada = ddTipoEntrada;
	}

	@Column(name = "dentrofuera")
	public Boolean getDentrofuera() {
		return this.dentrofuera;
	}

	public void setDentrofuera(Boolean dentrofuera) {
		this.dentrofuera = dentrofuera;
	}

	@Column(name = "vendida")
	public Boolean getVendida() {
		return this.vendida;
	}

	public void setVendida(Boolean vendida) {
		this.vendida = vendida;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechavalidada")
	public Date getFechaValidada() {
		return fechaValidada;
	}

	public void setFechaValidada(Date fechaValidada) {
		this.fechaValidada = fechaValidada;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fechavendida")
	public Date getFechaVendida() {
		return fechaVendida;
	}

	public void setFechaVendida(Date fechaVendida) {
		this.fechaVendida = fechaVendida;
	}

	@Column(name = "nombrecambiadotimes")
	public Integer getNombreCambiadoTimes() {
		return nombreCambiadoTimes;
	}

	public void setNombreCambiadoTimes(Integer nombreCambiadoTimes) {
		this.nombreCambiadoTimes = nombreCambiadoTimes;
	}

	@Column(name = "id_Transaccion_paypal")
	public String getIdTransaccionPayPal() {
		return idTransaccionPayPal;
	}

	public void setIdTransaccionPayPal(String idTransaccionPayPal) {
		this.idTransaccionPayPal = idTransaccionPayPal;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entrada")
	public Set<DdRrppJefeEntrada> getDdRrppJefeEntradas() {
		return this.ddRrppJefeEntradas;
	}

	public void setDdRrppJefeEntradas(Set<DdRrppJefeEntrada> ddRrppJefeEntradas) {
		this.ddRrppJefeEntradas = ddRrppJefeEntradas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entrada")
	public Set<EntradaComplemento> getEntradaComplementos() {
		return this.entradaComplementos;
	}

	public void setEntradaComplementos(Set<EntradaComplemento> entradaComplementos) {
		this.entradaComplementos = entradaComplementos;
	}

}
