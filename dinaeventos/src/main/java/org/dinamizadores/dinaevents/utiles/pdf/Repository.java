package org.dinamizadores.dinaevents.utiles.pdf;
import java.io.File;

public class Repository {

    public static CarPass findOneCarPass() {
        return new CarPass();
    }

    public static File findCarPassPDFTemplate() {
        return new File( "src/main/resources/templatesPdf/plantilla.pdf" );
    }
}