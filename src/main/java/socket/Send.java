package socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * 发送
 *
 * @author bin.yu
 * @create 2018-07-08 21:57
 **/
public class Send implements Runnable{

    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(System.in);
            DatagramSocket datagramSocket = new DatagramSocket();
            while (true) {
                String str = sc.nextLine();
                if ("quit".equals(str)) {
                    break;
                }
                DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getByName("127.0.0.1"), 6000);
                datagramSocket.send(packet);
            }
            datagramSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
