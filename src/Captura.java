import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;

public class Captura {
    public static BufferedImage capturarDaAreaDeTransferencia() throws Exception {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            Image imagem = (Image) clipboard.getData(DataFlavor.imageFlavor);

            // Converter para BufferedImage
            BufferedImage bufferedImage = new BufferedImage(
                    imagem.getWidth(null),
                    imagem.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = bufferedImage.createGraphics();

            // Usar renderização de alta qualidade
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(imagem, 0, 0, null);
            g2d.dispose();

            return bufferedImage;
        }
        return null;
    }
}
