import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PainelPagina extends JPanel {
    private java.util.List<BufferedImage> imagens;
    private int margem = 42;
    private int larguraPagina;
    private int alturaPagina;
    private int margemLateral;
    private int margemInterna;

    public PainelPagina(int margemInterna) {
        this.margemInterna = margemInterna;
        this.imagens = new ArrayList<>();
        setBackground(Color.LIGHT_GRAY);
        setLayout(null); // posições absolutas
    }

    public void limpar() {
        imagens.clear();
        removeAll();
        revalidate();
        repaint();
    }

    public void removerImagem() {
        if (imagens.size() > 0)
            imagens.removeLast();
        removeAll();
        revalidate();
        repaint();
    }

    public void adicionarImagem(BufferedImage imagem) {
        if (imagens.size() >= 2)
            return;
        imagens.add(imagem);
        revalidate();
        repaint();
    }

    public static Image redimensionarComProporcao(BufferedImage imagem, int larguraPainel, int alturaPainel) {
        int larguraOriginal = imagem.getWidth();
        int alturaOriginal = imagem.getHeight();

        double proporcaoImagem = (double) larguraOriginal / alturaOriginal;
        double proporcaoPainel = (double) larguraPainel / alturaPainel;

        int novaLargura, novaAltura;

        if (proporcaoPainel > proporcaoImagem) {
            // painel mais largo que a proporção da imagem: baseia na altura
            novaAltura = alturaPainel;
            novaLargura = (int) (novaAltura * proporcaoImagem);
        } else {
            // painel mais estreito ou igual: baseia na largura
            novaLargura = larguraPainel;
            novaAltura = (int) (novaLargura / proporcaoImagem);
        }

        return imagem.getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (getWidth() > getHeight()) {
            alturaPagina = getHeight() - (2 * margem);
            larguraPagina = (int) (alturaPagina / 1.414);
            margemLateral = (getWidth() - larguraPagina) / 2;
        } else {
            larguraPagina = getWidth() - (2 * margem);
            alturaPagina = (int) (larguraPagina * 1.414);
            margemLateral = margem;
        }

        // Fundo da "folha"
        g2d.setColor(Color.WHITE);
        g2d.fillRect(margemLateral, margem, larguraPagina, alturaPagina);

        // Borda da página
        g2d.setColor(Color.GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(margemLateral, margem, larguraPagina, alturaPagina);

        // Marcas de margem (linhas pontilhadas)
        g2d.setColor(Color.BLACK);
        float[] dash = { 5f, 5f };
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));

        // Margem esquerda
        g2d.drawLine(margemLateral + margemInterna - 1, margem,
                margemLateral + margemInterna - 1, margem + alturaPagina);
        // Margem direita
        g2d.drawLine(margemLateral + larguraPagina - margemInterna + 1, margem,
                margemLateral + larguraPagina - margemInterna + 1, margem + alturaPagina);
        // Margem superior
        g2d.drawLine(margemLateral, margem + margemInterna - 1,
                margemLateral + larguraPagina, margem + margemInterna - 1);
        // Margem inferior
        g2d.drawLine(margemLateral, margem + alturaPagina - margemInterna + 1,
                margemLateral + larguraPagina, margem + alturaPagina - margemInterna + 1);

        if (imagens.size() >= 1) {
            removeAll();
            Image imageScale = redimensionarComProporcao(imagens.get(0), larguraPagina - (margemInterna * 2),
                    (alturaPagina - 14 - (margemInterna * 2)) / 2);
            JLabel label = new JLabel(new ImageIcon(imageScale));

            int posY = margem + margemInterna
                    + (((alturaPagina / 2) - 7 - margemInterna) - imageScale.getHeight(label)) / 2;

            label.setBounds(margemLateral + margemInterna, posY, imageScale.getWidth(label),
                    imageScale.getHeight(label)); // margens e espaçamento
            add(label);
            if (imagens.size() == 2) {
                imageScale = redimensionarComProporcao(imagens.get(1), larguraPagina - (margemInterna * 2),
                        (alturaPagina - 14 - (margemInterna * 2)) / 2);
                label = new JLabel(new ImageIcon(imageScale));
                posY = margem + (alturaPagina / 2)
                        + (((alturaPagina / 2) - 7 - margemInterna) - imageScale.getHeight(label)) / 2;
                label.setBounds(margemLateral + margemInterna, posY,
                        imageScale.getWidth(label),
                        imageScale.getHeight(label)); // margens e espaçamento
                add(label);
            }

        }

    }
}
