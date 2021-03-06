package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.dao.UsuarioDao;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;

/**
 * Backing bean for Usuario entities.
 * <p/>
 * This class provides CRUD functionality for all Usuario entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */
@Named("usuarioBean")
@ViewScoped
@Loggable
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioDao usuarioDao;
	private Integer id;
	private Usuario usuario;
	private BigDecimal total = new BigDecimal(0);
	private Integer cantidad = 0;
	private Evento evento;
	private String nombreEntrada = null;
	private Map<Long,List<BigDecimal>> listaPrecios =  new HashMap<Long,List<BigDecimal>>();
	private List<EntradasCompleta> listadoEntradas = new ArrayList<EntradasCompleta>();
	private List<DdTipoEntrada> tiposEntrada = new ArrayList<DdTipoEntrada>();
	
	@PostConstruct
	public void init(){
		
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		listaPrecios = (Map<Long, List<BigDecimal>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lista");
		tiposEntrada = (List<DdTipoEntrada>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tiposEntrada");
		evento = (Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento");
		calcularInfoUsuarios();
	
	}
	
	private void calcularInfoUsuarios(){
		
		for (Entry<Long,List<BigDecimal>> e: listaPrecios.entrySet()){
			for (int i = 0; i < e.getValue().size(); i++){
				EntradasCompleta entrada = new EntradasCompleta();
				entrada.setIdTipoEntrada(e.getKey());
				entrada.setCantidadEntradas(e.getValue());
				for (DdTipoEntrada d : tiposEntrada)
					if (d.getIdtipoentrada() == e.getKey()){
						entrada.setNombre(d.getNombre());
						entrada.setIdTipoEntrada(Long.valueOf(d.getIdtipoentrada()));
					}
				entrada.setPrecio(e.getValue().get(0));
				listadoEntradas.add(entrada);
			}
					
		}
		
	}
	public void verificarDNI(AjaxBehaviorEvent event){
		try{
			for(EntradasCompleta e: listadoEntradas){
				Usuario us = new Usuario();
				us = usuarioDao.getUsuarioDni(e.getUsuario().getDni(), evento.getIdevento());
				if (us != null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!","El DNI indicado ya existe en el sistema."));
				}
				
			}
			}catch(Exception e){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!","El DNI indicado ya existe en el sistema."));
			}
		
	}
	
	public String cambiarPagina(){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradas", listadoEntradas);
		return "/comprar/comprarComplemento.xhtml?faces-redirect=true";
	}
	
	public void crearUsuarioBasico() {
		
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

	public Map<Long,List<BigDecimal>> getListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(Map<Long,List<BigDecimal>> listaPrecios) {
		this.listaPrecios = listaPrecios;
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

	public List<EntradasCompleta> getListadoEntradas() {
		return listadoEntradas;
	}

	public void setListadoEntradas(List<EntradasCompleta> listadoEntradas) {
		this.listadoEntradas = listadoEntradas;
	}

	public List<DdTipoEntrada> getTiposEntrada() {
		return tiposEntrada;
	}

	public void setTiposEntrada(List<DdTipoEntrada> tiposEntrada) {
		this.tiposEntrada = tiposEntrada;
	}
	
}