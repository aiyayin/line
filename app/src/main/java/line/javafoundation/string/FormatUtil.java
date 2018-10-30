package line.javafoundation.string;

import java.util.Formatter;
import java.util.Scanner;

/**
 * Created by ying.fu.
 * Date: 2018/8/23.
 */

public class FormatUtil {

    public String printPhoneNumber1(String s) {
        int length = s.length();
        String result = String.format("%-4s%-5s%-5s\n", s.substring(0, (length > 3) ? 3 : length), s.substring((length > 3) ? 3 : length, (length > 7) ? 7 : length), s.substring((length > 7) ? 7 : length, (length > 11) ? 11 : length));
        return result;
    }

    public void printPhoneNumber(String s) {
        Formatter formatter = new Formatter(System.out);
        int length = s.length();
        formatter.format("%-4s%-5s%-5s\n", s.substring(0, (length > 3) ? 3 : length), s.substring((length > 3) ? 3 : length, (length > 7) ? 7 : length), s.substring((length > 7) ? 7 : length, (length > 11) ? 11 : length));
        formatter.format("%-4s%-5s%-5s\n", "---", "----", "----");
    }

    public static void main(String[] args) {
        FormatUtil util = new FormatUtil();
        String s = util.scanner();
        String s2 = util.printPhoneNumber1(s);
        System.out.println("s2：" + s2);

//        util.printPhoneNumber("13");
//        util.printPhoneNumber("131458");
//        util.printPhoneNumber("1314588");
//        util.printPhoneNumber("13145880801");
//        System.out.print(util.printPhoneNumber1("13"));
//        System.out.print(util.printPhoneNumber1("131458"));
//        System.out.print(util.printPhoneNumber1("1314588"));
//        System.out.print(util.printPhoneNumber1("13145880801"));
    }

    private String scanner() {
        Scanner sc = null;
        String number = "";
        try {
            sc = new Scanner(System.in);
            System.out.println("请输入您的电话号码：");
            if (sc.hasNext()) {
                number = sc.next();
                System.out.println("您的电话号码：" + number + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sc != null) {
                System.out.println("sc.close()");
                sc.close();
                sc = null;
            }
        }
        return number;
    }

//    public static void main(String[] args) {
//
//        Scanner sc = null;
//        try {
//            sc = new Scanner(System.in);
//            System.out.println("请输入您的姓名：");
//            String name = sc.nextLine();
//            System.out.println("请输入您的年龄：");
//            int age = sc.nextInt();
//            System.out.println("请输入您的体重（kg）：");
//            float salary = sc.nextFloat();
//            System.out.println("您的个人信息如下：");
//            System.out.println("姓名：" + name + "\n" + "年龄：" + age + "\n" + "体重：" + salary);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (sc != null) {
//                sc.close();
//                sc = null;
//            }
//        }
//    }
}
