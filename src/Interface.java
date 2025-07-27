import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.util.ArrayList;

public class Interface {
    private final ArrayList<BufferedImage> imagens = new ArrayList<>();
    private final JPanel painelImagens = new JPanel(new GridLayout(0, 1));
    private final JScrollPane scroll;
    private ImageIcon icone;

    public Interface() {
        scroll = new JScrollPane(painelImagens);        
        icone = new ImageIcon(getClass().getResource("resources/ecopage32.png"));        
    }

    public void criarInterface() {
        JFrame frame = new JFrame("EcoPage");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icone.getImage());

        JButton btnInserir = new JButton("Inserir");
        btnInserir.setMnemonic(KeyEvent.VK_I);
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setMnemonic(KeyEvent.VK_L);
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setMnemonic(KeyEvent.VK_P);

        JPanel botoes = new JPanel();
        botoes.add(btnInserir);
        botoes.add(btnLimpar);
        botoes.add(btnImprimir);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);
        JMenu menuEditar = new JMenu("Editar");
        menuEditar.setMnemonic(KeyEvent.VK_E);
        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.setMnemonic(KeyEvent.VK_J);

        JMenuItem itemImprimir = new JMenuItem("Imprimir");
        itemImprimir.setMnemonic(KeyEvent.VK_P);
        itemImprimir.setAccelerator(KeyStroke.getKeyStroke("control P")); // Ctrl+P
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.setMnemonic(KeyEvent.VK_S);
        JMenuItem itemInserir = new JMenuItem("Inserir");
        itemInserir.setMnemonic(KeyEvent.VK_I);
        itemInserir.setAccelerator(KeyStroke.getKeyStroke("control I")); // Ctrl+I
        JMenuItem itemLimpar = new JMenuItem("Limpar");
        itemLimpar.setMnemonic(KeyEvent.VK_L);
        itemLimpar.setAccelerator(KeyStroke.getKeyStroke("control L")); // Ctrl+L
        JMenuItem itemSobre = new JMenuItem("Sobre...");
        itemSobre.setMnemonic(KeyEvent.VK_S); // Atalho Alt+S (opcional)

        itemSair.addActionListener(e -> {
            System.exit(0);
        });
        itemInserir.addActionListener(e -> {
            btnInserir.doClick();
        });
        itemLimpar.addActionListener(e -> {
            btnLimpar.doClick();
        });
        itemImprimir.addActionListener(e -> {
            btnImprimir.doClick();
        });
        itemSobre.addActionListener(e -> {
            mostrarSobre(frame);
        });

        menuArquivo.add(itemImprimir);
        menuArquivo.add(itemSair);
        menuEditar.add(itemInserir);
        menuEditar.add(itemLimpar);
        menuAjuda.add(itemSobre);
        barraMenu.add(menuArquivo);
        barraMenu.add(menuEditar);
        barraMenu.add(menuAjuda);
        frame.setJMenuBar(barraMenu); // Adiciona a barra ao seu JFrame

        frame.getContentPane().add(scroll, BorderLayout.CENTER);
        frame.getContentPane().add(botoes, BorderLayout.SOUTH);
        frame.setVisible(true);

        btnInserir.addActionListener(e -> {
            BufferedImage imagem = Captura.capturarDaAreaDeTransferencia();
            if (imagem != null) {
                if (imagens.size() < 2) {
                    imagens.add(imagem);
                    JLabel label = new JLabel(new ImageIcon(imagem));
                    painelImagens.add(label);
                    painelImagens.revalidate();
                } else {
                    JOptionPane.showMessageDialog(frame, "Já existem duas imagens inseridas.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Nenhuma imagem disponível na área de transferência.");
            }
        });

        btnLimpar.addActionListener(e -> {
            if (imagens.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nunhuma ação é necessária para limpar.");
                return;
            }
            imagens.clear();
            painelImagens.removeAll();
            painelImagens.revalidate();
            painelImagens.repaint();
        });

        btnImprimir.addActionListener(e -> {
            if (imagens.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nenhuma imagem para imprimir.");
                return;
            }
            if (imagens.size() < 2) {
                JOptionPane.showMessageDialog(frame, "São necessárias duas imagens para imprimir.");
                return;
            }

            Impressao impressao = new Impressao(imagens);
            try {
                impressao.imprimir();
                JOptionPane.showMessageDialog(frame, "Sucesso na Impressão.");
            } catch (PrinterAbortException err) {
                JOptionPane.showMessageDialog(frame, "Impressão suspensa.");
            } catch (PrinterException err) {
                err.printStackTrace();
                JOptionPane.showMessageDialog(frame, err.getMessage());
            }
        });

    }

    private void mostrarSobre(JFrame frame) {
        // Painel personalizado
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel linhaSuperior = new JPanel(new GridLayout(1, 2, 0, 10));
        linhaSuperior.setBackground(Color.WHITE);

        // Ícone carregado
        JLabel imagemLabel = new JLabel(icone, JLabel.RIGHT);

        // Título da aplicação
        JLabel tituloLabel = new JLabel("EcoPage", JLabel.LEFT);
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        // Adiciona componentes à grade
        linhaSuperior.add(imagemLabel); // Linha 0, Coluna 0
        linhaSuperior.add(tituloLabel); // Linha 0, Coluna 1

        // Texto formatado
        String textoHtml = """
                <html>
                <p>🖨️ Aplicação de Captura e Impressão</p>
                <p><b>Autor:</b> Cleantho B. Fonseca</p>
                <p><b>Versão:</b> 1.0.0</p>
                <p><b>Atualizado em:</b> 20/07/2025</p>
                <br>
                <p>Esta ferramenta permite capturar imagens da área de<br> transferência,
                    organizar visualmente e imprimir com layout A4 padronizado.</p>
                </html>
                """;
        JLabel textoLabel = new JLabel(textoHtml);

        // (coluna 0, linha 0)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 5, 20);
        gbc.ipadx = 10;

        painel.add(linhaSuperior, gbc); // Linha 0, Coluna 0

        // Texto geral (coluna 0, linha 1)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 20, 10, 20);
        painel.add(textoLabel, gbc); // Linha 1, Coluna 0

        // Caixa de diálogo
        JOptionPane.showMessageDialog(frame, painel, "Sobre", JOptionPane.PLAIN_MESSAGE);
    }

}
