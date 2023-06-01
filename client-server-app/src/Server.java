import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public static void main(String[] args) throws UnknownHostException {
        // ������� server socket
        ServerSocket server = null;
        int port = 8080;

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("�� ���� ���������� ���� 1234");
            System.exit(-1);
        }

        String address = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        System.out.println("������ �������� �� ������: " + address);
        System.out.println("������ ��������...");

        long interval = 999999;
        long nextStart = 1;

        //������� �������� � �������� ������
        try {
            while (true) {
                Socket client = server.accept(); //���� �������
                System.out.println("��������� ����� ������: " + client.getRemoteSocketAddress());

                final long a = nextStart;
                final long b = a + interval;
                nextStart += interval + 1;

                new Thread(new Runnable() {
                    public void run() {
                        worker(client, a, b);
                    }
                }).start(); //������ ������ � �������� � �������� ������
            }
        } catch (IOException e) {
            System.out.println("������ ��������");
        }

    }

    private static void worker(Socket client, long a, long b) {
        BufferedReader in;
        PrintWriter out;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("������ " + client.getRemoteSocketAddress() + " ����������!");
            return;
        }

        //�������� ��������� �������
        String toClient = a + " " + b;
        System.out.println("������� ���������: " + toClient);
        out.println(toClient);

        //���� ������
        try {
            String answer = in.readLine();
            System.out.println("������ " + client.getRemoteSocketAddress() + " ������� �����: " + answer);
        } catch (IOException e) {
            System.out.println("������ " + client.getRemoteSocketAddress() + " �� ������� �����!");
        }

        //��������� ������
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            System.out.println("�� ���� ������� ������: " + client.getRemoteSocketAddress());
        }
    }

}