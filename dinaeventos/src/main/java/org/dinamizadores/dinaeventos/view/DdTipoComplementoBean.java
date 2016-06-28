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
import org.dinamizadores.dinaeventos.dto.complementoEntero;
import org.dinamizadores.dinaeventos.dto.entradasCompleta;

import com.itextpdf.text.DocumentException;


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
	
	private entradasCompleta entrada = new entradasCompleta();
	
	private List<entradasCompleta> listadoEntradas = new ArrayList<entradasCompleta>();
	
	private List<DdTipoComplemento> listadoComplemento = new ArrayList<DdTipoComplemento>();
	
	private int cuenta = 0;
	
	private Boolean envioConjunto = false;
	

	
	@PostConstruct
	public void init(){
	
		total = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		listadoEntradas = (List<entradasCompleta>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaEntradas");
		
		calcularInfoComplementos();	
	}
	
	private void calcularInfoComplementos(){
			
		setListadoComplemento(tipoComplementoDao.listTipoComplemento());
		
		for (entradasCompleta entrada : listadoEntradas){
			entrada.getListaComplementos().clear();
			for (DdTipoComplemento c : listadoComplemento){
			complementoEntero comple = new complementoEntero();
			comple.setComplemento(c);
			entrada.getListaComplementos().add(comple);
			}
		}
	}
	
	public void agregarComplemento(){
		total = new BigDecimal(0);
		for (entradasCompleta entrada : listadoEntradas){
				for (complementoEntero c : entrada.getListaComplementos()){
					total = total.add(c.getComplemento().getPrecio().multiply(BigDecimal.valueOf(c.getCantidad())));
				}
				total = total.add(entrada.getPrecio());
			}
		
		}
	
	
	public void cambiarPagina(){
		
			try {
				
				FormarPDF.main(listadoEntradas, envioConjunto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public Boolean getEnvioConjunto() {
		return envioConjunto;
	}

	public void setEnvioConjunto(Boolean envioConjunto) {
		this.envioConjunto = envioConjunto;
	}

	
}
