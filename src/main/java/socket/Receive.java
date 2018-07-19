package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 接收
 *
 * @author bin.yu
 * @create 2018-07-08 22:10
 **/
public class Receive implements Runnable {
    @Override
    public void run() {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(6000);
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
            while (true) {
                datagramSocket.receive(datagramPacket);
                byte[] data = datagramPacket.getData();
                int length = datagramPacket.getLength();
                InetAddress address = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                System.out.println(new String(data,0,length));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
