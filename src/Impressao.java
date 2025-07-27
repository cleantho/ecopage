import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;

import java.util.List;

public class Impressao implements Printable {
    private List<BufferedImage> imagens;

    public Impressao(List<BufferedImage> imagens) {
        this.imagens = imagens;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0 || imagens.size() < 2)
            return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Margens
        int margemTopo = 42;
        int margemLateral = 42;
        int margemInferior = 42;

        // Dimensões da página A4 = 595 x 842
        double larguraUtil = 595 - 2 * margemLateral;
        double alturaUtil = 842 - margemTopo - margemInferior;
        double alturaImagem = alturaUtil / 2;

        // Posiciona imagens
        for (int i = 0; i < 2; i++) {
            BufferedImage img = imagens.get(i);

            double escalaX = larguraUtil / img.getWidth();
            double escalaY = alturaImagem / img.getHeight();
            double escala = Math.min(escalaX, escalaY);

            int novaLargura = (int) (img.getWidth() * escala);
            int novaAltura = (int) (img.getHeight() * escala);

            // Centraliza no eixo vertical
            // int posX = (int) margemLateral + (int) ((larguraUtil - novaLargura) / 2);
            int posX = margemLateral;
            int posY = margemTopo + i * (int) alturaImagem + (int) ((alturaImagem - novaAltura) / 2);

            g2d.drawImage(img, posX, posY, novaLargura, novaAltura, null);
        }

        return PAGE_EXISTS;
    }

    public void imprimir() throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);

        if (job.printDialog()) {
            job.print();
        } else {
            throw new PrinterAbortException();
        }
    }
}

