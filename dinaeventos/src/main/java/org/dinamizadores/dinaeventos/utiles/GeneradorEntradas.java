package org.dinamizadores.dinaeventos.utiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Entrada;
import org.dinamizadores.dinaeventos.model.EntradaComplemento;
import org.dinamizadores.dinaeventos.utiles.pdf.FormarPDF;

import com.itextpdf.text.DocumentException;

public class GeneradorEntradas {

	public static void generarEntrada(Entrada e){
		//Creamos el pdf con la nueva entrada y se la enviamos por correo
		ArrayList<EntradasCompleta> entradas = new ArrayList<EntradasCompleta>();
		
		EntradasCompleta eentradaCompleta = new EntradasCompleta();
		
		eentradaCompleta.setNombre(e.getUsuario().getNombre());
		eentradaCompleta.setNumeroserie(e.getNumeroserie());
		eentradaCompleta.setFechaVendida(e.getFechaVendida());
		eentradaCompleta.setPrecio(e.getPrecio());
		eentradaCompleta.setIdEntrada(Long.valueOf(e.getIdentrada()));
		eentradaCompleta.setIdTipoEntrada((long)1);
		eentradaCompleta.setUsuario(e.getUsuario());
		
		
		Iterator it = e.getEntradaComplementos().iterator();
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
		
		try {
			FormarPDF.main(entradas, e.getEvento(), false);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
}
