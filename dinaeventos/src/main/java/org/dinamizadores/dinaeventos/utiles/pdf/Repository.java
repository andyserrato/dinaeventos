package org.dinamizadores.dinaeventos.utiles.pdf;
import java.io.File;


import javax.faces.context.FacesContext;

public class Repository {

    public static CarPass findOneCarPass() {
        return new CarPass();
    }

    public File findCarPassPDFTemplate() {
    	String ruta = null;
    	
    	ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/templatesPdf/plantilla.pdf");
        return new File(ruta);

public class Repository {

    public  CarPass findOneCarPass() {
        return new CarPass();
    }

    public static File findCarPassPDFTemplate() {
        return new File( "src/main/resources/templatesPdf/plantilla.pdf" );

    }
}