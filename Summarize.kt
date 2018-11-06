// Written by Jack Abdo
// TODO: Make sure all types match, eg not mixing arrays, lists, and mutable lists
import java.util.*
import java.io.File
import kotlin.math.log

class Article(_article:String) {

//  private var articlevar:String = ""

  var article:String = _article
  var mostcommonwords:List<String>
  var wordscore = HashMap<String,Int>()
  var summary:String = ""

  private fun fillmostcommonwords(): List<String>{ // 
    val filename: String = "mostcommonwords.txt"
    val _mostcommonwords = File(filename).readLines().toTypedArray().toList()
    return _mostcommonwords
  }
  init {
    mostcommonwords = fillmostcommonwords()
  }
  // cleans the punctuation and doublewhitespace from the input
  private fun removewhitespaceandpunctuation(textstring: String): String {
    val re = Regex("[^A-Za-z0-9 ]")
    var purestring = re.replace(textstring," ")
    while (purestring.contains("  "))
      purestring.replace("  "," ")
    return purestring
  }

  //retuns the number of words in a string
  //calls removewhiespaceandpunctuation
  private fun sentencelength(textstring: String): Int {
    val processedstring = removewhitespaceandpunctuation(textstring)
    val sentencesize = processedstring.split(" ").size
    return sentencesize
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
  fun cleanandcountwords(): HashMap<String,Int>  {
    val purestring: String = removewhitespaceandpunctuation(article)
    val wordlist:List<String> = purestring.split(" ")
    val wordscore:HashMap<String,Int> = wordfreq(wordlist)
    return wordscore
  }
  
  init {
  wordscore = cleanandcountwords()
  }
  
  //Sorts the dict by value and returns the values.
  fun sortwords(purestring: String): List<String> {
    var purearray: Array<String> = purestring.split(" ").toTypedArray()
    purearray.sort()
    return purearray.toList()
  }

  //returns the sentence score as a length three int array
  //first number is the score, taken by adding the frequencies of each word
  //second number is the sentence length
  //third is top 5 scoring words in the sentence
  //TODO: Redesign and return individual vars as list rather than packing immed
  fun sentencescore(sentence: String): List<Any>{
    val sentencearray = sentence.split(" ") //split sentence into single words
    //first is sentence score, second is size, third is top tag words
    var sentencescore = 0
    var tagcloud = mutableListOf("","","","","")
    for (i in sentencearray) {
      if (i in summary) //if the word is already in the summary, do not score it
        continue
      sentencescore += wordscore[i]!!//add the wordscore to the sentencescore
      var word = i //assign the word to 'word' so it can be added to the tagcld
      for (j in 0..tagcloud.size-1){//add word to tag cloud
        //if the slot is empty, place the word in the slot
        if (tagcloud[j] == "") {
          tagcloud[j] = word
          break
        }
        // if the score is greater than that word, push all words down a slot
        if (wordscore[word]!! > wordscore[tagcloud[j]]!!) {
          var tempword = tagcloud[j]
          tagcloud[j] = word
          word = tempword
          //if the index is at the last word, break
          if (j == tagcloud.size-1)
            break
          //or else move everything down
          for (k in j+1..tagcloud.size) {
            tempword = tagcloud[k]
            tagcloud[j] = word
            word = tempword
          }
          break
        }
      }
    }
    return listOf(sentencescore,sentencearray.size,tagcloud as List<Any>)
  }
  
  //assigns a score to each sentence, returns score, length, and top 5 tag words 
  fun countworddensity(): List<List<Any>> {
    var articlelist = article.split(".")
    var sentencevalues = mutableListOf<List<Any>>()
    var puresentence:String
    for (sentence in articlelist) {
      puresentence = removewhitespaceandpunctuation(sentence)
      sentencevalues.add(listOf(sentence,sentencescore(puresentence)))
    }
    return sentencevalues
  }
  
  //returns the top scoring sentence
  //calls: nothing
  fun gettopsentence(sentencevalues:List<List<Any>>): Int {
    var topsentence:Int = 0
    //checks the score of all the sentences, includes checking the zeroth element in case article is one sentence long
    for (i in sentencevalues.indices) 
      //checks each value
      if (sentencevalues[i][0] as Int > sentencevalues[topsentence][0] as Int)
        topsentence = i
    
    return topsentence
  }
  
  //select the top 1-3 sentences and loads them into summary
  //calls countworddensity, gettopsentence
  fun getshortsummary(){
    val summarylength = sentencelength(article)
    val finallength = 3*log(summarylength as Double,3.0) as Int
    while ( summary.split(" ").size < finallength ) {
       val newwordscore = countworddensity()
       summary += gettopsentence(newwordscore)
    }
  }
  
  init {
    getshortsummary()
  }
  //returns a shortened version of the article
  //TODO: Finish this in the future
  //TODO: Compare this with existing summary algorithms
  fun summarize() {
    throw NotImplementedError()
  }
  
}
