package me.pieking1215.game.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import me.pieking1215.game.Game;
import me.pieking1215.game.Game.DebugLevel;
import me.pieking1215.game.Game.DebugPriority;
import me.pieking1215.game.entities.PlayerMP;
import me.pieking1215.game.net.packets.Packet;
import me.pieking1215.game.net.packets.Packet.PacketTypes;
import me.pieking1215.game.net.packets.Packet00Login;
import me.pieking1215.game.net.packets.Packet01Disconnect;
import me.pieking1215.game.net.packets.Packet02Move;

public class GameServer extends Thread {

    private DatagramSocket socket;
    
    @SuppressWarnings("unused")
	private Game game;
    
    public static List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

    public GameServer(Game game) {
        this.game = game;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
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
    	//System.out.println("gotpacket");
        String message = new String(data).trim();
        PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet packet = null;
        switch (type) {
        default:
        case INVALID:
            break;
        case LOGIN:
            packet = new Packet00Login(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet00Login) packet).getUsername() + " has connected...");
            PlayerMP player = new PlayerMP(Game.level, 100, 100, ((Packet00Login) packet).getUsername(), address, port);
            this.addConnection(player, (Packet00Login) packet);
            break;
        case DISCONNECT:
            packet = new Packet01Disconnect(data);
            System.out.println("[" + address.getHostAddress() + ":" + port + "] "
                    + ((Packet01Disconnect) packet).getUsername() + " has left...");
            this.removeConnection((Packet01Disconnect) packet);
            break;
        case MOVE:
            packet = new Packet02Move(data);
            this.handleMove(((Packet02Move) packet));
        }
    }

    public void addConnection(PlayerMP player, Packet00Login packet) {
        boolean alreadyConnected = false;
        for (PlayerMP p : GameServer.connectedPlayers) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                // relay to the current connected player that there is a new
                // player
                sendData(packet.getData(), p.ipAddress, p.port);

                // relay to the new player that the currently connect player
                // exists
                packet = new Packet00Login(p.getUsername(), p.x, p.y);
                sendData(packet.getData(), player.ipAddress, player.port);
            }
        }
        if (!alreadyConnected) {
            GameServer.connectedPlayers.add(player);
        }
    }

    public void removeConnection(Packet01Disconnect packet) {
        GameServer.connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
        packet.writeDataServer(this);
    }

    public PlayerMP getPlayerMP(String username) {
        for (PlayerMP player : GameServer.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerMPIndex(String username) {
        int index = 0;
        for (PlayerMP player : GameServer.connectedPlayers) {
            if (player.getUsername().equals(username)) {
                break;
            }
            index++;
        }
        return index;
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        if (!Game.isApplet) {
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDataToAllClients(byte[] data) {
    	//System.out.println("send data");
        for (PlayerMP p : connectedPlayers) {
            sendData(data, p.ipAddress, p.port);
        }
    }

    private void handleMove(Packet02Move packet) {
        if (getPlayerMP(packet.getUsername()) != null) {
            int index = getPlayerMPIndex(packet.getUsername());
            PlayerMP player = GameServer.connectedPlayers.get(index);
            player.x = packet.getX();
            player.y = packet.getY();
            player.setMoving(packet.isMoving());
            player.setMovingDir(packet.getMovingDir());
            player.setNumSteps(packet.getNumSteps());
            packet.writeDataServer(this);
            //System.out.println("handlemove");
        }else{
        	Game.debug(DebugLevel.INFO, DebugPriority.DEV, "null player");
        }
    }

}
