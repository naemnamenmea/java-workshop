import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static List<Integer> Fibo(int pos) {
        ArrayList<Integer> res = new ArrayList<>();
        res.add(1);
        if (pos == 1) {
            return res;
        }
        res.add(1);
        if(pos == 2) {
            return res;
        }

        int a = 1;
        int b = 1;

        while (2 < pos--) {
            int tmp = a + b;
            a = b;
            b = tmp;
            res.add(b);
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        Scanner in = new Scanner(new BufferedReader(new FileReader("input.txt")));
//        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));


        in.close();

        long endTime = System.nanoTime();
        double execTime = (endTime - startTime) / 1e9;
        System.err.println(execTime);
    }
}
