package org.dinamizadores.dinaeventos.utiles.pdf;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

/**
 * Created by koen on 9-06-14.
 */
public class FormarPDF {

    private static Logger logger = Logger.getLogger ( FormarPDF.class.getName() );

    public static void main ( String[] args ) throws IOException, DocumentException {

        logger.info ( "fetch the Car-Pass POJO from a mocked database" );
        CarPass carPass = Repository.findOneCarPass();

        logger.info ( "fetch the Car-Pass PDF template from a mocked database" );
        File pdfTemplate = Repository.findCarPassPDFTemplate();

        String ruta = null;
        ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/templatesPdf/");
        logger.info ( "define the File to which the completed Car-Pass PDF template should be saved" );
        File completedCarPassPDF = new File ( "car-" + carPass.getVin() + ".pdf" );

        logger.info ( "start the rendering of the Car-Pass" );
        renderCarPass ( carPass, pdfTemplate, completedCarPassPDF );

    }

    private static void renderCarPass ( CarPass carPass, File pdfTemplate, File completedCarPassPDF ) throws IOException, DocumentException {

        try (
            FileInputStream is = new FileInputStream(pdfTemplate);
            FileOutputStream os = new FileOutputStream(completedCarPassPDF)
        ) {
            logger.info ( "open the PDF template" );
            PdfReader pdfReader = new PdfReader(is);
            PdfStamper stamper = new PdfStamper(pdfReader, os);

            logger.info ( "convert the Car-Pass POJO in to a Key-Value pair Map" );
            Map<String, String> model = BeanUtils.recursiveDescribe ( carPass, "car-pass" );

            logger.info ( "get the fields from the PDF template" );
            AcroFields fields = stamper.getAcroFields();

            logger.info ( "set all the Key-Value pairs of the Car-Pass POJO on the AcroField object" );
            for ( Map.Entry<String, String> entry : model.entrySet() ) {
                fields.setField ( entry.getKey(), entry.getValue() );
            }

            logger.info ( "create QR code" );
            BarcodeQRCode qrcode = new BarcodeQRCode("https://ws.car-pass.be/verify/" + carPass.getCertificateNumber(), 75, 75, null);

            logger.info ( "get the PDF content" );
            PdfContentByte content = stamper.getOverContent(pdfReader.getNumberOfPages());

            logger.info("insert the QR code as an image on the PDF");
            Image image = qrcode.getImage();
            image.setAbsolutePosition(505f, 735f);
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
