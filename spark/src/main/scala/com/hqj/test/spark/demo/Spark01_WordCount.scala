package com.hqj.test.spark.demo

import org.apache.spark.SparkConf

object Spark01_WordCount {

  def main(args: Array[String]): Unit = {

    new SparkConf().setMaster("local");
  }

}
