package org.dinamizadores.dinaeventos.utiles.pdf;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.util.HashSet;

/**
 * Code provided from iText support.  Do use with care.
 */
public class PDFUtil {

    public static void removeEmbeddedFonts(PdfReader reader, PdfStamper stamper) throws DocumentException, IOException {

        // Compressed object and xref streams
        stamper.setFullCompression();

        // Removing fonts from AcroForm default resources, /DR

        PdfDictionary drdict = reader.getCatalog().getAsDict(PdfName.ACROFORM).getAsDict(PdfName.DR);
        PdfDictionary drfontdict = drdict.getAsDict(PdfName.FONT);

        for (PdfName key : new HashSet<PdfName>(drfontdict.getKeys())) {
            System.out.println("Removing entry from /DR: " + key);
            drfontdict.remove(key);
        }

        // Removing all unused objects, i.e. objects that cannot be reached from the root.
        System.out.println("Unused objects removed: " + reader.removeUnusedObjects());

        // Replace /FreeTextTypewriter with /FreeTextTypeWriter

        PdfArray pages = reader.getCatalog().getAsDict(PdfName.PAGES).getAsArray(PdfName.KIDS);
        for (int i = 0; i < pages.size(); i++) {
            PdfDictionary page = pages.getAsDict(i);
            PdfArray annots = page.getAsArray(PdfName.ANNOTS);
            for (int j = 0; j < annots.size(); j++) {
                PdfDictionary annot = annots.getAsDict(j);
                PdfName itvalue = annot.getAsName(new PdfName("IT"));
                if (new PdfName("FreeTextTypewriter").equals(itvalue)) {
                    annot.put(new PdfName("IT"), new PdfName("FreeTextTypeWriter"));
                }
            }
        }

    }

}
