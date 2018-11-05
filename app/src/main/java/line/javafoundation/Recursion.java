package line.javafoundation;


/**
 * Created by ying.fu.
 * Date: 2018/10/31.
 */

public class Recursion {

    public int sortArrayPositive(int[] n, int a) {
        if (a == 1) {
            System.out.println("有序");
            return 1;
        }
        if (n[a - 1] <= n[a - 2]) {
            System.out.println("无序");
            return 0;
        } else {
            System.out.println("进入递归："+(a - 1));
            return sortArrayPositive(n, a - 1);
        }
    }


    public static void main(String[] args) {
        Recursion util = new Recursion();
        util.sortArrayPositive(new int[]{5, 4, 3, 2, 1}, 5);
    }
}
