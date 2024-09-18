import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ProyectoParcial1MendozaReyesAngelEmanuel extends JFrame {
    private String titulo;
    private String[] nombresX;
    private String[] valoresY;
    private JPanel panelPrincipal;

    public ProyectoParcial1MendozaReyesAngelEmanuel() {
        setTitle("ProyectoParcial1 - Mendoza Reyes Angel Emanuel");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            BufferedImage imagenFondo = ImageIO.read(new File("fondo.jpg"));
            setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            });

            BufferedImage imagenIcono = ImageIO.read(new File("icono.png"));
            setIconImage(imagenIcono);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setLayout(new GridLayout(1, 3));

        JMenuItem menuItemAgregar = new JMenuItem();
        menuItemAgregar.setIcon(new ImageIcon(redimensionarImagen("agregar.png", 30, 30)));
        menuItemAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionAgregar();
            }
        });
        barraMenu.add(menuItemAgregar);

        JMenuItem menuItemTabla = new JMenuItem();
        menuItemTabla.setIcon(new ImageIcon(redimensionarImagen("tabla.png", 30, 30)));
        menuItemTabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionTabla();
            }
        });
        barraMenu.add(menuItemTabla);

        JMenu menuGraficas = new JMenu();
        menuGraficas.setIcon(new ImageIcon(redimensionarImagen("graficas.png", 30, 30)));

        JMenuItem itemBarra = new JMenuItem();
        itemBarra.setIcon(new ImageIcon(redimensionarImagen("grafica-barra.png", 30, 30)));
        itemBarra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionGraficaBarra();
            }
        });
        menuGraficas.add(itemBarra);

        JMenuItem itemPastel = new JMenuItem();
        itemPastel.setIcon(new ImageIcon(redimensionarImagen("grafica-pastel.png", 30, 30)));
        itemPastel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accionGraficaPastel();
            }
        });
        menuGraficas.add(itemPastel);

        barraMenu.add(menuGraficas);
        setJMenuBar(barraMenu);

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setOpaque(false);
        add(panelPrincipal);

        setVisible(true);
    }

    private void accionAgregar() {
        titulo = JOptionPane.showInputDialog(this, "Ingrese el título o nombre:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidadRegistros = 0;
        while (cantidadRegistros < 3 || cantidadRegistros > 5) {
            String registrosStr = JOptionPane.showInputDialog(this, "Ingrese el número de registros (mínimo 3, máximo 5):");
            if (registrosStr == null) {
                return;
            }
            try {
                cantidadRegistros = Integer.parseInt(registrosStr);
                if (cantidadRegistros < 3 || cantidadRegistros > 5) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar entre 3 y 5 registros.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        nombresX = new String[cantidadRegistros];
        valoresY = new String[cantidadRegistros];
        for (int i = 0; i < cantidadRegistros; i++) {
            nombresX[i] = JOptionPane.showInputDialog(this, "Ingrese el nombre para el registro " + (i + 1) + ":");
            if (nombresX[i] == null || nombresX[i].trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                i--;
                continue;
            }

            valoresY[i] = JOptionPane.showInputDialog(this, "Ingrese la cantidad para el registro " + (i + 1) + ":");
            if (valoresY[i] == null || valoresY[i].trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede estar vacía.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                i--;
            }
        }

        System.out.println("Título: " + titulo);
        for (int i = 0; i < cantidadRegistros; i++) {
            System.out.println("Registro " + (i + 1) + ": Nombre = " + nombresX[i] + ", Cantidad = " + valoresY[i]);
        }
    }

    private void accionTabla() {
        if (nombresX == null || valoresY == null || nombresX.length == 0 || valoresY.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos registrados. Por favor, agregue datos primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] nombresColumnas = {"Nombre", "Cantidad"};
        String[][] datos = new String[nombresX.length][2];
        for (int i = 0; i < nombresX.length; i++) {
            datos[i][0] = nombresX[i];
            datos[i][1] = valoresY[i];
        }

        JTable tabla = new JTable(datos, nombresColumnas);
        tabla.setOpaque(false);
        ((DefaultTableCellRenderer) tabla.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel etiquetaTitulo = new JLabel(titulo, SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(etiquetaTitulo, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.removeAll();
        panelPrincipal.add(panel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private void accionGraficaBarra() {
        if (nombresX == null || valoresY == null || nombresX.length == 0 || valoresY.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos registrados. Por favor, agregue datos primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel etiquetaTitulo = new JLabel(titulo, SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(etiquetaTitulo, BorderLayout.NORTH);
        panel.add(new PanelGraficaBarra(nombresX, valoresY), BorderLayout.CENTER);

        panelPrincipal.removeAll();
        panelPrincipal.add(panel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private void accionGraficaPastel() {
        if (nombresX == null || valoresY == null || nombresX.length == 0 || valoresY.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos registrados. Por favor, agregue datos primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel etiquetaTitulo = new JLabel(titulo, SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(etiquetaTitulo, BorderLayout.NORTH);
        panel.add(new PanelGraficaPastel(nombresX, valoresY), BorderLayout.CENTER);

        panelPrincipal.removeAll();
        panelPrincipal.add(panel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }

    private Image redimensionarImagen(String rutaArchivo, int ancho, int alto) {
        try {
            BufferedImage imagenOriginal = ImageIO.read(new File(rutaArchivo));
            Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return imagenRedimensionada;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProyectoParcial1MendozaReyesAngelEmanuel());
    }
}

class PanelGraficaBarra extends JPanel {
    private String[] nombresX;
    private String[] valoresY;

    public PanelGraficaBarra(String[] nombresX, String[] valoresY) {
        this.nombresX = nombresX;
        this.valoresY = valoresY;
        setPreferredSize(new Dimension(800, 600));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nombresX == null || valoresY == null) return;

        int ancho = getWidth();
        int alto = getHeight();
        int padding = 50;
        int paddingEtiquetas = 50;
        int anchoBarra = (ancho - 2 * padding) / (nombresX.length * 2);
        int alturaMaximaBarra = alto - 2 * padding - paddingEtiquetas;

        double valorMaximo = 0;
        for (String valor : valoresY) {
            valorMaximo = Math.max(valorMaximo, Double.parseDouble(valor));
        }

        Color[] colores = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.ORANGE};
        TexturePaint[] texturas = {
                crearTextura(Color.RED),
                crearTextura(Color.GREEN),
                crearTextura(Color.YELLOW),
                crearTextura(Color.BLUE),
                crearTextura(Color.ORANGE)
        };

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < nombresX.length; i++) {
            int alturaBarra = (int) ((Double.parseDouble(valoresY[i]) / valorMaximo) * (alturaMaximaBarra * 0.5));
            int x = padding + i * anchoBarra * 2;
            int y = alto - padding - alturaBarra - paddingEtiquetas - 80;

            g2d.setPaint(texturas[i % texturas.length]);
            g2d.fillRect(x, y, anchoBarra - 5, alturaBarra);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, anchoBarra - 5, alturaBarra);

            g2d.drawString(nombresX[i], x + (anchoBarra - 5) / 2 - g2d.getFontMetrics().stringWidth(nombresX[i]) / 2, y + alturaBarra + 15);
            g2d.drawString(valoresY[i], x + (anchoBarra - 5) / 2 - g2d.getFontMetrics().stringWidth(valoresY[i]) / 2, y - 5);
        }
    }

    private TexturePaint crearTextura(Color color) {
        BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, 10, 10);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(0, 0, 10, 10);
        g2d.drawLine(10, 0, 0, 10);
        return new TexturePaint(bi, new Rectangle(10, 10));
    }
}

class PanelGraficaPastel extends JPanel {
    private String[] nombresX;
    private String[] valoresY;

    public PanelGraficaPastel(String[] nombresX, String[] valoresY) {
        this.nombresX = nombresX;
        this.valoresY = valoresY;
        setPreferredSize(new Dimension(800, 600));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (nombresX == null || valoresY == null) return;

        int ancho = getWidth();
        int alto = getHeight();
        int padding = 50;
        int paddingEtiquetas = 50;
        int diametro = Math.min(ancho, alto) - 2 * padding - 100;

        double valorTotal = 0;
        for (String valor : valoresY) {
            valorTotal += Double.parseDouble(valor);
        }

        Color[] colores = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.ORANGE};
        GradientPaint[] degradados = {
            new GradientPaint(0, 0, colores[0], ancho, alto, colores[4]),
            new GradientPaint(0, 0, colores[4], ancho, alto, colores[2]),
            new GradientPaint(0, 0, colores[2], ancho, alto, colores[1]),
            new GradientPaint(0, 0, colores[1], ancho, alto, colores[3]),
            new GradientPaint(0, 0, colores[3], ancho, alto, colores[0]),
        };

        Graphics2D g2d = (Graphics2D) g;
        int anguloInicio = 0;
        for (int i = 0; i < nombresX.length; i++) {
            double valor = Double.parseDouble(valoresY[i]);
            int anguloArco = (int) Math.round((valor / valorTotal) * 360);

            g2d.setPaint(degradados[i % degradados.length]);
            g2d.fillArc(padding, padding, diametro, diametro, anguloInicio, anguloArco);

            g2d.setColor(Color.BLACK);
            g2d.drawArc(padding, padding, diametro, diametro, anguloInicio, anguloArco);

            double angulo = Math.toRadians(anguloInicio + anguloArco / 2.0);
            int etiquetaX = (int) (padding + diametro / 2 + (diametro / 4) * Math.cos(angulo));
            int etiquetaY = (int) (padding + diametro / 2 - (diametro / 4) * Math.sin(angulo));
            g2d.drawString(valoresY[i], etiquetaX - g2d.getFontMetrics().stringWidth(valoresY[i]) / 2, etiquetaY);

            int leyendaX = padding + diametro + 20;
            int leyendaY = padding + i * 30;
            g2d.setPaint(degradados[i % degradados.length]);
            g2d.fillRect(leyendaX, leyendaY, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(leyendaX, leyendaY, 20, 20);
            g2d.drawString(nombresX[i], leyendaX + 30, leyendaY + 15);

            anguloInicio += anguloArco;
        }
    }
}