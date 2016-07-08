package org.dinamizadores.dinaeventos.utiles.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.Base64.InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import org.dinamizadores.dinaeventos.dto.EmailBasico;
import org.dinamizadores.dinaeventos.dto.entradasCompleta;
import org.dinamizadores.dinaeventos.model.Evento;
import org.dinamizadores.dinaeventos.utiles.Email;

/**
 * Created by koen on 9-06-14.
 */
public class FormarPDF {

    private static Logger logger = Logger.getLogger ( FormarPDF.class.getName() );
   

    public static void main ( List<entradasCompleta> listaEntradas,Evento evento, Boolean enviarConjunto ) throws IOException, DocumentException {


        logger.info ( "fetch the Car-Pass PDF template from a mocked database" );
        File pdfTemplate = Repository.findCarPassPDFTemplate();

        logger.info ( "start the rendering of the Entrada" );
        crearPDF(listaEntradas,evento, pdfTemplate, enviarConjunto);
        

    }

    private static void crearPDF ( List<entradasCompleta> listaEntrada,Evento evento, File pdfTemplate, Boolean envioConjunto ) throws IOException, DocumentException {
    	
    	//String ruta = null;
        //loop de listado de entradas a generar
    	for (entradasCompleta entrada : listaEntrada){
    		
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
    		correo.enviarEmail(listaEntrada, datosEmail);
    	}else if(envioConjunto){
    		for (entradasCompleta entrada: listaEntrada){
    			Email correo = new Email();
    			EmailBasico datosEmail = new EmailBasico();
    			List<entradasCompleta> auxlista = new ArrayList<entradasCompleta>();
    			auxlista.add(entrada);
    			datosEmail.setNombreUsuario(entrada.getUsuario().getNombre());
    			datosEmail.setMailReceptor(entrada.getUsuario().getEmail());
    			correo.enviarEmail(auxlista, datosEmail);
    		}
    	}
    
    }

    private static void hacerEntrada (entradasCompleta entrada,Evento evento , File pdfTemplate, File entradaCompletaPDF ) throws IOException, DocumentException {

       
    	try (
                FileInputStream is = new FileInputStream(pdfTemplate);
                FileOutputStream os = new FileOutputStream(entradaCompletaPDF)
            ) {
                logger.info ( "open the PDF template" );
                PdfReader pdfReader = new PdfReader(is);
                PdfStamper stamper = new PdfStamper(pdfReader, os);

                logger.info ( "convert the Car-Pass POJO in to a Key-Value pair Map" );
                Map<String, String> model = BeanUtils.recursiveDescribe ( entrada, "entrada" );
                Map<String, String> model2 = BeanUtils.recursiveDescribe ( evento, "evento" );
                
                logger.info ( "get the fields from the PDF template" );
                AcroFields fields = stamper.getAcroFields();

                logger.info ( "set all the Key-Value pairs of the Car-Pass POJO on the AcroField object" );
                for ( Map.Entry<String, String> entry : model.entrySet() ) {
                    fields.setField ( entry.getKey(), entry.getValue() );
                }
                
                for ( Map.Entry<String, String> entry : model2.entrySet() ) {
                    fields.setField ( entry.getKey(), entry.getValue() );
                }

                logger.info ( "create QR code" );
               // BarcodeQRCode qrcode = new BarcodeQRCode("https://ws.car-pass.be/verify/" + carPass.getCertificateNumber(), 75, 75, null);

                logger.info ( "get the PDF content" );
                PdfContentByte content = stamper.getOverContent(pdfReader.getNumberOfPages());

                logger.info("insert the QR code as an image on the PDF");
                //Image image = qrcode.getImage();
                //image.setAbsolutePosition(505f, 735f);
                //content.addImage(image);

                logger.info ( "flatten the PDF form" );
                stamper.setFormFlattening(true);

                logger.info ( "enable full compression" );
                stamper.setFullCompression();

                logger.info("remove all embedded fonts");
                PDFUtil.removeEmbeddedFonts(pdfReader, stamper);

                logger.info ( "close the PDF file" );
                stamper.close();
                pdfReader.close();
                
           
    	}
    }

}
