package homework01

/**
 * Created by mcapizzi on 1/24/15.
 */

import CalculateMED._
import MinimumEditDistance._

//args => first item = target, resulting items = misspellings
object Main {
  def main(args: Array[String]) = {
    //args(0), args(1)
    println(rankMED(args(0), args.drop(1).toList))
  }
}
