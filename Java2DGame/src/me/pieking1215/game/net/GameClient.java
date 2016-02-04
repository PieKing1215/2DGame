package me.pieking1215.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.entities.PlayerMP;
import me.pieking1215.game.net.packets.Packet;
import me.pieking1215.game.net.packets.Packet.PacketTypes;
import me.pieking1215.game.net.packets.Packet00Login;
import me.pieking1215.game.net.packets.Packet01Disconnect;
import me.pieking1215.game.net.packets.Packet02Move;

public class GameClient extends Thread {

    private InetAddress ipAddress;
    private DatagramSocket socket;
    
    public GameClient(Game game, String ipAddress) {
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) {
            Game.debug(DebugLevel.SEVERE, DebugPriority.DEV, "CLIENT COUND NOT START! (SocketException)");
            e.printStackTrace();
        } catch (UnknownHostException e) {
        	Game.debug(DebugLevel.SEVERE, DebugPriority.DEV, "CLIENT COUND NOT START! (UnknownHostException)");
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
        default:
        case INVALID:
            break;
        case LOGIN:
            packet = new Packet00Login(data);
            handleLogin((Packet00Login) packet, address, port);
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            Game.debug(DebugLevel.INFO, DebugPriority.NORMAL,"[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect) packet).getUsername() + " has left the world...");
            Game.level.removePlayerMP(((Packet01Disconnect) packet).getUsername());
            break;
        case MOVE:
            packet = new Packet02Move(data);
            handleMove((Packet02Move) packet);
        }
    }

    public void sendData(byte[] data) {
        //if (!game.isApplet) {
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
    }

    private void handleLogin(Packet00Login packet, InetAddress address, int port) {
    	System.out.println("client login "+packet.getUsername());
        System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername()
                + " has joined the game...");
        PlayerMP player = new PlayerMP(Game.level, packet.getX(), packet.getY(), packet.getUsername(), address, port);
        Game.level.addEntity(player);
        System.out.println(Game.level);
    }

    private void handleMove(Packet02Move packet) {
    	//System.out.println("client move "+packet.getUsername());
        Game.level.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getNumSteps(),packet.isMoving(), packet.getMovingDir());
    }
}