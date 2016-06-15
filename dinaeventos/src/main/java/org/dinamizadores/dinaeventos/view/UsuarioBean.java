package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.component.FacesComponent;

import org.dinamizadores.dinaeventos.model.Usuario;

/**
 * Backing bean for Usuario entities.
 * <p/>
 * This class provides CRUD functionality for all Usuario entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@ManagedBean 
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Usuario entities
	 */

	private Integer id;
	private Usuario usuario;
	
	public BigDecimal total = new BigDecimal(0);
	
	
	@PostConstruct
	public void init(){
		
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		
		
		
	}
	
	

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	
}
