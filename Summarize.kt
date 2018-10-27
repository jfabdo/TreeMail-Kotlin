// Written by Jack Abdo

import java.util.*
import java.io.File

fun mostcommonwords(): List<String> { // 
  
  val filename: String = "mostcommonwords.txt"
  val mostcommonwords: Array<String> = File(filename).readLines().toTypedArray()
  return mostcommonwords.toList()
}

// cleans the punctuation and doublewhitespace from the article
fun removewhitespaceandpunctuation(textstring: String): String {
  val re = Regex("[^A-Za-z0-9 ]")
  var purestring = re.replace(textstring," ")
  while (purestring.contains("  "))
    purestring.replace("  "," ")
  
  return purestring
}

//Sorts the dict by value and returns the values.
fun sortwords(purestring: String): List<String> {
  var purearray: Array<String> = purestring.split(" ").toTypedArray()
  purearray.sort()
  
  return purearray.toList()
}

//returns the most frequently used words above severity threshold, calls sortwords and mostcommonwords
fun returntopwords(wordcount: Int) {
}

//takes a string of words and returns the word frequency
fun wordfreq(purearray:List<String>): HashMap<String,Int> {
  var wordscore:HashMap<String,Int> = HashMap<String,Int>()
  for (key in purearray) {
    if (wordscore.containsKey(key)) {
      var value:Int = wordscore[key] as Int
      wordscore.replace(key,(value+1)) 
    } else {
      wordscore.put(key,1)
    }
  }
  return wordscore
}

//wrapper for wordfreq, cleans text of punctuation and whitespace, calls whitespace and wordfreq
fun cleanandcountwords(textstring: String): Int  {
  
  return 0
}

// assigns a score to each sentence
fun countworddensity(article: String,wordcount: Int) {
}

fun summarize(article: String) {
}
