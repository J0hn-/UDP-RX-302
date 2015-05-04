package udp.rx302;

import java.net.InetAddress;

/**
 * Created by SuRvYv0r on 27/04/2015.
 */
public class Connexion extends UDPinterface implements Runnable {

    public Connexion(InetAddress iaClient, int portClient) {
        super(nmap(1024,2048));
        envoyer("Serveur RX302 ready", iaClient, portClient);
    }

    public void run() {
        String s = new String();

        while(s.compareTo("STOP") != 0)
        {
            reception();
            s = getBuffer();
            System.out.println("Message recu : " + s);
            envoyer(s, getHostAddress(), getHostPort());
        }

        stop();
    }
}
