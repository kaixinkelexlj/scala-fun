package com.fun

import java.io.{FileNotFoundException, IOException}
import java.nio.file.{Files, Paths}
import java.util.Date

import scala.util.matching.Regex

/**
  * @author xulujun @date 2019/08/26
  */
object HelloScala {
  def main(args: Array[String]): Unit = {

    Test.simpleTest()
    Test.loopTest()
    FunctionTest.test()
    FunctionTest.printInt(b = 100, a = 200)
    FunctionTest.inc(3)
    FunctionTest.logWithDateBound("hello function")
    FunctionTest.strcat("hello")("world")

    StringTest.strFmt()

    ArrayTest.test()
    CollectionTest.test()
    CollectionTest.testTuple()

    RegexpTest.test()
    IOTest.test()

    import ClassTest._
    4 times println("hello")

  }


  object Test {
    def simpleTest(): Unit = {
      println("hello fun")
      println(
        """the present string
spans three
lines.
      """)
    }

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

    def strcat(s1: String)(s2: String) = s1 + s2

  }

  object ClosureTest {

  }

  object StringTest {
    def strFmt(): Unit = {
      val s = printf("hello %s", "xulujun")
      println(s)
      val name = "xlj"
      val str = s"hello $name"
      println(str)
      val height = 1.9d
      println(f"$name%s is $height%2.2f meters tall") //James is 1.90 meters tall
      println(raw"x/x/x/x/\\x")
    }
  }

  object ArrayTest {
    def test(): Unit = {
      val arr = Array(1, 2, 3)
      for (x <- arr) {
        println(x)
      }
      val bList: Array[Int] = Array.range(1, 10)
      val cList = Array.concat(arr, bList)
      for (x <- cList) {
        print(x)
      }
      println("")
      println(s"cList(10) is: ${cList(10)}")
    }
  }

  object CollectionTest {
    def test(): Unit = {
      val list = List(1, 2, 3)
      val list2 = List(4, 5, 6)
      val list3: List[Int] = list ::: list2
      list3.foreach(i => println(i))
      val list4: List[String] = List.fill(3)("repeat me")
      list4.foreach(i => println(i))

      var list5: List[Int] = List(3, 4, 5)
      list5 = list5.+:(2)
      list5 = list5.:+(6)
      list5 = list5.::(1)
      list5.foreach(i => println(i))
      list5 = list5 ::: List(100, 101)
      list5 = list5 ++ List(200, 201)
      list5.foreach(i => println(i))


      val mySet = Set(1, 2, 1)
      mySet.foreach(i => println(i))

      val map1 = Map("red" -> "red value", "green" -> "green value")
      val map2 = Map("hello" -> "world")
      val map3 = map1 ++ map2
      map3.foreach(p => println(s"${p._1}==>${p._2}"))
      println(map3.find(p => p._1.equals("hello"))
        .getOrElse(""))

    }

    def testTuple(): Unit = {
      val a = ("hello", 1, "world", 3.14f)
      println(s"a._3: ${a._3}")
    }

  }

  object CaseTest {

    val lujun = User("xulujun", 30)
    val huiqian = User("huiqian", 20)
    val xuhe = User("xuhe", 3)
    for (u <- List(lujun, huiqian, xuhe)) {
      u match {
        case User("xulujun", 30) => println("lujun")
        case `huiqian` => println("huiqian")
        case `xuhe` => println("son")
      }
    }

    case class User(name: String, age: Int)

  }

  object RegexpTest {
    def test(): Unit = {
      var pattern = new Regex("\\S+")
      var str = "this is ok"
      println((pattern findAllIn str).mkString(","))

      pattern = "\\.+".r
      str = "this is ok"
      println((pattern findAllIn str).mkString(","))
    }
  }

  object IOTest {
    def test(): Unit = {
      try {
        Files.readAllLines(Paths.get("test.data"))
      } catch {
        case ex: IOException => {
          println(ex)
          //ex.printStackTrace()
        }
        case ex: FileNotFoundException => {
          println(ex)
          //ex.printStackTrace()
        }
        case ex: Exception => {
          println("unknown exception")
          //ex.printStackTrace()
        }
      } finally {
        println("finally execute")
      }
    }
  }

  object ClassTest {

    // 作用域
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

    class Point(val xc: Int, val yc: Int) {
      var x: Int = xc
      var y: Int = yc
    }

    class Location(override val xc: Int, override val yc: Int, val zc: Int) extends Point(xc: Int, yc: Int) {

    }

    implicit class IntTimes(x: Int) {
      def times[A](f: => A): Unit = {
        for (i <- 0 until x) {
          f
        }
      }
    }

    class Student(name: String, id: Int) {
      var age = 0L

      def this(name: String, id: Int, age: Int) {
        this(name, id)
        this.age = age
      }
    }

  }

  //提取器测试
  /**
    * 当一个类的实例后跟括号为零个或多个参数的列表时，编译器会调用该实例上的apply方法。 可以在对象和类中定义apply方法。
    * 如上所述，unapply方法的目的是提取正在寻找的特定值。当使用match语句比较提取器对象时，将自动执行unapply方法。
    * 尝试以下示例程序 -原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/scala/scala_extractors.html
    */

  object Demo {
    def main(args: Array[String]) {
      val x = Demo(5)
      println(x)

      x match {
        case Demo(num) => println(x + " is bigger two times than " + num)

        //unapply is invoked
        case _ => println("i cannot calculate")
      }
    }

    def apply(x: Int): Int = x * 2

    def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
  }


}
