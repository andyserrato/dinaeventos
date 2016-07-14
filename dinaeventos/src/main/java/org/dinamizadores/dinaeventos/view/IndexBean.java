package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.dao.EventoDao;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;

/**
 * Backing bean for Entrada entities.
 * <p/>
 * This class provides CRUD functionality for all Entrada entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named("indexBean")
@ViewScoped
@Loggable
public class IndexBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Entrada entities
	 */

	@EJB
	private EventoDao eventoDao; 
	
	private List<Evento> listaEventos = new ArrayList<Evento>();
	
	


	@PostConstruct
	public void init() {
		
		setListaEventos(eventoDao.listAll(null, null));
		System.out.println("Las entradas" + getListaEventos());
		
	}
	


	public String irComprar(Evento evento){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);
		
		return "/comprar/comprarEntrada.xhtml?faces-redirect=true";
	}
	
	public String irActivar(Evento evento){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);
		return "/validacion/validarEntrada.xhtml?faces-redirect=true";
	}
	
	public String irCambioNombre(Evento evento){
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("evento", evento);
		return "/cambioNombre/cambioNombre.xhtml?faces-redirect=true";
	}



	public List<Evento> getListaEventos() {
		return listaEventos;
	}



	public void setListaEventos(List<Evento> listaEventos) {
		this.listaEventos = listaEventos;
	}
	
	
	
}
