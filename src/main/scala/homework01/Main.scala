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
    //run this line:
    //"britney" "brittany" "brittney" "britany" "britny" "briteny" "britteny" "briney" "brittny" "brintey" "britanny" "britiny" "britnet" "britiney" "britaney" "britnay" "brithney" "brtiney" "birtney" "brintney"
    println(rankMED(args(0), args.drop(1).toList).sortBy(_._2))
  }
}
