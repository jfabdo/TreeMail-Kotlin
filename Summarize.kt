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

//takes a string of words and returns the word frequency
//accepts a string list and returns a HashMap
//calls: nothing
fun wordfreq(purearray:List<String>): HashMap<String,Int> {
  var wordscore:HashMap<String,Int> = HashMap<String,Int>()
  //increments the count if it exists in the hash table, otherwise adds it
  for (key in purearray) {
    if (wordscore.containsKey(key)) {// increments the count if there
      var value:Int = wordscore[key] as Int //
      wordscore.replace(key,(value+1)) 
    } else {
      wordscore.put(key,1)
    }
  }
  return wordscore
}

//wrapper for wordfreq, cleans punctuation and whitespace out of the text
//calls: whitespace and wordfreq
fun cleanandcountwords(textstring: String): HashMap<String,Int>  {
  val purestring: String = removewhitespaceandpunctuation(textstring)
  val wordlist:List<String> = purestring.split(" ")
  val wordscore:HashMap<String,Int> = wordfreq(wordlist)
  return wordscore
}

//Sorts the dict by value and returns the values.
fun sortwords(purestring: String): List<String> {
  var purearray: Array<String> = purestring.split(" ").toTypedArray()
  purearray.sort()
  
  return purearray.toList()
}

//returns the most frequently used words above severity threshold
//calls sortwords and mostcommonwords

fun returntopwords(wordcount: Int) {
}


// assigns a score to each sentence
fun countworddensity(article: String,wordcount: Int) {
}

fun summarize(article: String) {
}
