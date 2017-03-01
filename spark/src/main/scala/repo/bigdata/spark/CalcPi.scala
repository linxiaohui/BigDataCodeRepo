package repo.bigdata

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat

import scala.math.random

object CalcPi {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[5]").setAppName("SparkPi")
    val sc = new SparkContext(conf)
    val input = sc.newAPIHadoopFile[LongWritable, Text, TextInputFormat]("file://")
    val slices = if (args.length > 0) args(0).toInt else 3
    val n = math.min(1000000L * slices, Int.MaxValue).toInt
    // avoid overflow
    val count = sc.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x * x + y * y < 1) 1 else 0
    }.reduce(_ + _)
    println(4.0 * count / (n - 1))
    sc.stop()
  }
}