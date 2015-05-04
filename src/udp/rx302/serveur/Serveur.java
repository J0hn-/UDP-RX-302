/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp.rx302.serveur;

import udp.rx302.UDPinterface;

/**
 *
 * @author SuRvYv0r
 */
public class Serveur extends UDPinterface {
    
    public Serveur(int port) {
        super(port);
    }

    public void run()
    {
        String s = new String();

        System.out.println("Serveur initialisé sur le port : " + PORT_HOST);

        do {
            reception();
        } while (getBuffer().compareTo("hello serveur RX302") != 0);
        System.out.println("Nouveau client : " + getHostName() + ":" + getHostPort());
        envoyer("Serveur RX302 ready", getHostAddress(), getHostPort());

        while(s.compareTo("STOP") != 0)
        {
            reception();
            s = getBuffer();
            System.out.println("Message recu : " + s);
            envoyer(s, getHostAddress(), getHostPort());
        }
    }
    
    public static void main(String[] args) {
        Serveur serveur = new Serveur(PORT_HOST);
        serveur.run();
        serveur.stop();
    }
}
