import java.awt.*;
import java.awt.print.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFrame;

public class Impressao implements Printable {
    private List<BufferedImage> imagens;
    // Margens
    private final int margem = 25;

    public Impressao(List<BufferedImage> imagens) {
        this.imagens = imagens;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0 || imagens.size() < 2)
            return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        g2d.scale(1.0, 1.0); // Escala 100%
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dimensões da página A4(210 x 297mm) = 595.3322834645669 x 841.9181102362205
        // pontos
        // Área disponivel para impressão A4(200 x 287mm) = 566 x 813 pontos
        // double scale = 2.83465; // conversão mm -> pt
        double larguraMaxImagem = 534;
        double alturaMaxImagem = 386;

        // Posiciona imagens
        for (int i = 0; i < 2; i++) {
            BufferedImage img = imagens.get(i);

            double escalaX = larguraMaxImagem / img.getWidth();
            double escalaY = alturaMaxImagem / img.getHeight();
            double escala = Math.min(escalaX, escalaY);

            int novaLargura = (int) (img.getWidth() * escala);
            int novaAltura = (int) (img.getHeight() * escala);

            // Centraliza no eixo vertical
            // int posX = (int) margem + (int) ((larguraMaxImagem - novaLargura) / 2);
            int posX = margem;
            int posY;
            if (i == 0) {
                posY = margem + (int) ((alturaMaxImagem - novaAltura) / 2);
            } else {
                posY = margem + (int) alturaMaxImagem + 15 + (int) ((alturaMaxImagem - novaAltura) / 2);
            }

            // Melhora a nitidez
            float[] softSharpKernel = {
                    0, -0.5f, 0,
                    -0.5f, 3, -0.5f,
                    0, -0.5f, 0
            };
            BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, softSharpKernel));

            g2d.drawImage(op.filter(img, null), posX, posY, novaLargura, novaAltura, null);
        }
        g2d.dispose();
        return PAGE_EXISTS;
    }

    public void imprimir(JFrame frame, boolean pdf) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        // Procurar pela impressora "Microsoft Print to PDF"
        if (pdf) {
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            for (PrintService service : services) {
                if (service.getName().toLowerCase().contains("pdf")) {                    
                        job.setPrintService(service);
                        break;                    
                }
            }
        }

        if (job.printDialog()) {
            Cursor originalCursor = frame.getCursor();
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            job.print();
            frame.setCursor(originalCursor);
        } else {
            throw new PrinterAbortException();
        }
    }
}
