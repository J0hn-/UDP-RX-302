package udp.rx302;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * Created by SuRvYv0r on 26/04/2015.
 */
public class UDPinterface {

    public DatagramSocket socket;
    public DatagramPacket dp;
    public InetAddress ia;

    public static int PORT_HOST = 8008;
    public static int MAX_SIZE = 512;

    public static String IP_S = "localhost";

    byte[] buffer = new byte[MAX_SIZE];

    public static char END_CHAR = '_';

    public UDPinterface() {
        try{
            socket = new DatagramSocket();
            ia = InetAddress.getByName(IP_S);
        }
        catch(SocketException ex) {
            System.err.println("Impossible de créer le DatagramSocket");
            System.exit(1);
        } catch (UnknownHostException e) {
            System.err.println("Impossible d'obtenir l'adresse de l'hote !");
            System.exit(1);
        }

    }

    public UDPinterface(int port) {
        try{
            socket = new DatagramSocket(port);
        }
        catch(SocketException ex) {
            System.err.println("Impossible de créer le DatagramSocket avec le port précisé : " + port);
            System.exit(1);
        }
    }

    public void envoyer(String msg, InetAddress adr, int port) {
        msg += END_CHAR; // On rajoute un caractère de fin

        byte[] chaine = new byte[0];
        try {
            chaine = msg.getBytes("ascii");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Problème avec l'encodage ASCII");
            System.exit(1);
        }

        try {
            dp = new DatagramPacket(chaine, chaine.length, adr, port);
            socket.send(dp);
        }
        catch(IOException ex) {
            System.err.println("Problème lors de l'envoi !");
            System.exit(1);
        }
    }

    public void reception() {
        dp = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(dp);
        } catch (IOException e) {
            System.err.println("Problème lors de la reception !");
            System.exit(1);
        }
    }

    public String getBuffer() {
        try {
            String s = new String(buffer, "ascii");
            int i = 0;
            while (s.charAt(i) != END_CHAR) ++i;
            return (String) s.subSequence(0, i);
        } catch (UnsupportedEncodingException e) {
            System.err.println("Problème lors de la conversion ASCII");
            System.exit(1);
        }
        return null;
    }

    public InetAddress getHostAddress() {
        return dp.getAddress();
    }

    public String getHostName() {
        return dp.getAddress().getHostAddress();
    }

    public void stop() {
        socket.close();
    }

    public int getHostPort() {
        return dp.getPort();
    }

    /*
        Cette fonction prend en paramètres un port de depart ainsi qu'un port d'arrivée
        La fonction retourne le premier port disponible sur cette plage de port
        Elle retourne 0 si les paramètres sont incorrects
     */
    public static int nmap(int departurePort, int arrivalPort){
        if(departurePort > arrivalPort || departurePort > 65535 || arrivalPort > 65535)
            return 0;
        boolean isUsed;
        DatagramSocket ds = null;
        for(int i = departurePort; i <= arrivalPort; ++i) {
            isUsed = false;
            try {
                 ds = new DatagramSocket(i);
            }
            catch(SocketException ex) {
                isUsed = true;
            }
            if(!isUsed) {
                ds.close();
                return i;
            }
        }
        return 0;
    }
}
