package udp.rx302.serveur;

import udp.rx302.Connexion;

/**
 * Created by SuRvYv0r on 04/05/2015.
 */
public class ServeurConcurrent extends Serveur {
    public ServeurConcurrent(int port) {
        super(port);
        System.out.println("Serveur concurrent initialisé sur le port : " + PORT_HOST);
    }

    public void run() {
        while(true) {
            do {
                reception();
            } while (getBuffer().compareTo("hello serveur RX302") != 0);
            System.out.println("Nouveau client : " + getHostName() + ":" + getHostPort());

            new Thread(new Connexion(getHostAddress(), getHostPort())).start();
        }
    }

    public static void main(String[] args) {
        ServeurConcurrent sc = new ServeurConcurrent(PORT_HOST);
        sc.run();
        sc.stop();
    }
}
