package com.labs.lab6.Server.Utils;

import com.labs.lab6.Server.App.Main;

import java.io.IOException;
import java.net.*;

public class RepliesSendler {

    public static boolean sendReply(String msg) {
        byte[] buffer = msg.getBytes();

        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                    new InetSocketAddress(Main.getDefaultAddr(), Main.getDefaultPort() + 1));

            Thread.sleep(10);

            socket.send(packet);
            return true;

        } catch (SocketException e) {
            System.out.println("Socket exception. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException. " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Troubles with Thread... " + e.getMessage());
        }
        return false;
    }

}
