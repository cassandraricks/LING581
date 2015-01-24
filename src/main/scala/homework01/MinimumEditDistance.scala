package homework01



import org.apache.commons.codec.language.Soundex
import scala.io.Source
import CalculateMED._

/**
 * Created by mcapizzi on 1/23/15.
 */
object MinimumEditDistance {
  // TODO add Gus's resource code
  // TODO Build MAIN

  //how Soundex works
  //http://docs.oracle.com/cd/B19306_01/server.102/b14200/functions148.htm

  //creates Soundex coder
  val soundexCoder = new Soundex()
  
  //creates name list from file
  val names = Source.fromFile("/home/mcapizzi/Github/LING581/src/main/resources/all_names.txt").getLines.toList
  //val nameList = names.map(_.toLowerCase)
  val soundexNameTuple = for (name <- names.map(_.toLowerCase)) yield name -> soundexCoder.soundex(name)
 
  //to use Soundex
  //soundexCoder.soundex("britney") returns String: B635
  
  //creates list of Soundex matches to input name
  def findSoundexMatches(name: String): List[String] = {
    (soundexNameTuple filter (x => x._2.matches(soundexCoder.soundex(name)))).map(_._1) filterNot (x => x.matches(name))
  }
  
  def rankMED(nameToMatch: String, inputList: List[String]): List[(String, AnyVal)] = {
    //makes a list of lists contains the Soundex matches for each inputList name
    val inputListSoundexMatches = inputList.map(findSoundexMatches).flatten
    for (name <- inputList) yield
      if (findSoundexMatches(nameToMatch).contains(name)) name -> 0.5
      else
        if (distance(nameToMatch, name) < (for (item <- inputListSoundexMatches) yield item -> distance(item, name)).sortBy(_._2).map(_._2).min) name -> distance(nameToMatch, name)
        else name -> (for (item <- inputListSoundexMatches) yield item -> distance(item, name)).sortBy(_._2).map(_._2).min
  }
  
 }
