package jantar;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adeildo Pereira
 */
public class Principal {

    static Semaphore mutex = new Semaphore(1);
    //semaforo para cada filosofo
    static Semaphore[] semaforos = new Semaphore[5]; 

    static Filosofo[] filosofos = new Filosofo[5];
    static int[] estado = new int[5];

    public static void main(String[] args) {

        //todos os filosofos iniciam pensando
        for (int i = 0; i < estado.length; i++) {
            estado[i] = 0;
        }

        // Inicializa todos filósofos
        filosofos[0] = new Filosofo("Sócrates", 0);
        filosofos[1] = new Filosofo("Platão", 1);
        filosofos[2] = new Filosofo("Aristótoles", 2);
        filosofos[3] = new Filosofo("Tales", 3);
        filosofos[4] = new Filosofo("Parmênides", 4);
        
        //saber quais garfos pertence aos filosofos
        for (int i = 0; i < filosofos.length; i++) {            
            System.out.println("garfo " + i + " - filosofo  " + i + " - garfo " + (i + 1) % 5);            
        }
        System.out.println("");        
        /*semaforos[0] = new Semaphore(0);
        semaforos[1] = new Semaphore(0);
        semaforos[2] = new Semaphore(0);
        semaforos[3] = new Semaphore(0);
        semaforos[4] = new Semaphore(0);*/
        for (int i = 0; i < semaforos.length; i++) {
            semaforos[i] = new Semaphore(0);
        }

        /*filosofos[0].start();
        filosofos[1].start();
        filosofos[2].start();
        filosofos[3].start();
        filosofos[4].start();*/              
        for (int i = 0; i < filosofos.length; i++) {
            filosofos[i].start();
        }

        try {
            Thread.sleep(10000);
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}