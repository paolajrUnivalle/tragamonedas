package myProject;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.Random;

public class Canal extends JLabel implements Runnable{

    private boolean rodar;
    private Random aleatorio;
    private int indexImagen;
    private ImageIcon imagen;

    public Canal() {
        aleatorio = new Random();
        indexImagen = aleatorio.nextInt(16)+1;
        imagen = new ImageIcon(getClass().getResource(GUI.PATH+indexImagen+".png"));
        this.setIcon(imagen);

        //JLabel setup
        Border raised = BorderFactory.createRaisedBevelBorder();
        Border lowered = BorderFactory.createLoweredBevelBorder();
        this.setBorder(BorderFactory.createCompoundBorder(raised, lowered));
    }

    public int getIndexImagen() {
        return indexImagen;
    }

    public void iniciarHilo() {
        rodar=true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    public void pararHilo() {
        rodar=false;
    }

    @Override
    public void run() {
        while (rodar) {
            indexImagen = aleatorio.nextInt(16) + 1;
            imagen = new ImageIcon(getClass().getResource(GUI.PATH + indexImagen + ".png"));
            this.setIcon(imagen);
            try {
                Thread.sleep(aleatorio.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
