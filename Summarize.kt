// Written by Jack Abdo
// TODO: Make this all an object, where the class is article
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
fun returntop(wordcount:HashMap<String,Int>){}//,):

//returns the sentence score as a length three int array
//first number is the score, taken by adding the frequencies of each word
//second number is the sentence length
//third is top 5 scoring words in the sentence
//TODO: Turn Array into MutableList
fun sentencescore(sentence: String, wordcount: HashMap<String,Int>): Array<Any>{
  val sentencearray = sentence.split(" ")
  var sentencescore:Array<Any> = arrayOf(0,sentencearray.size,arrayOf("","","","",""))
  var taglist = sentencescore[2] as Array<String>
  for (i in sentencearray) {
    sentencescore[0] = wordcount[i]!!
    var word = i
    for (j in 0..taglist.size-1){
      //if the slot is empty, place the word in the slot
      if (taglist[j] == null) {
        taglist[j] = word
        break
      }
      // if the score is greater than that word, push all words down a slot
      if (wordcount[word]!! > wordcount[taglist[j]]!!) {
        var tempword = taglist[j]
        taglist[j] = word
        word = tempword
        //if the index is at the last word, break
        if (j == taglist.size-1)
          break
        //or else move everything down
        for (k in j+1..taglist.size) {
          tempword = taglist[k]
          taglist[j] = word
          word = tempword
        }
        break
      }
    }
  }
  return sentencescore
}

//assigns a score to each sentence
//depending on the length a score of 1 means non sentence summary
//score "uniqueness" of a sentence as well, and return the best sentence and thenext most unique best sentence.
//or maybe parse by topic?
//tag cloud as well 
//select the top 1-3 sentences
fun countworddensity(article: String,wordcount: HashMap<String,Int>): MutableList<List<Any>> {
  var articlelist = article.split(".")
  var sentencevalues = mutableListOf<List<Any>>()
  for (sentence in articlelist)
    sentencevalues.add(listOf(sentence,sentencescore(sentence,wordcount)))
  return sentencevalues
}

//returns a ln(n)/ln(x) length summary, with the most word coverage
//calls sentencescore
fun getlogsummary(article: String,wordcount: HashMap<String,Int>){//: Array<String>{
//  val sentencevalues = countworddensity(article)
}

// returns the most used words. will be depricated in final version
fun returntopwords(wordcount: Int) {
  
}

fun summarize(article: String) {
}

