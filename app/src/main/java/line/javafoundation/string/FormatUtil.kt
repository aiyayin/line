package line.javafoundation.string

import java.util.*

/**
 * Created by ying.fu.
 * Date: 2018/8/23.
 */
class FormatUtil {
    fun printPhoneNumber1(s: String): String {
        val length = s.length
        return String.format("%-4s%-5s%-5s\n", s.substring(0, if (length > 3) 3 else length), s.substring(if (length > 3) 3 else length, if (length > 7) 7 else length), s.substring(if (length > 7) 7 else length, if (length > 11) 11 else length))
    }

    fun printPhoneNumber(s: String) {
        val formatter = Formatter(System.out)
        val length = s.length
        formatter.format("%-4s%-5s%-5s\n", s.substring(0, if (length > 3) 3 else length), s.substring(if (length > 3) 3 else length, if (length > 7) 7 else length), s.substring(if (length > 7) 7 else length, if (length > 11) 11 else length))
        formatter.format("%-4s%-5s%-5s\n", "---", "----", "----")
    }

    private fun scanner(): String {
        var sc: Scanner? = null
        var number = ""
        try {
            sc = Scanner(System.`in`)
            println("请输入您的电话号码：")
            if (sc.hasNext()) {
                number = sc.next()
                println("您的电话号码：$number\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (sc != null) {
                println("sc.close()")
                sc.close()
                sc = null
            }
        }
        return number
    } //    public static void main(String[] args) {

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
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val util = FormatUtil()
            val s = util.scanner()
            val s2 = util.printPhoneNumber1(s)
            println("s2：$s2")

//        util.printPhoneNumber("13");
//        util.printPhoneNumber("131458");
//        util.printPhoneNumber("1314588");
//        util.printPhoneNumber("13145880801");
//        System.out.print(util.printPhoneNumber1("13"));
//        System.out.print(util.printPhoneNumber1("131458"));
//        System.out.print(util.printPhoneNumber1("1314588"));
//        System.out.print(util.printPhoneNumber1("13145880801"));
        }
    }
}