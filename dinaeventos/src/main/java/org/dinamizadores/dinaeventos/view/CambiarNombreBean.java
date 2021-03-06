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
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;

import com.mangopay.entities.CardRegistration;

@Named("cambiarNombreBean")
@ViewScoped
public class CambiarNombreBean implements Serializable{

	private String numeroSerie;
	private String nombre, apellidos;
	
	private String data = "";
	
	private String accessKeyRef = null;
	
	private String returnURL = null;
	
	private CardRegistration tarjetaRegistrada = null;
	
	private boolean renderedFormularioPago;
	
	private BigDecimal costeCambioNombre;
	
	private Evento evento;
	
	@PostConstruct
	public void init(){
		renderedFormularioPago = false;
		setEvento((Evento) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("evento"));
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
	
	public String IrAPagar(){
		Entrada entrada = entradaDao.findByNumeroSerie(numeroSerie);
		String resultado = "";
		
		if (entrada != null)
		{
			
			if (entrada.getValidada() || (!entrada.getValidada() && entrada.getDdTipoEntrada().getCanalDeVentas().equals("online"))){
				DdTipoEntrada tipoEntrada = entrada.getDdTipoEntrada();
				costeCambioNombre = tipoEntrada.getCosteCambioDeNombre();

				//Ponemos en la sesion los datos de la entrada y el nombre
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("entrada", entrada);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nuevoNombre", nombre);			
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nuevosApellidos", apellidos);				
				
				Pagar pa = new Pagar();
				
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
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cambio_de_nombre", "si");				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("entrada", entrada);				

				resultado = "/validacion/validarEntrada.xhtml?faces-redirect=true";
			}
			
			
		}else{
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No existe ninguna entrada asociada con el número de serie que has introducido", ""));
		}
		
		return resultado;
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
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
}
