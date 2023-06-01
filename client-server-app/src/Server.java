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
        // Создаем server socket
        ServerSocket server = null;
        int port = 8080;

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Не могу прослушать порт 1234");
            System.exit(-1);
        }

        String address = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        System.out.println("Сервер доступен по адресу: " + address);
        System.out.println("Ожидаю клиентов...");

        long interval = 999999;
        long nextStart = 1;

        //Ожидаем клиентов в основном потоке
        try {
            while (true) {
                Socket client = server.accept(); //ждем клиента
                System.out.println("Подключен новый клиент: " + client.getRemoteSocketAddress());

                final long a = nextStart;
                final long b = a + interval;
                nextStart += interval + 1;

                new Thread(new Runnable() {
                    public void run() {
                        worker(client, a, b);
                    }
                }).start(); //запуск работы с клиентом в побочном потоке
            }
        } catch (IOException e) {
            System.out.println("Сервер отключен");
        }

    }

    private static void worker(Socket client, long a, long b) {
        BufferedReader in;
        PrintWriter out;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Клиент " + client.getRemoteSocketAddress() + " недоступен!");
            return;
        }

        //отсылаем аргументы клиенту
        String toClient = a + " " + b;
        System.out.println("Отсылаю аргументы: " + toClient);
        out.println(toClient);

        //ждем ответа
        try {
            String answer = in.readLine();
            System.out.println("Клиент " + client.getRemoteSocketAddress() + " прислал ответ: " + answer);
        } catch (IOException e) {
            System.out.println("Клиент " + client.getRemoteSocketAddress() + " не прислал ответ!");
        }

        //закрываем клиент
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Не смог закрыть клента: " + client.getRemoteSocketAddress());
        }
    }

}