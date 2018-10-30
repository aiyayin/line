package line.javafoundation.bitmask;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by ying.fu.
 * Date: 2018/8/16.
 */

public class BitMask1 {
    //    题目大意：给出一个T， 和一个下限L, 上限R， 在[L, R]之间找一个数， 使得这个数与T做或运算之后的数值最大 输出这个数。
    private void a(int t, int l, int r) {
        int max = l;
        int maxi = t;
        for (int i = l; i <= r; i++) {
            int b = t | i;
            System.out.print(" b: " + b + " t: " + t + " i: " + i + "\n");
            if (max < b) {
                max = b;
                maxi = i;
            }
        }
        System.out.print("max: " + max + " i: " + maxi + "\n");
    }

    //贪心
    private void b(int t, int l, int r) {
        ArrayList<Integer> t_int = new ArrayList<>();
        intToBinary(t, t_int);

        ArrayList<Integer> r_int = new ArrayList<>();
        intToBinary(r, r_int);

        getMax(t_int, r_int, l, r);
    }

    /**
     * 关键算法
     *
     * @param t_int
     * @param r_int
     * @param l
     * @param r
     */
    private void getMax(ArrayList<Integer> t_int, ArrayList<Integer> r_int, int l, int r) {
        ArrayList<Integer> result = new ArrayList<>();
        result.addAll(r_int);
        /**
         * 依次取最高位 置为t相应位置相反的数(0,1)
         */
        for (int s = r_int.size() - 1; s >= 0; s--) {
            int t = 0;
            if (s < t_int.size())
                t = t_int.get(s);
            result.set(s, t ^ 1);
            result.toArray();
//            System.out.println(result);
            int k = binaryToInt(result);
            if (k < l || k > r) {
                /**
                 * 若置换该位置不在范围内 复原
                 */
                result.set(s, r_int.get(s));
            }
        }
        Logger logger = Logger.getLogger("getMax result: ");
        logger.severe( binaryToInt(result) + "\n");
        System.out.print("getMax result: " + binaryToInt(result) + "\n");
    }


    /**
     * int转二进制
     *
     * @param i
     * @param i_int
     */
    private void intToBinary(int i, ArrayList<Integer> i_int) {
        while (i >= 1) {
            i_int.add(i % 2);
            i = i / 2;
        }
    }

    private int binaryToInt(ArrayList<Integer> t_int) {
        int result = t_int.get(0);
        for (int i = 1; i < t_int.size(); i++) {
            result = (int) (result + t_int.get(i) * Math.pow(2, i));
        }
        return result;
    }

    public static void main(String[] args) {
        BitMask1 bitMask1 = new BitMask1();
        bitMask1.b(100, 50, 60);//59
        bitMask1.b(100, 50, 50);//50
        bitMask1.b(100, 0, 100);//27
        bitMask1.b(1, 0, 100);//100
        bitMask1.b(15, 1, 15);//1
    }
}
