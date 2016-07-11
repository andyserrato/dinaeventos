package org.dinamizadores.dinaeventos.view;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("cambiarNombreBean")
@ViewScoped
public class CambiarNombreBean implements Serializable{

	private String numeroSerie;
	private String nombre;
	
	
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
}
