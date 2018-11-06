//Written by Jack Abdo

package com.jfabdo.article

import java.util.*
import java.io.File
import com.jfabdo.summarize

fun main(args: Array<String>) {
  val testfiles = listOf("testarticle.txt","testarticle2.txt","testarticle3.txt")
  for (i in testfiles) {
    val ourarticle = Article(i)
    println(ourarticle.summary)
  }
}
