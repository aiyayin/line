package line.javafoundation.vampire;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ying.fu.
 * Date: 2018/8/10.
 */

public class Vampire {
    List<Integer> resultHelper = new ArrayList<>();

    public void printVampire() {
        for (int t = 1001; t < 9999; t++) {
            int first = t / 1000;
            int second = (t - first * 1000) / 100;
            int third = (t - first * 1000 - second * 100) / 10;
            int fourth = (t - first * 1000 - second * 100 - third * 10);
            LinkedList<Integer> ints = new LinkedList<>();
            ints.add(first);
            ints.add(second);
            ints.add(third);
            ints.add(fourth);
            for (int j = 0; j < 4; j++) {
                for (int i = j + 1; i < 4; i++) {
                    ints.clear();
                    ints.add(first);
                    ints.add(second);
                    ints.add(third);
                    ints.add(fourth);
                    int a = ints.get(i) * 10 + ints.get(j);
                    int b = ints.get(j) * 10 + ints.get(i);
                    ints.remove(i);
                    ints.remove(j);
                    int c = ints.get(0) * 10 + ints.get(1);
                    int d = ints.get(1) * 10 + ints.get(0);
                    printResult(a, c, t);
                    printResult(a, d, t);
                    printResult(b, c, t);
                    printResult(b, d, t);

                }
            }
        }
    }

    private void printResult(int a, int c, int t) {
        if (a * c == t) {
            for (int i : resultHelper) {
                if (i == a || i == c)
                    return;
            }
            System.out.print("printVampire:  " + a + " * " + c + "=" + t + "\n");
            resultHelper.add(a);
            resultHelper.add(c);
        }
    }


    public void printVampire(int number, int k) {
        for (int t = (int) (Math.pow(10, number - 1) + 1); t < (int) (Math.pow(10, number) - 1); t++) {
            char[] chars = String.valueOf(t).toCharArray();
            combine(chars, k, t);
        }
    }

    private void combine(char[] data, int k, int t) {
        ArrayList<String> resultdata = new ArrayList<>();
        combineN(data, resultdata, 0);
        for (String item :
                resultdata) {
            char[] chars = item.toCharArray();
            int ck = 0;
            int cend = 0;
            for (int i = 0; i < chars.length; i++) {
                if (i < k)
                    ck = (int) (ck + (chars[i] - '0') * Math.pow(10, k - i - 1));
                else
                    cend = (int) (cend + (chars[i] - '0') * Math.pow(10, chars.length - i - 1));
            }
            printResult(ck, cend, t);

        }

    }

    private void combineN(char[] data, ArrayList<String> resultdata, int start) {
        if (start >= data.length)
            return;
        resultdata.add(String.valueOf(data));
        for (int i = start; i < data.length; i++) {
            if (check(data, start, i)) {
                change(data, start, i);
                combineN(data, resultdata, start + 1);
                change(data, start, i);
            }
        }

    }

    private void change(char[] chs, int start, int i) {
        char temp = chs[start];
        chs[start] = chs[i];
        chs[i] = temp;
    }

    private boolean check(char[] chs, int start, int i) {
        if (chs[start] == chs[i]) return false;
        return true;
    }

    public static void main(String[] args) {
        Vampire vampire = new Vampire();
        vampire.printVampire(4, 2);
    }
}
