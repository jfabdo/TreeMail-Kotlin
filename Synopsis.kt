// Written by Jack Abdo

package com.jfabdo.summarize

import java.util.*
import java.io.File
import kotlin.math.log
import kotlin.math.round
import kotlin.system.exitProcess

fun main(args: Array<String>) {
  if (args.size == 0) {
    println("Please specify a file.")
    exitProcess(0)
  }
  for (i in args) {
    val inputarticle = Article(File(i).readText())
    println(inputarticle.tagcloud)
    println(inputarticle.synopsis+"\n\n\n")
    //println(inputarticle.wordscore)
  }
}

class Article(_article:String) {

//  private var articlevar:String = ""

  var article:String = _article
  private var ignorelist:List<String>
  var wordscore = HashMap<String,Int>()
  var synopsis:String = ""
  var tagcloud:List<String> = listOf()

  private fun fillignorelist(): List<String>{ // 
    val filename: String = "ignorelist.txt"
    val _ignorelist = File(filename).readLines().toTypedArray().toList()
    return _ignorelist
  }
  init {
    ignorelist = this@article.fillignorelist()
  }
  // cleans the punctuation and doublewhitespace from the input
  private fun removewhitespaceandpunctuation(textstring: String): String {
    val re = Regex("[^A-Za-z0-9 ']")
    var purestring = re.replace(textstring," ")
    while (purestring.contains("  "))
      purestring = purestring.replace("  "," ")
    return purestring
  }

  //retuns the number of words in a string
  //calls removewhiespaceandpunctuation
  private fun sentencelength(textstring: String): Int {
    val processedstring = this@article.removewhitespaceandpunctuation(textstring)
    val sentencesize = processedstring.split(" ").size
    return sentencesize
  }
  //string cleaner, all strings should be referred to through here
  private fun strcln(upperstring:String):String{
    return upperstring.toLowerCase()
  }
  //takes a string of words and returns the word frequency
  //accepts a string list and returns a HashMap
  //calls: nothing
  private fun wordfreq(purearray:List<String>): HashMap<String,Int> {
    var wordscore:HashMap<String,Int> = HashMap<String,Int>()
    //increments the count if it exists in the hash table, otherwise adds it
    for (key in purearray) {
      if (key in ignorelist)
        continue
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
  private fun cleanandcountwords(): HashMap<String,Int>  {
    val purestring: String = this@article.removewhitespaceandpunctuation(article).toLowerCase()
    val wordlist:List<String> = purestring.split(" ")
    val wordscore:HashMap<String,Int> = this@article.wordfreq(wordlist)
    return wordscore
  }
  
  init {
  wordscore = this@article.cleanandcountwords()
  }
  
  //Sorts the dict by value and returns the values.
  private fun sortwords(purestring: String): List<String> {
    var purearray: Array<String> = purestring.split(" ").toTypedArray()
    purearray.sort()
    return purearray.toList()
  }
  private fun wordscore(wordtoscore:String): Int {
    val wordtoscorelower = wordtoscore.toLowerCase()
    return wordscore[wordtoscorelower]!!
  }
  //returns the sentence score as a length three int array
  //first number is the score, taken by adding the frequencies of each word
  //second number is the sentence length
  //third is top 5 scoring words in the sentence
  private fun sentencescore(sentence: String): List<Any>{
    val sentencearray = sentence.split(" ") //split sentence into single words
    //first is sentence score, second is size, third is top tag words
    var sentencescore = 0
    var tagcloud = mutableListOf<String>("","","","","")
    for (ii in sentencearray) {
      val i = strcln(ii)
      //if the word is already in the synopsis, do not score it
      if (i in tagcloud || i in synopsis.toLowerCase() || i in ignorelist) {
        continue
      }
      sentencescore += this@article.wordscore(i)//add the wordscore to the sentencescore
      var word = i //assign the word to 'word' so it can be added to the tagcld
      for (j in 0..tagcloud.size-1){//add word to tag cloud
        //if the slot is empty, place the word in the slot
        if (tagcloud[j] == "") {
          tagcloud[j] = word
          break
        }
        // if the score is greater than that word, push all words down a slot
        if (this@article.wordscore(word) > this@article.wordscore(tagcloud[j])) {
          var tempword = tagcloud[j] //swap word and tagcloud[j]
          tagcloud[j] = word
          word = tempword
          if (j == tagcloud.size-1) //if the index is at the last word, break
            break
          for (k in j+1..tagcloud.size-1) { //or else move everything down
            tempword = tagcloud[k]
            tagcloud[k] = word
            word = tempword
          }
          break
        }
      }
    }
    return listOf(sentencescore,sentencearray.size,tagcloud as List<String>)
  }
  
  //assigns a score to each sentence, returns score, length, and top 5 tag words 
  private fun countworddensity(): List<List<Any>> {
    var articlelist = article.split(".")
    var sentencevalues = mutableListOf<List<Any>>()
    var puresentence:String
    for (sentence in articlelist) {
      puresentence = this@article.removewhitespaceandpunctuation(sentence)
      this@article.sentencevalues.add(listOf(sentence+". ") + this@article.sentencescore(puresentence))
    }
    return sentencevalues
  }
  
  //returns the top scoring sentence
  //calls: nothing
  private fun gettopsentence(sentencevalues:List<List<Any>>): List<Any> {
    var topsentence:Int = 0
    //checks the score of all the sentences, includes checking the zeroth element in case article is one sentence long
    for (i in sentencevalues.indices)
      //checks each value
      if (sentencevalues[i][1] as Int > sentencevalues[topsentence][1] as Int)
        topsentence = i
    return listOf(sentencevalues[topsentence][0],sentencevalues[topsentence][3])
  }
  
  //select the top 1-3 sentences and loads them into synopsis
  //calls countworddensity, gettopsentence
  private fun getshortsynopsis(){
    val synopsislength = this@article.sentencelength(article)
    val finallength = round(10*log(synopsislength + 0.0,3.0)).toInt()
    while ( synopsis.split(" ").size < finallength + 2 ) {
       val newwordscore = this@article.countworddensity()
       val result = this@article.gettopsentence(newwordscore)
       synopsis += result[0]
       tagcloud += result[1] as List<String>
    }
  }
  
  init {
    this@article.getshortsynopsis()
  }
  //returns a shortened version of the article
  //TODO: Finish this in the future
  //TODO: Compare this with existing synopsis algorithms
  fun longsynopsis() {
    throw NotImplementedError()
  }
}
