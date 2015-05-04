/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp.rx302.client;

import udp.rx302.UDPinterface;
import java.util.Scanner;

/**
 *
 * @author SuRvYv0r
 */
public class Client extends UDPinterface{

    public Client() {
        super();
    }

    public void run()
    {
        Scanner sc = new Scanner(System.in);
        String str = new String();

        envoyer("hello serveur RX302", ia, PORT_HOST);
        do {
            reception();
        } while(getBuffer().compareTo("Serveur RX302 ready") != 0);
        System.out.println("Serveur RX302 ready : " + getHostName() + ":" + getHostPort());

        while(str.compareTo("STOP") != 0) {
            System.out.println("Que voulez-vous faire ?");
            str = sc.nextLine();
            envoyer(str, getHostAddress(), getHostPort());
            reception();
            System.out.println("Le serveur à répondu : " + getBuffer());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
