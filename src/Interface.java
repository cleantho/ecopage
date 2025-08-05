import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterException;
import java.util.ArrayList;

public class Interface {
    private final ArrayList<BufferedImage> imagens = new ArrayList<>();
    private final ArrayList<BufferedImage> backupImagens = new ArrayList<>();
    private final PainelPagina painelImagens = new PainelPagina();
    private final JScrollPane scroll;
    private ImageIcon icone;
    private boolean pdf = false;

    private static final String IMPRIMIR = "Imprimir...";
    private static final String IMPRIMIR_PDF = "Imprimir PDF...";
    private static final String SAIR = "Sair";
    private static final String DESFAZER = "Desfazer";
    private static final String REFAZER = "Refazer";
    private static final String INSERIR = "Inserir";
    private static final String LIMPAR = "Limpar";
    private static final String SOBRE = "Sobre...";

    public Interface() {
        scroll = new JScrollPane(painelImagens);
        icone = new ImageIcon(getClass().getResource("resources/ecopage32.png"));
    }

    public void criarInterface() {
        JFrame frame = new JFrame("EcoPage");
        frame.setSize(800, 600); // Tamanho inicial
        frame.setMinimumSize(new Dimension(400, 700)); // Tamanho mínimo
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icone.getImage());

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");

        JButton btnInserir = new JButton(INSERIR);
        btnInserir.setMnemonic(KeyEvent.VK_I);
        JButton btnLimpar = new JButton(LIMPAR);
        btnLimpar.setMnemonic(KeyEvent.VK_L);
        JButton btnImprimir = new JButton(IMPRIMIR);
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

        JMenuItem itemImprimir = new JMenuItem(IMPRIMIR);
        itemImprimir.setMnemonic(KeyEvent.VK_P);
        itemImprimir.setAccelerator(KeyStroke.getKeyStroke("control P")); // Ctrl+P
        itemImprimir.setIcon(new ImageIcon(getClass().getResource("resources/imprimir32.png")));
        JMenuItem itemImprimirPDF = new JMenuItem(IMPRIMIR_PDF);
        itemImprimirPDF.setMnemonic(KeyEvent.VK_F);
        itemImprimirPDF.setAccelerator(KeyStroke.getKeyStroke("control F")); // Ctrl+F
        itemImprimirPDF.setIcon(new ImageIcon(getClass().getResource("resources/pdf32.png")));
        JMenuItem itemSair = new JMenuItem(SAIR);
        itemSair.setIcon(new ImageIcon(getClass().getResource("resources/none32.png")));
        itemSair.setMnemonic(KeyEvent.VK_S);
        JMenuItem itemDesfazer = new JMenuItem(DESFAZER);
        itemDesfazer.setMnemonic(KeyEvent.VK_Z);
        itemDesfazer.setAccelerator(KeyStroke.getKeyStroke("control Z")); // Ctrl+Z
        itemDesfazer.setIcon(new ImageIcon(getClass().getResource("resources/desfazer32.png")));
        itemDesfazer.setEnabled(false);
        JMenuItem itemRefazer = new JMenuItem(REFAZER);
        itemRefazer.setMnemonic(KeyEvent.VK_R);
        itemRefazer.setAccelerator(KeyStroke.getKeyStroke("control Y")); // Ctrl+Y
        itemRefazer.setIcon(new ImageIcon(getClass().getResource("resources/refazer32.png")));
        itemRefazer.setEnabled(false);
        JMenuItem itemInserir = new JMenuItem(INSERIR);
        itemInserir.setMnemonic(KeyEvent.VK_I);
        itemInserir.setAccelerator(KeyStroke.getKeyStroke("control I")); // Ctrl+I
        itemInserir.setIcon(new ImageIcon(getClass().getResource("resources/inserir32.png")));
        JMenuItem itemLimpar = new JMenuItem(LIMPAR);
        itemLimpar.setMnemonic(KeyEvent.VK_L);
        itemLimpar.setAccelerator(KeyStroke.getKeyStroke("control L")); // Ctrl+L
        itemLimpar.setIcon(new ImageIcon(getClass().getResource("resources/limpar32.png")));
        JMenuItem itemSobre = new JMenuItem(SOBRE);
        itemSobre.setIcon(new ImageIcon(getClass().getResource("resources/none32.png")));
        itemSobre.setMnemonic(KeyEvent.VK_S); // Atalho Alt+S (opcional)

