package myProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is used for ...
 * @autor Paola-J Rodriguez-C paola.rodriguez@correounivalle.edu.co
 * @version v.1.0.0 date:21/11/2021
 */
public class GUI extends JFrame {
    public static final String PATH ="resources/";
    private Header headerProject;
    private JPanel botones, canales;
    private Canal canal1, canal2, canal3;
    private JButton iniciar, parar;
    private ImageIcon imagen;
    private Escucha escucha;

    /**
     * Constructor of GUI class
     */
    public GUI(){
        initGUI();

        //Default JFrame configuration
        this.setTitle("TragaMonedas app");
        Image image = new ImageIcon(getClass().getResource(PATH+"iconMoney.png")).getImage();
        this.setIconImage(image);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */
    private void initGUI() {
        //Set up JFrame Container's Layout
        //Create Listener Object and Control Object
        escucha = new Escucha();
        //Set up JComponents
        headerProject = new Header("TragaMonedas", Color.BLACK);
        this.add(headerProject,BorderLayout.NORTH);

        canales = new JPanel();
        canales.setLayout(new GridLayout(1,3));
        canales.setBackground(Color.WHITE);
        canal1 = new Canal();
        canal2 = new Canal();
        canal3 = new Canal();

        canales.add(canal1);
        canales.add(canal2);
        canales.add(canal3);

        botones = new JPanel();

        imagen = new ImageIcon(getClass().getResource(PATH+"iniciar-1.png"));
        iniciar = new JButton(imagen);
        iniciar.setBorder(null);
        iniciar.setContentAreaFilled(false);
        iniciar.addActionListener(escucha);

        imagen = new ImageIcon(getClass().getResource(PATH+"parar-1.png"));
        parar = new JButton(imagen);
        parar.setBorder(null);
        parar.setContentAreaFilled(false);
        parar.addActionListener(escucha);

        botones.add(iniciar);
        botones.add(parar);

        add(canales,BorderLayout.CENTER);
        add(botones,BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this,
                                     "Ten presente que: \nBotón Verde inicia el jugo " +
                                             "\nBotón Rojo para el juego",
                                      "Instrucciones de Juego", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUI miProjectGUI = new GUI();
        });
    }

    private void validarJuego() {
        if(canal1.getIndexImagen()==canal2.getIndexImagen() && canal1.getIndexImagen()==canal3.getIndexImagen()) {
            String mensaje = "Máxima jugada, has ganado $1000 ;) \n¿Deseas continuar jugando?";
            estadoJuego(mensaje);
        }else {
            if(canal1.getIndexImagen()==canal2.getIndexImagen() ||
               canal1.getIndexImagen()==canal3.getIndexImagen() ||
               canal2.getIndexImagen()==canal3.getIndexImagen()) {
                String mensaje = "Has logrado una pareja, ganaste $20 \n¿Deseas continuar jugando?";
                estadoJuego(mensaje);
            }else {
                String mensaje = "Has perdido :( \n¿Desea continuar jugando?";
                estadoJuego(mensaje);
            }
        }
    }

    private void estadoJuego(String mensaje) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                int option= JOptionPane.showConfirmDialog(botones, mensaje, "Estado del Juego",
                                                          JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(option==JOptionPane.YES_OPTION) {
                    reiniciarJuego();
                }else {
                    System.exit(0);
                }
            }
        });
    }

    private void reiniciarJuego() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //restablezco las imagenes de los botones
                imagen = new ImageIcon(getClass().getResource(PATH+"iniciar-1.png"));
                iniciar.setIcon(imagen);
                iniciar.addActionListener(escucha);
                imagen = new ImageIcon(getClass().getResource(PATH+"parar-1.png"));
                parar.setIcon(imagen);
                parar.addActionListener(escucha);
            }
        });
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Escucha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource()==iniciar) {
                imagen = new ImageIcon(getClass().getResource(PATH+"iniciar-2.png"));
                iniciar.setIcon(imagen);
                iniciar.removeActionListener(escucha);
                canal1.iniciarHilo();
                canal2.iniciarHilo();
                canal3.iniciarHilo();
            }else {
                canal1.pararHilo();
                canal2.pararHilo();
                canal3.pararHilo();
                imagen = new ImageIcon(getClass().getResource(PATH+"parar-2.png"));
                parar.setIcon(imagen);
                parar.removeActionListener(escucha);
                validarJuego();
            }
        }
    }
}
