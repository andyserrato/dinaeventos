package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import java.math.BigDecimal;
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
import javax.persistence.Table;

/**
 * DdFormapago generated by hbm2java
 */
@Entity
@Table(name = "dd_tipo_complemento", catalog = "jbossforge")
public class DdTipoComplemento implements java.io.Serializable {

	private static final long serialVersionUID = -5169190954994893032L;
	private int idTipoComplemento;
	private String nombre;
	private BigDecimal precio;
	private String descripcion;
	private Integer idEvento;
	private Evento evento;
	private Set<Entrada> entradas = new HashSet<Entrada>(0);
	
	public DdTipoComplemento() {
		nombre = null;
		precio = new BigDecimal(0);
		descripcion = null;
		idEvento = null;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtipocomplemento", unique = true, nullable = false)
	public int getIdTipoComplemento() {
		return this.idTipoComplemento;
	}

	public void setIdTipoComplemento(int idTipoComplemento) {
		this.idTipoComplemento = idTipoComplemento;
	}

	@Column(name = "nombre", length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "precio", precision = 5, scale = 2)
	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	@Column(name = "descripcion", length = 255)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "idevento")
	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idTipoEvento) {
		this.idEvento = idTipoEvento;
	}
	
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idevento", insertable = false, updatable = false)
    public Evento getEvento() {
        return this.evento;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    @ManyToMany(fetch=FetchType.LAZY, mappedBy="ddTipoComplementos")
    public Set<Entrada> getEntradas() {
        return this.entradas;
    }
    
    public void setEntradas(Set<Entrada> entradas) {
        this.entradas = entradas;
    }
    
}
