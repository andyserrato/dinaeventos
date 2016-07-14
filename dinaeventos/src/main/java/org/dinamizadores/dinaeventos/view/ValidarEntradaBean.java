package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.dinamizadores.dinaeventos.dao.DAOGenerico;
import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.Entrada;

@Named("validarEntradaBean")
@ViewScoped
public class ValidarEntradaBean implements Serializable{
	
	private String numeroSerie;
	private boolean renderedInNumeroSerie;
	private boolean renderedBotonValidar;
	private boolean renderedDatosEntrada;
	private boolean renderedFormularioDatos;
	private Entrada entradaSeleccionada;
	private String nombre, apellidos, codPostal, direccion, email, dni, telefono;
	private Date fechaNacimiento;
	private boolean camposDeshabilitados = false;
	private boolean cambioDeNombre = false;
	@EJB
	EntradaDao entradaDao;
	
	@EJB
	DAOGenerico genericoDao;
	
	@PostConstruct
	public void init(){
		
		
		//Si viene redireccionado de cambio de nombre
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cambio_de_nombre") != null){
			renderedBotonValidar = false;
			renderedInNumeroSerie = false;
			renderedDatosEntrada = false;
			renderedFormularioDatos = true;
			
			//seleccionamos la entrada
			entradaSeleccionada = (Entrada)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("entrada");
			setAtributosCamposEntrada();
			cambioDeNombre = true;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cambio_de_nombre");
		}else{
			renderedBotonValidar = true;
			renderedInNumeroSerie = true;
			renderedDatosEntrada = false;
			renderedFormularioDatos = false;
			
			cambioDeNombre = false;
		}

	}
	
	private void setAtributosCamposEntrada(){
		nombre = entradaSeleccionada.getUsuario().getNombre();
		apellidos = entradaSeleccionada.getUsuario().getApellidos();
		dni = entradaSeleccionada.getUsuario().getDni();
		email = entradaSeleccionada.getUsuario().getEmail();
		fechaNacimiento = entradaSeleccionada.getUsuario().getFechanac();
		
		camposDeshabilitados = true;
	}
	
	public void validarEntradaPaso1(){
		System.out.println("Validando entrada: " + numeroSerie);
		
		Entrada entrada = entradaDao.findByNumeroSerie(numeroSerie);
		
		if (entrada != null){
			//Validamos la entrada
			entradaSeleccionada = entrada;
			
			if (entradaSeleccionada.getDdTipoEntrada().getCanalDeVentas().equals("online")){
				setAtributosCamposEntrada();
			}
			
			renderedBotonValidar = false;
			renderedInNumeroSerie = false;
			renderedFormularioDatos = true;
			
			
			
		}else{
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No existe ninguna entrada asociada con el número de serie que has introducido"));
		}
		
	}
	
	public void validarEntradaPaso2(){
		renderedDatosEntrada = true;
		renderedFormularioDatos = false;
		
		entradaSeleccionada.setValidada(true);
		entradaDao.update(entradaSeleccionada);
		
		entradaSeleccionada.getUsuario().setNombre(nombre);
		entradaSeleccionada.getUsuario().setApellidos(apellidos);
		//entradaSeleccionada.getUsuario().setCodigoPostal(codPostal);
		
		if (direccion != null)
			entradaSeleccionada.getUsuario().setDireccion(direccion);
		
		entradaSeleccionada.getUsuario().setFechanac(fechaNacimiento);
		entradaSeleccionada.getUsuario().setDni(dni);
		entradaSeleccionada.getUsuario().setEmail(email);
		
		if (telefono != null)
			entradaSeleccionada.getUsuario().setTelefono(telefono);

		try {
			genericoDao.modificar(entradaSeleccionada.getUsuario());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Validar paso 2");
	}
	
	
	public Entrada getEntradaSeleccionada() {
		return entradaSeleccionada;
	}

	public void setEntradaSeleccionada(Entrada entradaSeleccionada) {
		this.entradaSeleccionada = entradaSeleccionada;
	}

	public boolean isRenderedDatosEntrada() {
		return renderedDatosEntrada;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public boolean isRenderedInNumeroSerie() {
		return renderedInNumeroSerie;
	}

	public boolean isRenderedBotonValidar() {
		return renderedBotonValidar;
	}

	public boolean isRenderedFormularioDatos() {
		return renderedFormularioDatos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isCamposDeshabilitados() {
		return camposDeshabilitados;
	}

	public void setCamposDeshabilitados(boolean camposDeshabilitados) {
		this.camposDeshabilitados = camposDeshabilitados;
	}

	public boolean isCambioDeNombre() {
		return cambioDeNombre;
	}

	public void setCambioDeNombre(boolean cambioDeNombre) {
		this.cambioDeNombre = cambioDeNombre;
	}
	
	
	
}
