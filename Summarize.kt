// Written by Jack Abdo
// TODO: Make this all an object, where the class is article
// TODO: Make sentencevalues, hashmap et al global
// TODO: Check if globals are empty, and if they are, fill them on the fly, but not in init
// TODO: Make 2d array an array of arrays and access array[0][0], eg
import java.util.*
import java.io.File
import kotlin.math.log

class Article(articlein:String) {

//  private var articlevar:String = ""

  var article:String = ""
  var mostcommonwords = List<String>()
  var wordscore = HashMap<String,Int>()
  var sentencevalues = List<Array<Any>>()
  var summary:String = ""
  
  init {
    article = articlein
  }

  private fun fillmostcommonwords() { // 
    val filename: String = "mostcommonwords.txt"
    mostcommonwordsvar = File(filename).readLines().toTypedArray().toList()
  }  
  
  mostcommonwords = fillmostcommonwords()
  
  // cleans the punctuation and doublewhitespace from the article
  fun removewhitespaceandpunctuation(textstring: String): String {
    val re = Regex("[^A-Za-z0-9 ]")
    var purestring = re.replace(textstring," ")
    while (purestring.contains("  "))
      purestring.replace("  "," ")
    return purestring
  }

  //retuns the number of words in a string
  //calls removewhiespaceandpunctuation
  fun sentencelength(textstring: String): Int {
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
  
  var wordscore = cleanandcountwords()
  
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
  //TODO: Turn Array into MutableList
  fun sentencescore(sentence: String): Array<Any>{
    val sentencearray = sentence.split(" ")
    var sentencescore:Array<Any> = arrayOf(
      arrayOf(0),
      arrayOf(sentencearray.size),
      arrayOf("","","","","") )
    for (i in sentencearray) {
      sentencescore[0] = wordcount[i]!!
      var word = i
      for (j in 0..sentencescore[2].size-1){
        //if the slot is empty, place the word in the slot
        if (sentencescore[2][j] == null) {
          sentencescore[2][j] = word
          break
        }
        // if the score is greater than that word, push all words down a slot
        if (wordcount[word]!! > wordcount[sentencescore[2][j]]!!) {
          var tempword = sentencescore[2][j]
          sentencescore[2][j] = word
          word = tempword
          //if the index is at the last word, break
          if (j == sentencescore[2].size-1)
            break
          //or else move everything down
          for (k in j+1..sentencescore[2].size) {
            tempword = sentencescore[2][k]
            sentencescore[2][j] = word
            word = tempword
          }
          break
        }
      }
    }
    return sentencescore
  }
  
  //assigns a score to each sentence, returns score, length, and top 5 tag words 
  fun countworddensity(): MutableList<List<Any>> {
    var articlelist = article.split(".")
    var sentencevalues = mutableListOf<List<Any>>()
    for (sentence in articlelist)
      sentencevalues.add(listOf(sentence,sentencescore(sentence,wordcount)))
    return sentencevalues
  }
  
  sentencevalues = countworddensity()
  
  //returns the top scoring sentence
  //calls: nothing
  fun gettopsentence(sentencevalues:MutableList<List<Any>>): Int {
    var topsentence:Int = 0
    //checks the score of all the sentences, includes checking the zeroth element
    for (i in sentencevalues.index): 
      //checks each value
      if (sentencevalues[i][0] > sentencevalues[topsentence][0])
        topsentence = i
    return topsentence
  }

  //rescores sententences by measuring against the existing summary.
  //
  fun rank_n-ary_sentences(
    summary:String,
    sentencevalues:List<Any>,
    wordcount)
      :List<List<Any>>{
    var sentencescores = sentencevalues as MutableList<MutableList>
  }

  //calls countworddensity
  //score "uniqueness" of a sentence as well, and return the best sentence and thenext most unique best sentence.
  //or maybe parse by topic?
  //tag cloud as well 
  //select the top 1-3 sentences
  //TODO:put in while loop that fills string until length is met
  //EX: while length < finallength, keep adding unique sentences
  //Create a mutable list that checks how similar it is to the existing summary
  fun getshortsummary(){//: Array<String>{
    val summarylength = sentencelength(article)
    val finallength = 3*log(summarylength,3)
    var wordcountmut = wordcount
    while ( summary.split(" ").size < finallength ) {
      var 
    }
  }

  // returns the most used words. will be depricated in final version
  fun returntopwords(wordcount: Int) {
    
  }

  fun summarize(article: String) {
  }
/*
  private var mostcommonwordsvar = List<String>()
  private var wordscorevar = HashMap<String,Int>()
  private var articlevar:String = ""
  private var summaryvar:String = ""
*/
  
}
