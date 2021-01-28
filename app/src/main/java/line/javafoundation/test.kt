package line.javafoundation

open class test(val a: String = "") {

}

class test2(val b: String = "") : test(b) {

}

class test3(val c: String = "") {
    fun main() {
        test().a
        test("111").a
    }
}