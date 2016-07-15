package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.EntradaComplemento;
import org.dinamizadores.dinaeventos.model.Usuario;
import org.dinamizadores.dinaeventos.utiles.BBDDFaker;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;
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
	
	@EJB
	private DAOGenerico genericoDao;
	
    @PostConstruct
	public void init(){
    	opcionesComboMostrar = new ArrayList<SelectItem>();
    	
    	opcionesComboMostrar.add(new SelectItem("1","opcion1"));
    	opcionesComboMostrar.add(new SelectItem("2","opcion2"));
    	
    	try {
//			bbddFaker.llenarBBDD();
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
		
		itemSeleccionado.getUsuario().setNombre((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevoNombre"));
		itemSeleccionado.getUsuario().setApellidos((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nuevosApellidos"));

		itemSeleccionado.getUsuario().setEmail(emailSeleccionado);
		
		
		try {
			//Persistimos los datos en la base de datos
			genericoDao.modificar(itemSeleccionado.getUsuario());
			//Creamos el pdf con la nueva entrada y se la enviamos por correo
			ArrayList<EntradasCompleta> entradas = new ArrayList<EntradasCompleta>();
			
			EntradasCompleta eentradaCompleta = new EntradasCompleta();
			
			eentradaCompleta.setNombre(itemSeleccionado.getUsuario().getNombre());
			eentradaCompleta.setNumeroserie(itemSeleccionado.getNumeroserie());
			eentradaCompleta.setFechaVendida(itemSeleccionado.getFechaVendida());
			eentradaCompleta.setPrecio(itemSeleccionado.getPrecio());
			eentradaCompleta.setIdEntrada(Long.valueOf(itemSeleccionado.getIdentrada()));
			eentradaCompleta.setIdTipoEntrada((long)1);
			eentradaCompleta.setUsuario(itemSeleccionado.getUsuario());
			
			
			Iterator it = itemSeleccionado.getEntradaComplementos().iterator();
			ArrayList<ComplementoEntero> listaComplementos = new ArrayList<>(); 
			
			while (it.hasNext()){
				EntradaComplemento complemento = (EntradaComplemento) it.next();
				System.out.println("Descripcion:" + complemento.getDdTipoComplemento().getDescripcion());
				ComplementoEntero compl = new ComplementoEntero();
				compl.setComplemento(complemento.getDdTipoComplemento());
				//TODO BIEN
				compl.setCantidad(1);
				
				listaComplementos.add(compl);
			}
			
			eentradaCompleta.setListaComplementos(listaComplementos);
			
			//eentradaCompleta.set
			
			entradas.add(eentradaCompleta);
			
			FormarPDF.main(entradas, itemSeleccionado.getEvento(), false);
			
		}catch (Exception e){
			e.printStackTrace();
		}
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