        // 🖱️ Barra de ícones (toolbar)
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false); // Impede que seja arrastado
        JButton btnDesfazer = new JButton(new ImageIcon(getClass().getResource("resources/desfazer32.png"))); // ícone
                                                                                                              // local
        btnDesfazer.setToolTipText("Desfazer");
        btnDesfazer.setEnabled(false);
        JButton btnRefazer = new JButton(new ImageIcon(getClass().getResource("resources/refazer32.png")));
        btnRefazer.setToolTipText("Refazer");
        btnRefazer.setEnabled(false);
        JButton btnInserir2 = new JButton(new ImageIcon(getClass().getResource("resources/inserir32.png")));
        btnInserir2.setToolTipText("Inserir");
        JButton btnLimpar2 = new JButton(new ImageIcon(getClass().getResource("resources/limpar32.png")));
        btnLimpar2.setToolTipText("Limpar");
        JButton btnImprimir2 = new JButton(new ImageIcon(getClass().getResource("resources/imprimir32.png")));
        btnImprimir2.setToolTipText("Imprimir...");
        JButton btnImprimirPDF = new JButton(new ImageIcon(getClass().getResource("resources/pdf32.png")));
        btnImprimirPDF.setToolTipText("Imprimir para PDF...");

        toolBar.add(btnDesfazer);
        toolBar.add(btnRefazer);
        toolBar.add(btnInserir2);
        toolBar.add(btnLimpar2);
        toolBar.add(btnImprimir2);
        toolBar.add(btnImprimirPDF);
        // 🧩 Layout principal
        frame.setLayout(new BorderLayout());
        frame.add(toolBar, BorderLayout.NORTH); // abaixo da barra de menu

        itemSair.addActionListener(e -> {
            System.exit(0);
        });
        itemDesfazer.addActionListener(e -> {
            if (imagens.size() > 0) {
                backupImagens.add(imagens.removeLast());
                painelImagens.removerImagem();
                itemRefazer.setEnabled(true);
                btnRefazer.setEnabled(true);
            }
            if (imagens.size() < 1) {
                itemDesfazer.setEnabled(false);
                btnDesfazer.setEnabled(false);
            }

        });
        itemRefazer.addActionListener(e -> {
            if (backupImagens.size() > 0) {
                imagens.add(backupImagens.removeLast());
                painelImagens.adicionarImagem(imagens.getLast());
                itemDesfazer.setEnabled(true);
                btnDesfazer.setEnabled(true);
            }
            if (backupImagens.size() < 1) {
                itemRefazer.setEnabled(false);
                btnRefazer.setEnabled(false);
            }
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
        itemImprimirPDF.addActionListener(e -> {
            pdf = true;
            btnImprimir.doClick();
            pdf = false;
        });
        itemSobre.addActionListener(e -> {
            mostrarSobre(frame);
        });

        btnDesfazer.addActionListener(e -> {
            itemDesfazer.doClick();
        });
        btnRefazer.addActionListener(e -> {
            itemRefazer.doClick();
        });
        btnInserir2.addActionListener(e -> {
            btnInserir.doClick();
        });
        btnLimpar2.addActionListener(e -> {
            btnLimpar.doClick();
        });
        btnImprimir2.addActionListener(e -> {
            itemImprimir.doClick();
        });
        btnImprimirPDF.addActionListener(e -> {
            itemImprimirPDF.doClick();
        });

        menuArquivo.add(itemImprimir);
        menuArquivo.add(itemImprimirPDF);
        menuArquivo.add(itemSair);
        menuEditar.add(itemDesfazer);
        menuEditar.add(itemRefazer);
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
            try {
                BufferedImage imagem = Captura.capturarDaAreaDeTransferencia();
                if (imagem != null) {
                    if (imagens.size() < 2) {
                        imagens.add(imagem);
                        painelImagens.adicionarImagem(imagem);
                        itemRefazer.setEnabled(false);
                        itemDesfazer.setEnabled(true);
                        btnRefazer.setEnabled(false);
                        btnDesfazer.setEnabled(true);
                        if (backupImagens.size() > 0) {
                            backupImagens.removeLast();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Já existem duas imagens inseridas.", " Aviso",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhuma imagem disponível na área de transferência.", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception error) {
                error.printStackTrace();
                String message = "Erro ao acessar a área de transferência: " + error.getMessage();
                JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpar.addActionListener(e -> {
            if (imagens.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nenhuma ação é necessária para limpar.", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            itemDesfazer.doClick();
            itemDesfazer.doClick();
            imagens.clear();
            painelImagens.limpar();
        });

        btnImprimir.addActionListener(e -> {
            if (imagens.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nenhuma imagem para imprimir.", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (imagens.size() < 2) {
                JOptionPane.showMessageDialog(frame, "São necessárias duas imagens para imprimir.", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Impressao impressao = new Impressao(imagens);
            try {
                if (pdf) {
                    impressao.imprimir(frame, true);
                } else {
                    impressao.imprimir(frame, false);
                }
                int resposta = JOptionPane.showConfirmDialog(
                        frame,
                        "Impressão realizada com sucesso!!!\nDeseja limpar?\n\n",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    btnLimpar.doClick();
                }
            } catch (PrinterAbortException err) {
                frame.setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(frame, "Impressão suspensa.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException err) {
                frame.setCursor(Cursor.getDefaultCursor());
                JOptionPane.showMessageDialog(frame, err.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
                <p><b>Versão:</b> 2.3.19</p>
                <p><b>Atualizado em:</b> 04/08/2025</p>
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
