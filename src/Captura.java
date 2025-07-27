import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;

public class Captura {
    public static BufferedImage capturarDaAreaDeTransferencia() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                Image imagem = (Image) clipboard.getData(DataFlavor.imageFlavor);

                // Converter para BufferedImage
                BufferedImage bufferedImage = new BufferedImage(
                        imagem.getWidth(null),
                        imagem.getHeight(null),
                        BufferedImage.TYPE_INT_RGB);

                Graphics g = bufferedImage.getGraphics();
                g.drawImage(imagem, 0, 0, null);
                g.dispose();

                return bufferedImage;
            } else {
                // System.out.println("Nenhuma imagem disponível na área de transferência.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao acessar a área de transferência: " + e.getMessage());
        }

        return null;
    }
}
