package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaevents.dto.complementoEntero;
import org.dinamizadores.dinaevents.dto.entradasCompleta;


@ManagedBean 
@ViewScoped
public class DdTipoComplementoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving DdTipoComplementoBean entities
	 */
	
	@EJB
	private EntradaDao tipoComplementoDao; 

	private Integer id;
	private Usuario usuario;
	
	private BigDecimal total = new BigDecimal(0);
	
	private Integer cantidad = 0;
	
	private String nombreEntrada = null;
	
	private entradasCompleta entrada = new entradasCompleta();
	
	private List<entradasCompleta> listadoEntradas = new ArrayList<entradasCompleta>();
	
	private List<DdTipoComplemento> listadoComplemento = new ArrayList<DdTipoComplemento>();
	
	private int cuenta = 0;
	

	
	@PostConstruct
	public void init(){
		System.out.println("Inicio init");
		
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		listadoEntradas = (List<entradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		
		calcularInfoComplementos();
	
		System.out.println("Fin init");
	}
	
	private void calcularInfoComplementos(){
		System.out.println("Inicio calcularInfoComplementos");
		
		setListadoComplemento(tipoComplementoDao.listTipoComplemento());
		
		for (entradasCompleta entrada : listadoEntradas){
			entrada.getListaComplementos().clear();
			for (DdTipoComplemento c : listadoComplemento){
			complementoEntero comple = new complementoEntero();
			comple.setComplemento(c);
			entrada.getListaComplementos().add(comple);
			}
		}
		
		System.out.println("Fin calcularInfoComplementos");
	}
	
	public String cambiarPagina(){
		
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradas", listadoEntradas);
		
		return "/comprar/comprarEntrada.xhtml?faces-redirect=true";
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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombreEntrada() {
		return nombreEntrada;
	}

	public void setNombreEntrada(String nombreEntrada) {
		this.nombreEntrada = nombreEntrada;
	}

	public List<entradasCompleta> getListadoEntradas() {
		return listadoEntradas;
	}

	public void setListadoEntradas(List<entradasCompleta> listadoEntradas) {
		this.listadoEntradas = listadoEntradas;
	}

	public List<DdTipoComplemento> getListadoComplemento() {
		return listadoComplemento;
	}

	public void setListadoComplemento(List<DdTipoComplemento> listadoComplemento) {
		this.listadoComplemento = listadoComplemento;
	}

	public entradasCompleta getEntrada() {
		return entrada;
	}

	public void setEntrada(entradasCompleta entrada) {
		this.entrada = entrada;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	
}