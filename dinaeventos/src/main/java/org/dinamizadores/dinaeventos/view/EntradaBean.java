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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.primefaces.event.FlowEvent;

/**
 * Backing bean for Entrada entities.
 * <p/>
 * This class provides CRUD functionality for all Entrada entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@ManagedBean 
@ViewScoped
public class EntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Entrada entities
	 */

	@EJB
	private EntradaDao tipoEntradaDao; 
	
	private Integer id;
	
	private Integer cantidad = 0;
	
	private Map<Long,List<BigDecimal>> listaPrecios =  new HashMap<Long,List<BigDecimal>>();

	public BigDecimal total = new BigDecimal(0);
	
	private Entrada entrada;
	
	private List<DdTipoEntrada> tiposEntrada;
	
	private boolean skip;




	@PostConstruct
	public void init() {
		System.out.println("Inicio init");
		
		this.tiposEntrada = tipoEntradaDao.listTipoEntrada();
		for (DdTipoEntrada tipo : tiposEntrada){
			listaPrecios.put((long) tipo.getIdtipoentrada(), new ArrayList<BigDecimal>());
		}
		System.out.println("Fin init");
	}
	
	public void sumarTotal(Long idTipoEntrada){
		System.out.println("Inicio sumarTotal");
		
		total = new BigDecimal(0);
		
		for (DdTipoEntrada tipo : tiposEntrada){
			if (idTipoEntrada == tipo.getIdtipoentrada()){
				
				listaPrecios.get(idTipoEntrada).clear();
				for (int j = 0; j < cantidad; j++)
					listaPrecios.get(idTipoEntrada).add(tipo.getPrecio());
				}
		}
		
		for (Entry<Long,List<BigDecimal>> e: listaPrecios.entrySet())
			for (BigDecimal a : e.getValue())
				total = total.add(a);
			
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
		
		System.out.println("Fin sumarTotal");
	}

	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Entrada getEntrada() {
		return this.entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public EntradaDao getTipoEntrada() {
		return tipoEntradaDao;
	}

	public void setTipoEntrada(EntradaDao tipoEntrada) {
		this.tipoEntradaDao = tipoEntrada;
	}

	public List<DdTipoEntrada> getTiposEntrada() {
		return tiposEntrada;
	}

	public void setTiposEntrada(List<DdTipoEntrada> tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Map<Long, List<BigDecimal>> getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(Map<Long, List<BigDecimal>> listaPrecios) {
		this.listaPrecios = listaPrecios;
	}
	
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}
	
}
