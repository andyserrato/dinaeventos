package org.dinamizadores.dinaeventos.utiles.pdf;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.dinamizadores.dinaeventos.dto.ComplementoEntero;
import org.dinamizadores.dinaeventos.dto.EmailBasico;
import org.dinamizadores.dinaeventos.dto.EntradasCompleta;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.Email;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * Created by koen on 9-06-14.
 */
public class FormarPDF {

    private static Logger logger = Logger.getLogger ( FormarPDF.class.getName() );
   

    public static void main ( List<EntradasCompleta> listaEntradas,Evento evento, Boolean enviarConjunto ) throws IOException, DocumentException {

        logger.info ( "fetch the Car-Pass PDF template from a mocked database" );
        File pdfTemplate = Repository.findCarPassPDFTemplate();

        logger.info ( "start the rendering of the Entrada" );
        crearPDF(listaEntradas,evento, pdfTemplate, enviarConjunto);
    }

    private static void crearPDF ( List<EntradasCompleta> listaEntrada,Evento evento, File pdfTemplate, Boolean envioConjunto ) throws IOException, DocumentException {
    	
    	//String ruta = null;
        //loop de listado de entradas a generar
    	for (EntradasCompleta entrada : listaEntrada){
    		
            //ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/templatesPdf/");
            logger.info ( "define the File to which the completed Car-Pass PDF template should be saved" );
            File entradaCompletaPDF = new File ( "entrada-" + entrada.getUsuario().getDni() + ".pdf" );
         
    		hacerEntrada(entrada,evento, pdfTemplate, entradaCompletaPDF);
    			
    	}

    	if (!envioConjunto){
    		Email correo = new Email();
    		EmailBasico datosEmail = new EmailBasico();
    		datosEmail.setNombreUsuario(listaEntrada.get(0).getUsuario().getNombre());
    		datosEmail.setMailReceptor(listaEntrada.get(0).getUsuario().getEmail());
    		datosEmail.setTitulo(evento.getNombre());
    		correo.enviarEmail(listaEntrada, datosEmail);
    	}else if(envioConjunto){
    		for (EntradasCompleta entrada: listaEntrada){
    			Email correo = new Email();
    			EmailBasico datosEmail = new EmailBasico();
    			List<EntradasCompleta> auxlista = new ArrayList<EntradasCompleta>();
    			auxlista.add(entrada);
    			datosEmail.setNombreUsuario(entrada.getUsuario().getNombre());
    			datosEmail.setMailReceptor(entrada.getUsuario().getEmail());
    			datosEmail.setTitulo(evento.getNombre());
    			correo.enviarEmail(auxlista, datosEmail);
    		}
    	}
    
    }

    //TODO [EQUIPO] mejorar código, crear funciones xq está muy acoplada 
    private static void hacerEntrada (EntradasCompleta entrada, Evento evento , File pdfTemplate, File entradaCompletaPDF ) throws IOException, DocumentException {

       
    	try (
                FileInputStream is = new FileInputStream(pdfTemplate);
                FileOutputStream os = new FileOutputStream(entradaCompletaPDF);
            ) {
                logger.info ( "open the PDF template" );
                PdfReader pdfReader = new PdfReader(is);
                PdfStamper stamper = new PdfStamper(pdfReader, os);

                logger.info ( "convert the Car-Pass POJO in to a Key-Value pair Map" );
//                Map<String, String> model = BeanUtils.recursiveDescribe ( entrada, "entrada" );
//                Map<String, String> model2 = BeanUtils.recursiveDescribe ( evento, "evento" );
                
                Map<String, String> datosEventoPlantillaPdf = new TreeMap<>();
                datosEventoPlantillaPdf.put("evento.fecha_ini", new SimpleDateFormat("dd/MM/yyyy").format(evento.getFechaIni()));
                datosEventoPlantillaPdf.put("evento.nombrelugar", evento.getNombreLugar());
                datosEventoPlantillaPdf.put("evento.nombre", evento.getNombre());
                datosEventoPlantillaPdf.put("organizadores.nombre", evento.getNombreOrganizador());
                
                Map<String, String> datosEntradaPlantillaPdf = new TreeMap<>();
                datosEntradaPlantillaPdf.put("entrada.idEntrada", String.valueOf(entrada.getIdEntrada()));
                datosEntradaPlantillaPdf.put("entrada.numeroserie", entrada.getNumeroserie());
                datosEntradaPlantillaPdf.put("entrada.fechaVendida", new SimpleDateFormat("dd/MM/yyyy").format(entrada.getFechaVendida()));
                datosEntradaPlantillaPdf.put("entrada.precio", entrada.getPrecio().toString());
                // Es el nombre del tipo de entrada corregir
                datosEntradaPlantillaPdf.put("entrada.nombre", entrada.getNombre());
                // buscar descripcion del tipo de entrada
//                datosEntradaPlantillaPdf.put("entrada.descripcion", entrada.getTipoEntrada.);
                datosEntradaPlantillaPdf.put("entrada.usuario.nombreCompleto", entrada.getUsuario().getNombre() + " " + entrada.getUsuario().getApellidos());
                datosEntradaPlantillaPdf.put("entrada.usuario.dni", entrada.getUsuario().getDni());
                
                int numeroComplemento = 0;
                for (ComplementoEntero complemento : entrada.getListaComplementos()) {
                	if (complemento.getCantidad() > 0) {
	                	datosEntradaPlantillaPdf.put("entrada.listaComplementos[" + numeroComplemento + "].complemento.nombre", complemento.getComplemento().getNombre());
	                	datosEntradaPlantillaPdf.put("entrada.listaComplementos[" + numeroComplemento + "].cantidad", String.valueOf(complemento.getCantidad()));
	                	numeroComplemento++;
                	}
                }
                
                logger.info ( "get the fields from the PDF template" );
                AcroFields fields = stamper.getAcroFields();

                logger.info ( "set all the Key-Value pairs of the Car-Pass POJO on the AcroField object" );
                for ( Map.Entry<String, String> entry : datosEventoPlantillaPdf.entrySet() ) {
                    fields.setField ( entry.getKey(), entry.getValue() );
                }
                
                for ( Map.Entry<String, String> entry : datosEntradaPlantillaPdf.entrySet() ) {
                    fields.setField ( entry.getKey(), entry.getValue() );
                }

                logger.info ( "create QR code" );
                BarcodeQRCode qrcode = new BarcodeQRCode("https://ws.car-pass.be/verify/" + entrada.getNumeroserie(), 100, 100, null);

                logger.info ( "get the PDF content" );
                PdfContentByte content = stamper.getOverContent(pdfReader.getNumberOfPages());
                
                System.out.println("Esto tiene" + pdfReader.getNumberOfPages());

                logger.info("insert the QR code as an image on the PDF");
                Image image = qrcode.getImage();
                image.setAbsolutePosition(250, 640);
                content.addImage(image);

                logger.info ( "flatten the PDF form" );
                stamper.setFormFlattening(true);

                logger.info ( "enable full compression" );
//                stamper.setFullCompression();

                logger.info("remove all embedded fonts");
//                PDFUtil.removeEmbeddedFonts(pdfReader, stamper);

                logger.info ( "close the PDF file" );
                stamper.close();
                pdfReader.close();
                
           
    	}
    }

}
