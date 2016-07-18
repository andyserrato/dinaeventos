package org.dinamizadores.dinaeventos.utiles;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilDinaEventos implements Serializable {

	private static final long serialVersionUID = 2713034753222414710L;
	
	 public String getMyFormattedDate(Date fecha) {
	        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
	 }

}
