import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Formulario extends JFrame implements ActionListener {
    JButton btnReset;
    JTextField txf;
    String acu = "";
    JMenuBar mnuPrincipal;
    JMenu mnuArchivo, mnuMovil, mnuOtros;
    JMenuItem mnuGrabar, mnuLeer, mnuReset, mnuSalir, mnuInformacion;

    private String[] teclado = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "0", "*" };
    JButton[] b = new JButton[12];
    Color colorbase, colorCambiado;

    String home = System.getProperty("user.home");
    File archivo = new File("/Telefono.txt");

    public Formulario() {
        super("Teléfono móvil");
        setLayout(null);

        int x = 10, y = 100;
        for (int i = 0; i < b.length; i++) {

            b[i] = new JButton(teclado[i]);
            b[i].setSize(100, 20);
            b[i].setLocation(x, y);
            b[i].addMouseMotionListener(new Raton());
            b[i].addMouseListener(new Raton());
            b[i].addKeyListener(new Teclado());
            b[i].addActionListener(this);
            this.add(b[i]);

            if ((i + 1) % 3 == 0) {
                x = 10;
                y += 40;
            } else {
                x += 210;
            }
        }

        txf = new JTextField();
        txf.setSize(520, 50);
        txf.setLocation(10, 10);
        txf.addActionListener(this);
        txf.addKeyListener(new Teclado());
        txf.setEditable(false);
        add(txf);

        btnReset = new JButton("Reset");
        btnReset.setSize(100, 20);
        btnReset.setLocation(220, 270);
        btnReset.addActionListener(this);
        add(btnReset);

        // Menú Archivo
        mnuGrabar = new JMenuItem("Grabar");
        mnuGrabar.setMnemonic('G');
        mnuGrabar.addActionListener(this);

        mnuLeer = new JMenuItem("Leer");
        mnuLeer.setMnemonic('L');
        mnuLeer.addActionListener(this);

        mnuArchivo = new JMenu("Archivo");
        mnuArchivo.add(mnuGrabar);
        mnuArchivo.add(mnuLeer);

        // Menú Móvil
        mnuReset = new JMenuItem("Reset");
        mnuReset.setMnemonic('R');
        mnuReset.addActionListener(this);

        mnuSalir = new JMenuItem("Salir");
        mnuSalir.setMnemonic('S');
        mnuSalir.addActionListener(this);

        mnuMovil = new JMenu("Móvil");
        mnuMovil.add(mnuReset);
        mnuMovil.addSeparator();
        mnuMovil.add(mnuSalir);

        // Menú Otros
        mnuInformacion = new JMenuItem("Información");
        mnuInformacion.setMnemonic('I');
        mnuInformacion.addActionListener(this);

        mnuOtros = new JMenu("Otros");
        mnuOtros.add(mnuInformacion);

        // Menú Principal

        mnuPrincipal = new JMenuBar();
        mnuPrincipal.add(mnuArchivo);
        mnuPrincipal.add(mnuMovil);
        mnuPrincipal.add(mnuOtros);
        this.setJMenuBar(mnuPrincipal);

    }

    private class Raton extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {

            if (((JButton) e.getSource()).getBackground() != colorCambiado) {
                ((JButton) e.getSource()).setBackground(Color.red);
                colorbase = ((JButton) e.getSource()).getBackground();

            }
        }

        @Override
        public void mouseExited(MouseEvent e) {

            if (((JButton) e.getSource()).getBackground() == colorbase) {
                ((JButton) e.getSource()).setBackground(null);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            ((JButton) e.getSource()).setBackground(Color.yellow);
            colorCambiado = ((JButton) e.getSource()).getBackground();
        }
    }

    private class Teclado extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() >= e.VK_0 && e.getKeyCode() <= e.VK_9) {
                acu += e.getKeyChar();

            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            txf.setText(acu);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().getClass() == JButton.class) {
            acu += e.getActionCommand();
            txf.setText(acu);

        }

        if (e.getSource() == mnuGrabar) {
            try (PrintWriter f = new PrintWriter(new FileWriter(home + archivo, true))) {
                f.println(acu);
            } catch (Exception ex) {
                System.err.println("Error de acceso al archivo");
            }
        }

        if (e.getSource() == mnuLeer) {

            String contenido = "";
            try (Scanner f = new Scanner(new File(home + archivo))) {
                while (f.hasNext()) {
                    contenido += f.nextLine() + "\n";
                }
                JOptionPane.showMessageDialog(null, contenido, "Numeros", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception s) {
                System.err.println("Error de Accesso al Archivo");
            }
        }

        if (e.getSource() == btnReset || e.getSource() == mnuReset) {
            acu = "";
            txf.setText(null);
            for (int i = 0; i < b.length; i++) {
                b[i].setBackground(null);

            }
        }
        if (e.getSource() == mnuSalir) {

            int respuesta;
            respuesta = JOptionPane.showConfirmDialog(null, "Estas seguro que quieres salir de la aplicación ?",
                    "Salir", JOptionPane.OK_CANCEL_OPTION);
            if (respuesta == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }

        if (e.getSource() == mnuInformacion) {

            JOptionPane.showMessageDialog(null, "Directorio actual: " + System.getProperty("user.dir") + "\n"
                    + "Usuario: " + System.getProperty("user.home"), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}