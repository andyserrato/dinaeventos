package org.dinamizadores.dinaeventos.model;
// Generated 13-jun-2016 11:45:19 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RrppJefes generated by hbm2java
 */
@Entity
@Table(name = "rrpp_jefes", catalog = "jbossforge")
public class RrppJefes implements java.io.Serializable {

	private static final long serialVersionUID = -2647515921108590406L;
	private int idrrppJefe;
	private Integer idOrganizador;
	private Integer idUsuario;
	private Integer limiteEntradas;
	private String codigoPromocional;

	public RrppJefes() {
	}

	public RrppJefes(int idrrppJefe) {
		this.idrrppJefe = idrrppJefe;
	}
	public RrppJefes(int idrrppJefe,Integer idOrganizador, Integer idUsuario, Integer limiteEntradas,
			String codigoPromocional) {
		this.idrrppJefe = idrrppJefe;
		this.idOrganizador = idOrganizador;
		this.idUsuario = idUsuario;
		this.limiteEntradas = limiteEntradas;
		this.codigoPromocional = codigoPromocional;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idrrpp_jefe", unique = true, nullable = false)
	public int getIdrrppJefe() {
		return this.idrrppJefe;
	}

	public void setIdrrppJefe(int idrrppJefe) {
		this.idrrppJefe = idrrppJefe;
	}

	@Column(name = "idorganizador")
	public Integer getIdOrganizador() {
		return this.idOrganizador;
	}

	public void setIdOrganizador(Integer idOrganizador) {
		this.idOrganizador = idOrganizador;
	}
	
	@Column(name = "idUsuario")
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "limite_entradas")
	public Integer getLimiteEntradas() {
		return this.limiteEntradas;
	}

	public void setLimiteEntradas(Integer limiteEntradas) {
		this.limiteEntradas = limiteEntradas;
	}

	@Column(name = "codigo_promocional", length = 100)
	public String getCodigoPromocional() {
		return this.codigoPromocional;
	}

	public void setCodigoPromocional(String codigoPromocional) {
		this.codigoPromocional = codigoPromocional;
	}

}
