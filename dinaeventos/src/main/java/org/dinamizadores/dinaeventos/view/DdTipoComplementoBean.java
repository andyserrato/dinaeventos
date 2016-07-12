package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.DdTipoComplemento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.log.Loggable;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;
import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;

import com.itextpdf.text.DocumentException;
import com.mangopay.entities.CardRegistration;


@Named
@ViewScoped
@Loggable

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
	
	private EntradasCompleta entrada = new EntradasCompleta();
	
	private List<EntradasCompleta> listadoEntradas = new ArrayList<EntradasCompleta>();
	
	private List<DdTipoComplemento> listadoComplemento = new ArrayList<DdTipoComplemento>();
	
	private int cuenta = 0;
	
	private Boolean envioConjunto = false;
	

	
	@PostConstruct
	public void init(){
	
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		listadoEntradas = (List<EntradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		
		calcularInfoComplementos();	
	}
	
	private void calcularInfoComplementos(){
			
		setListadoComplemento(tipoComplementoDao.listTipoComplemento());
		
		for (EntradasCompleta entrada : listadoEntradas){
			entrada.getListaComplementos().clear();
			for (DdTipoComplemento c : listadoComplemento){
			ComplementoEntero comple = new ComplementoEntero();
			comple.setComplemento(c);
			entrada.getListaComplementos().add(comple);
			}
		}
	}
	
	public void agregarComplemento(){
		total = new BigDecimal(0);
		for (EntradasCompleta entrada : listadoEntradas){
				for (ComplementoEntero c : entrada.getListaComplementos()){
					total = total.add(c.getComplemento().getPrecio().multiply(BigDecimal.valueOf(c.getCantidad())));
				}
				total = total.add(entrada.getPrecio());
			}
		
		}

	public String cambiarPagina(){
		
				Pagar pa = new Pagar();
				String idUsuario = pa.nuevoUsuario(listadoEntradas.get(0).getUsuario());
				CardRegistration tarjetaRegistrada = pa.nuevoTarjeta(idUsuario);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listaEntradas", listadoEntradas);
				
				//Enviamos los datos a la nueva pagina
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tarjeta", tarjetaRegistrada);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("envioConjunto", envioConjunto);
				
				return "/comprar/pagarCambioNombre.xhtml?faces-redirect=true";
			
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

	public List<EntradasCompleta> getListadoEntradas() {
		return listadoEntradas;
	}

	public void setListadoEntradas(List<EntradasCompleta> listadoEntradas) {
		this.listadoEntradas = listadoEntradas;
	}

	public List<DdTipoComplemento> getListadoComplemento() {
		return listadoComplemento;
	}

	public void setListadoComplemento(List<DdTipoComplemento> listadoComplemento) {
		this.listadoComplemento = listadoComplemento;
	}

	public EntradasCompleta getEntrada() {
		return entrada;
	}

	public void setEntrada(EntradasCompleta entrada) {
		this.entrada = entrada;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public Boolean getEnvioConjunto() {
		return envioConjunto;
	}

	public void setEnvioConjunto(Boolean envioConjunto) {
		this.envioConjunto = envioConjunto;
	}

	
}
