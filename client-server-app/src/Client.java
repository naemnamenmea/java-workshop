import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        int port = 8080;
//        int port = Integer.parseInt(args[1]);
        String ip = args.length == 0 ? "localhost" : args[0]; //по умолчанию - localhost
        String address = ip + ":" + port;
        println("Устанавливаю соединение с " + address);

        Socket client = null;
        try {
            client = new Socket(ip, port);
        } catch (IOException e) {
            println("Сервер недоступен!");
            System.exit(-1);
        }

        int clientPort = client.getLocalPort();
//        println("Клиент доступен по адресу: " + client.getLocalSocketAddress());

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);

        //Ожидаем сообщение сервера
        try {
            String fromServer = in.readLine();
            println("Клиент " + clientPort + " получил от сервера интервал: " + fromServer);

            String[] arguments = fromServer.split(" ");
            long a = Long.parseLong(arguments[0]);
            long b = Long.parseLong(arguments[1]);

            println("Клиент " + clientPort + " проводит вычисление");
            int cnt = find(a, b);
            println("Клиент " + clientPort + " закончил поиск и на интервале " + fromServer + " нашел " + cnt +  " чисел");

            out.println(cnt);
        } catch (Exception e) {
            println("Ошибка передачи данных!");
        }

        //закрываем клиента и потоки
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