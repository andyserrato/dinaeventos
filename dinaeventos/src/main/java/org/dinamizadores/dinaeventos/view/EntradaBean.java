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

@Named("entradaBean")
@ViewScoped
@Loggable
public class EntradaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Entrada entities
	 */

	@EJB
	private EntradaDao tipoEntradaDao; 
	
	private Integer id;
	
	private int cantidad;
	
	
	private Map<Long,List<BigDecimal>> listaPrecios =  new HashMap<Long,List<BigDecimal>>();

	private BigDecimal total = new BigDecimal(0);
	
	private Entrada entrada;
	
	private List<DdTipoEntrada> tiposEntrada;
	
	private Evento evento;
	
	private boolean skip;


	@PostConstruct
	public void init() {
		
		//reiniciamos cada vez que cargamos el Bean
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lista", listaPrecios);
		cantidad=0;
		evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");
		
		this.tiposEntrada = tipoEntradaDao.listTipoEntrada(evento.getIdevento());
		for (DdTipoEntrada tipo : tiposEntrada){
			listaPrecios.put((long) tipo.getIdtipoentrada(), new ArrayList<BigDecimal>());
		}
		
	}
	
	public void sumarTotal(Long idTipoEntrada){
		
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
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lista", listaPrecios);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tiposEntrada", tiposEntrada);
		
	}
	
	public boolean isRendererBoton() {
		
		boolean desactivado = true;
        int res;
        
        res = total.compareTo(BigDecimal.valueOf(0));
		
        //Iguales
		if (res == 0){
			desactivado = true;
		//0 menor que total
		}else if (res == 1){
			desactivado = false;
		//total menor que 0
		}else if (res == -1){
			desactivado = true;
		}
		
		return desactivado;
	}

	public String cambiarPagina(){
		
		
		return "/comprar/formularioCliente.xhtml?faces-redirect=true";
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
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
