package com.fun

/**
 * @author xulujun 2019/8/28
 */
class CompanionClass {
  private var classPrivateObj: String = "this is private"


}

object CompanionClass {

  def showPrivate(): String = {
    new CompanionClass().classPrivateObj
  }

}
