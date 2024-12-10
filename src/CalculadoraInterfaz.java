import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraInterfaz extends JFrame implements ActionListener, KeyListener, WindowListener {
    private JLabel pantallaResultado;
    private JLabel pantallaAlmacenada;
    private StringBuilder entrada;
    private String modoEntrada;
    private CalculadoraLogica logica;

    public CalculadoraInterfaz() {
        logica = new CalculadoraLogica();
        entrada = new StringBuilder();
        modoEntrada = "Libre";

        setTitle("Calculadora de Natalia Rey Loroño");

        // Configuración de ventana
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = screenSize.width / 2;
        int frameHeight = 600;
        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pantalla de resultado
        pantallaResultado = new JLabel("0", SwingConstants.RIGHT);
        pantallaResultado.setFont(new Font("Arial", Font.PLAIN, 48));
        pantallaResultado.setOpaque(true);
        pantallaResultado.setBackground(Color.BLUE);
        pantallaResultado.setForeground(Color.WHITE);
        pantallaResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pantallaResultado, BorderLayout.NORTH);

        // Pantalla de valor almacenado
        pantallaAlmacenada = new JLabel("Valor Almacenado: 0", SwingConstants.RIGHT);
        pantallaAlmacenada.setFont(new Font("Arial", Font.PLAIN, 16));
        pantallaAlmacenada.setOpaque(true);
        pantallaAlmacenada.setBackground(Color.ORANGE);
        pantallaAlmacenada.setForeground(Color.BLACK);
        pantallaAlmacenada.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(pantallaAlmacenada, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelNumeros = crearPanelNumeros();
        JPanel panelOperaciones = crearPanelOperaciones();

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelNumeros, BorderLayout.CENTER);
        panelInferior.add(panelOperaciones, BorderLayout.EAST);
        add(panelInferior, BorderLayout.SOUTH);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private JPanel crearPanelNumeros() {
        JPanel panelNumeros = new JPanel(new GridLayout(4, 3, 20, 20));
        panelNumeros.setBackground(Color.ORANGE);
        panelNumeros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] botonesNumeros = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "." };
        for (String texto : botonesNumeros) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 24));
            boton.setBackground(Color.BLUE);
            boton.setForeground(Color.WHITE);
            boton.setBorder(new LineBorder(Color.WHITE, 4));
            boton.addActionListener(this);
            panelNumeros.add(boton);
        }
        return panelNumeros;
    }

    private JPanel crearPanelOperaciones() {
        JPanel panelOperaciones = new JPanel(new GridLayout(2, 3, 20, 20));
        panelOperaciones.setBackground(Color.ORANGE);
        panelOperaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] botonesOperaciones = { "+", "-", "*", "/", "C", "=" };
        for (String texto : botonesOperaciones) {
            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 24));
            boton.setBackground(Color.BLUE);
            boton.setForeground(Color.WHITE);
            boton.setBorder(new LineBorder(Color.WHITE, 4));
            boton.addActionListener(this);
            panelOperaciones.add(boton);
        }
        return panelOperaciones;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = e.getActionCommand();
        procesarEntrada(input);
    }

    private void procesarEntrada(String input) {
        try {
            switch (input) {
                case "+":
                case "-":
                case "*":
                case "/":
                    logica.setOperando1(Double.parseDouble(pantallaResultado.getText()));
                    logica.setOperacion(input);
                    pantallaAlmacenada
                            .setText("Valor Almacenado: " + pantallaResultado.getText() + " | Modo: " + modoEntrada);
                    entrada.setLength(0);
                    break;
                case "=":
                    try {
                        double resultado = logica.realizarOperacion(Double.parseDouble(pantallaResultado.getText()));
                        pantallaResultado.setText(String.valueOf(resultado));
                        colorResultado(resultado);
                    } catch (ArithmeticException ex) {
                        pantallaResultado.setText("Error: División por 0");
                        pantallaResultado.setForeground(Color.RED);
                    }
                    entrada.setLength(0);
                    break;
                case "C":
                    pantallaResultado.setText("0");
                    pantallaResultado.setForeground(Color.WHITE);
                    pantallaAlmacenada.setText("Valor Almacenado: 0 | Modo: " + modoEntrada);
                    entrada.setLength(0);
                    break;
                default:
                    entrada.append(input);
                    pantallaResultado.setText(entrada.toString());
                    break;
            }
        } catch (NumberFormatException ex) {
            pantallaResultado.setText("Error");
        }
    }

    private void colorResultado(double valor) {
        pantallaResultado.setForeground(valor < 0 ? Color.RED : Color.WHITE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_NUMPAD7:
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_NUMPAD9:
                procesarEntrada(String.valueOf(keyCode - KeyEvent.VK_NUMPAD0));
                break;
            case KeyEvent.VK_COMMA:
            case KeyEvent.VK_DECIMAL:
                procesarEntrada(",");
                break;
            case KeyEvent.VK_ADD:
                procesarEntrada("+");
                break;
            case KeyEvent.VK_SUBTRACT:
                procesarEntrada("-");
                break;
            case KeyEvent.VK_MULTIPLY:
                procesarEntrada("*");
                break;
            case KeyEvent.VK_DIVIDE:
                procesarEntrada("/");
                break;
            case KeyEvent.VK_ENTER:
                procesarEntrada("=");
                break;
            case KeyEvent.VK_BACK_SPACE:
                procesarEntrada("C");
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
        pantallaResultado.setText("0");
        entrada.setLength(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraInterfaz calculadora = new CalculadoraInterfaz();
            calculadora.setVisible(true);
        });
    }
}
