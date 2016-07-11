package org.dinamizadores.dinaeventos.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.dinamizadores.dinaeventos.dao.EntradaDao;
import org.dinamizadores.dinaeventos.model.Entrada;

@Named("descargarEntradasBean")
@ViewScoped
public class DescargarEntradasBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	EntradaDao entradaDao;
	
	public void generarExcel(){
		System.out.println("Generando excel");
		
		ArrayList<Entrada> entradasFiltradas = (ArrayList<Entrada>) entradaDao.listByEventID(1);
		
		Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("Listado de entradas");
		
		Row row = null;
		Entrada e = null;
		
		//Cabeceras
		row = sheet1.createRow((short)0);
		 
		row.createCell(0).setCellValue("NOMBRE");
		row.createCell(1).setCellValue("APELLIDOS");
		row.createCell(2).setCellValue("DNI");
		
		for (int i = 0; i < entradasFiltradas.size(); i++){
			e = entradasFiltradas.get(i); 
			row = sheet1.createRow((short)i + 1);
			 
			row.createCell(0).setCellValue(e.getUsuario().getNombre());
			row.createCell(1).setCellValue(e.getUsuario().getApellidos());
			row.createCell(2).setCellValue(e.getUsuario().getDni());

		}
		 
	
	
		
		// Write the output to a file
	    FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream("workbook.xls");
			wb.write(fileOut);
		    fileOut.close();
		    System.out.println("Excel generado");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	}
}


