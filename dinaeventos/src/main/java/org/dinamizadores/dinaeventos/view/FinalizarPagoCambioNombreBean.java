package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.EntradaComplemento;
import org.dinamizadores.dinaeventos.utiles.Constantes;
import org.dinamizadores.dinaeventos.utiles.GeneradorEntradas;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.Pagar;

import com.itextpdf.text.DocumentException;
import com.mangopay.entities.CardRegistration;
import com.mangopay.entities.PayIn;

@Named("finalizarPagoCambioNombreBean")
@ViewScoped
public class FinalizarPagoCambioNombreBean implements Serializable{

	private BigDecimal total;
	
	private String dummyVar = ""; 
	private boolean rendererMensajeOk;
	private boolean rendererMensajeError;
	
	@EJB
	DAOGenerico genericoDao;
	
	@PostConstruct
	public void init(){
		System.out.println("Inicializacion");
		
		rendererMensajeError = true;
		rendererMensajeOk = false;
		
		efectuarPago();
	}
	
	public void dummyMethod(){
		
	}
	
	
	public void efectuarPago(){
		
		//crearEntradasUsuarios();
		Pagar pa = new Pagar();
		CardRegistration tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
		total = (BigDecimal)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		
		pa.setTotal(total);
		String[] lista = null;
		lista =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("data");
		
		tarjetaRegistrada.RegistrationData = lista[0];
	
		PayIn resultadoPago = pa.actualizarTarjeta(tarjetaRegistrada);
		
		if (resultadoPago.Status.name().equals(Constantes.MangoPay.PAGO_OK)){
			rendererMensajeOk = true;
			rendererMensajeError = false;
			
			//Actualizamos la entidad en la base de datos
			Entrada e = (Entrada)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("entrada");
			e.getUsuario().setNombre((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevoNombre"));
			e.getUsuario().setApellidos((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevosApellidos"));

			
			try {
				genericoDao.modificar(e.getUsuario());
				
				GeneradorEntradas.generarEntrada(e);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}

	public String getDummyVar() {
		return dummyVar;
	}

	public void setDummyVar(String dummyVar) {
		this.dummyVar = dummyVar;
	}



	public boolean isRendererMensajeOk() {
		return rendererMensajeOk;
	}



	public void setRendererMensajeOk(boolean rendererMensajeOk) {
		this.rendererMensajeOk = rendererMensajeOk;
	}



	public boolean isRendererMensajeError() {
		return rendererMensajeError;
	}



	public void setRendererMensajeError(boolean rendererMensajeError) {
		this.rendererMensajeError = rendererMensajeError;
	}
	
	
	
}
