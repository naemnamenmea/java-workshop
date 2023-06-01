import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        int port = 8080;
//        int port = Integer.parseInt(args[1]);
        String ip = args.length == 0 ? "localhost" : args[0]; //�� ��������� - localhost
        String address = ip + ":" + port;
        println("������������ ���������� � " + address);

        Socket client = null;
        try {
            client = new Socket(ip, port);
        } catch (IOException e) {
            println("������ ����������!");
            System.exit(-1);
        }

        int clientPort = client.getLocalPort();
//        println("������ �������� �� ������: " + client.getLocalSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        //������� ��������� �������
        try {
            String fromServer = in.readLine();
            println("������ " + clientPort + " ������� �� ������� ��������: " + fromServer);

            String[] arguments = fromServer.split(" ");
            long a = Long.parseLong(arguments[0]);
            long b = Long.parseLong(arguments[1]);

            println("������ " + clientPort + " �������� ����������");
            int cnt = find(a, b);
            println("������ " + clientPort + " �������� ����� � �� ��������� " + fromServer + " ����� " + cnt +  " �����");

            out.println(cnt);
        } catch (Exception e) {
            println("������ �������� ������!");
        }

        //��������� ������� � ������
        out.close();
        in.close();
        client.close();
    }
    
    private static void println(String s) {
        System.out.print("\n" + s);
    }

    private static int find(long a, long b) {
        int cnt = 0;
        for (long i = a; i <= b; i++) {
            if (i % 11 == 0 && i % 13 == 0 && i % 17 == 0) {
                cnt++;
            }
        }
        return cnt;
    }

}