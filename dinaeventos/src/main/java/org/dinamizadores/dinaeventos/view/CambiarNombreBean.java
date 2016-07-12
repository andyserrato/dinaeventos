package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.DdTipoEntrada;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.pagar;

import com.mangopay.entities.CardRegistration;

@Named("cambiarNombreBean")
@ViewScoped
public class CambiarNombreBean implements Serializable{

	private String numeroSerie;
	private String nombre;
	
	private String data = "";
	
	private String accessKeyRef = null;
	
	private String returnURL = null;
	
	private CardRegistration tarjetaRegistrada = null;
	
	private boolean renderedFormularioPago;
	
	private BigDecimal costeCambioNombre;
	
	@PostConstruct
	public void init(){
		renderedFormularioPago = false;
	}
	
	@EJB 
	EntradaDao entradaDao;
	
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void cambiarNombre(){
		System.out.println("Cambiar nombre");
	}
	
	public void IrAPagar(){
		Entrada entrada = entradaDao.findByNumeroSerie(numeroSerie);
		
		if (entrada != null)
		{
			DdTipoEntrada tipoEntrada = entrada.getDdTipoEntrada();
			costeCambioNombre = tipoEntrada.getCosteCambioDeNombre();

			//Ponemos en la sesion los datos de la entrada y el nombre
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("entrada", entrada);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nuevoNombre", nombre);			
			
			pagar pa = new pagar();
			
			String idUsuario = pa.nuevoUsuario(entrada.getUsuario());
			tarjetaRegistrada = pa.nuevoTarjeta(idUsuario);
			
			data = tarjetaRegistrada.PreregistrationData;
			accessKeyRef = tarjetaRegistrada.AccessKey;
			returnURL = "http://localhost:8080/dinaeventos/faces/comprar/finalizarPagoCambioNombre.xhtml?faces-redirect=true";
			
			//Enviamos los datos a la nueva pagina
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tarjeta", tarjetaRegistrada);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", tipoEntrada.getCosteCambioDeNombre());
				
			renderedFormularioPago = true;
			
		}else{
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe ninguna entrada asociada con el número de serie que has introducido", ""));
		}
		
		//return "/comprar/pagarEntradas.xhtml?faces-redirect=true";
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getAccessKeyRef() {
		return accessKeyRef;
	}
	public void setAccessKeyRef(String accessKeyRef) {
		this.accessKeyRef = accessKeyRef;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public boolean isRenderedFormularioPago() {
		return renderedFormularioPago;
	}
	public void setRenderedFormularioPago(boolean renderedFormularioPago) {
		this.renderedFormularioPago = renderedFormularioPago;
	}
	public BigDecimal getCosteCambioNombre() {
		return costeCambioNombre;
	}
	public void setCosteCambioNombre(BigDecimal costeCambioNombre) {
		this.costeCambioNombre = costeCambioNombre;
	}
	
	
	
	
	
}
