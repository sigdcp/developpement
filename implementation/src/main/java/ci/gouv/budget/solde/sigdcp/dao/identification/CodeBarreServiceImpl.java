package ci.gouv.budget.solde.sigdcp.dao.identification;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import ci.gouv.budget.solde.sigdcp.service.ServiceException;
import ci.gouv.budget.solde.sigdcp.service.identification.CodeBarreService;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

public class CodeBarreServiceImpl implements CodeBarreService {

	@Override
	public byte[] encoder(String value) {
		try{
			ByteMatrix matrix = createMatrix(value, ErrorCorrectionLevel.L);
			return createImage("png", matrix, 200);
		}catch(Exception exception){
			throw new ServiceException(exception);
		}
	}

	@Override
	public String decoder(byte[] codeBarre) {
		try{
			InputStream barCodeInputStream = new ByteArrayInputStream(codeBarre);
	    	BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
	    	LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
	    	BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	    	Reader reader = new MultiFormatReader();
	    	Result result = reader.decode(bitmap);
	    	return result.getText();
		}catch(Exception exception){
			if(exception.getMessage()!=null && !exception.getMessage().equals("com.google.zxing.NotFoundException"))
				throw new ServiceException(exception);
			return null;
		}
	}
	
    private ByteMatrix createMatrix(String data,ErrorCorrectionLevel level) throws WriterException {
    	QRCode qr = new QRCode();
        qr = Encoder.encode(data, level);
        final ByteMatrix matrix = qr.getMatrix();
        return matrix;
    }
    
    private byte[] createImage(String imageFormat, ByteMatrix matrix,int size) throws IOException  {
        /**
         * Java 2D Traitement de Area
         */
        Area a = new Area(); // les futurs modules
        Area module = new Area(new Rectangle2D.Float(0.05f, 0.05f, 0.9f, 0.9f));

        AffineTransform at = new AffineTransform(); // pour déplacer le module
        int width = matrix.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix.get(j, i) == 1) {
                    a.add(module); // on ajoute le module
                }
                at.setToTranslation(1, 0); // on décale vers la droite
                module.transform(at);
            }
            at.setToTranslation(-width, 1); // on saute une ligne on revient au
                                            // début
            module.transform(at);
        }

        // agrandissement de l'Area pour le remplissage de l'image
        double ratio = size / (double) width;
        // il faut respecter la Quietzone : 4 modules de bordures autour du QR
        // Code
        double adjustment = width / (double) (width + 8);
        ratio = ratio * adjustment;

        at.setToTranslation(4, 4); // à cause de la quietzone
        a.transform(at);

        at.setToScale(ratio, ratio); // on agrandit
        a.transform(at);

        /**
         * Java 2D Traitement l'image
         */
        BufferedImage im = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) im.getGraphics();
        /*
        Color couleur1 = new Color(0xFF0000);
        g.setPaint(couleur1);
        */
        
        Color couleur1 = new Color(0xFF6600);
        Color couleur2 = new Color(0x00CC33);
        // Debut et fin du gradient
        float[] fractions = { 0.0f, 1.0f }; 
        Color[] colors = { couleur1, couleur2 };
        g.setPaint(new RadialGradientPaint(size / 2, size / 2, size / 2, fractions, colors));

        g.setBackground(new Color(0xFFFFFF));
        g.clearRect(0, 0, size, size); // pour le fond blanc
        g.fill(a); // remplissage des modules

        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        ImageIO.write(im, imageFormat, baos);
        return baos.toByteArray();
        
       
    }

}
