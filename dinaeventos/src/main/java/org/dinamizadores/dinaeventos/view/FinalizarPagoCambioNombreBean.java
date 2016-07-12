package org.dinamizadores.dinaeventos.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;
import org.dinamizadores.dinaeventos.utiles.plataformapagos.pagar;

import com.itextpdf.text.DocumentException;
import com.mangopay.entities.CardRegistration;

@Named("finalizarPagoCambioNombreBean")
@ViewScoped
public class FinalizarPagoCambioNombreBean implements Serializable{

	private BigDecimal total;
	
	private String juas = "Prueba"; 
	
	@EJB
	DAOGenerico genericoDao;
	
	@PostConstruct
	public void init(){
		efectuarPago();
		System.out.println("Inicializacion");
	}
	
	public void efectuarPago(){
		
		//crearEntradasUsuarios();
		pagar pa = new pagar();
		CardRegistration tarjetaRegistrada = (CardRegistration) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tarjeta");
		total = (BigDecimal)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
		
		pa.setTotal(total);
		String[] lista = null;
		lista =  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap().get("data");
		
		tarjetaRegistrada.RegistrationData = lista[0];
	
		pa.actualizarTarjeta(tarjetaRegistrada);
		
		Entrada e = (Entrada)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("entrada");
		e.getUsuario().setNombre((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevoNombre"));
		System.out.println("Nombre cambiado: " + e.getUsuario().getNombre());
		try {
			genericoDao.modificar(e.getUsuario());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getJuas() {
		return juas;
	}

	public void setJuas(String juas) {
		this.juas = juas;
	}
	
	
}
