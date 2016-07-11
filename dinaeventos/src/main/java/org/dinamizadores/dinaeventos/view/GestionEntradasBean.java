package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;
import org.primefaces.event.RowEditEvent;

@Named("gestionEntradasBean")
@ViewScoped
public class GestionEntradasBean implements Serializable{
	
	private ArrayList<Entrada> entradas = null;
	private Entrada itemSeleccionado = null;
	private String textoBusqueda = "";
	private String emailSeleccionado = "";
	
	private ArrayList<SelectItem> opcionesComboMostrar = null;
	private SelectItem opcionSeleccionadaMostrar = null;
	
	@EJB
	private EntradaDao entradaDao;
	
	@EJB
	private BBDDFaker bbddFaker;
	
    @PostConstruct
	public void init(){
    	opcionesComboMostrar = new ArrayList<SelectItem>();
    	
    	opcionesComboMostrar.add(new SelectItem("1","opcion1"));
    	opcionesComboMostrar.add(new SelectItem("2","opcion2"));
    	
    	try {
			bbddFaker.llenarBBDD();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    public String getEmailSeleccionado() {
		System.out.println("ajajaja:" + emailSeleccionado);
    	return emailSeleccionado;
	}

	public void setEmailSeleccionado(String emailSeleccionado) {
		this.emailSeleccionado = emailSeleccionado;
		
		System.out.println("Mail seleccionado: " + emailSeleccionado);

	}



	public void regenerarEntrada(){
    	
		System.out.println("Regenerando entrada: " + emailSeleccionado);
    }
    
    
	public SelectItem getOpcionSeleccionadaMostrar() {
		return opcionSeleccionadaMostrar;
	}



	public void setOpcionSeleccionadaMostrar(SelectItem opcionSeleccionadaMostrar) {
		this.opcionSeleccionadaMostrar = opcionSeleccionadaMostrar;
	}

	public ArrayList<SelectItem> getOpcionesComboMostrar() {
		return opcionesComboMostrar;
	}

	public void setOpcionesComboMostrar(ArrayList<SelectItem> opcionesComboMostrar) {
		this.opcionesComboMostrar = opcionesComboMostrar;
	}


	public ArrayList<Entrada> getEntradas() {
		System.out.println("JUAS: ");
		
		return entradas;
	}
	
	public void setEntradas(ArrayList<Entrada> entradas) {
		this.entradas = entradas;
	}
	
	public Entrada getItemSeleccionado() {
		return itemSeleccionado;
	}
	
	public void setItemSeleccionado(Entrada itemSeleccionado) {
		this.itemSeleccionado = itemSeleccionado;
		
		this.emailSeleccionado = itemSeleccionado.getUsuario().getEmail();
	}
	
	public String getTextoBusqueda() {
		return textoBusqueda;
	}
	
	public void setTextoBusqueda(String textBusqueda) {
		this.textoBusqueda = textBusqueda;
		
		System.out.println(textBusqueda);
		
		if (!textBusqueda.equals("")){
			entradas = new ArrayList<Entrada>();
			
			entradas = (ArrayList<Entrada>)entradaDao.findByField(textBusqueda);
			
			for (Entrada e: entradas){
				Usuario u = e.getUsuario();
				System.out.println(u.getNombre());
				System.out.println(u.getEmail());
			}
			
			System.out.println(entradas.size());
		}
		
	}
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("La entrada se ha modificado correctamente.","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        Entrada e = ((Entrada) event.getObject());
        
        System.out.println(e.getUsuario().getDni());
        entradaDao.update(e);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("No se ha modificado la entrada.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	
}
