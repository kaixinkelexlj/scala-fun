package com.fun

import java.util.Date

/**
  * @author xulujun @date 2019/08/26
  */
object HelloScala {
  def main(args: Array[String]): Unit = {
    println("hello fun")
    println(
      """the present string
spans three
lines.
      """)
  }

  Test.loopTest()
  FunctionTest.test()
  FunctionTest.printInt(b = 100, a = 200)
  FunctionTest.inc(3)
  FunctionTest.logWithDateBound("hello function")

}


object Test {
  def loopTest(): Unit = {
    var i: Int = 0
    while (i < 100) {
      println(i)
      i += 1
    }

    var j: Int = 0
    do {
      j += 1
      println(j)
    } while (j < 100)

    for (c <- 1 to 10 if c % 2 == 0; if c < 10) {
      println(c)
    }

  }

}


object FunctionTest {

  def test(): Unit = {
    println(delayed(time()))
    apply(objToString, 10)
  }

  def time() = {
    println("Getting time in nano seconds")
    System.nanoTime
  }

  def delayed(t: => Long) = {
    println("In delayed method")
    println("Param: " + t)
  }

  // 带默认值
  def printInt(a: Int, b: Int = 7) = {
    println("Value of a : " + a)
    println("Value of b : " + b)
  }

  def printStrings(arr: String*): Unit = {
    for (value <- arr) {
      println(value)
    }
  }

  def apply(f: Int => String, v: Int) = f(v)

  def objToString[A](x: A): String = "[" + x.toString + "]"

  var inc = (x: Int) => x + 1

  def log(date: Date, message: String) = {
    println("date" + "----" + message)
  }

  def logWithDateBound = log(new Date, _: String)


}


class Outer {

  class Inner {
    private def f() {
      println("f")
    }

    def publicMethod(): Unit = {

    }

    class InnerMost {
      f() // OK
    }

  }

  // (new Inner).f() // Error: f is not accessible
  (new Inner).publicMethod()
}

class Super {
  protected def f() {
    println("f")
  }
}

class Sub extends Super {
  f()
}

class Other {
  // (new Super).f() // Error: f is not accessible
}




