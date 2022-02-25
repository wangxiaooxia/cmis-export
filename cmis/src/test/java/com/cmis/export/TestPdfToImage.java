package com.cmis.export;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class TestPdfToImage {
    public static void main(String[] args) throws Exception {
        pdfToImageFile();
    }

    static void pdfToImageFile() throws Exception {
        File file = new File("D://Users//hacker//Downloads//20211206140357218457.pdf");
        PDDocument doc = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(doc);

        try {
         int pageCount = doc.getNumberOfPages();
        for(int i=0;i<pageCount;i++){
        BufferedImage image = renderer.renderImageWithDPI(i, 296);
        //          BufferedImage image = renderer.renderImage(i, 2.5f);
        ImageIO.write(image, "PNG", new File("D:\\pdfbox_image.png"));
        }
        } catch (IOException e) {
        e.printStackTrace();
        }finally {
            doc.close();
        }
    }
}
