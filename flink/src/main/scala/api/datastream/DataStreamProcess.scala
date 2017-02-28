package api.datastream

import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.extensions._
import org.apache.flink.streaming.api.windowing.time.Time

object DataStreamProcess {
  def main(args: Array[String]) {
    println("DataStream Example")

    //Obtain Execution environment
    /* create a local/remote environment
    val local = ExecutionEnvironment.createLocalEnvironment()
    val remote = ExecutionEnvironment.createRemoteEnvironment("locahost",9300,"a.jar")
    */
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val env2 = StreamExecutionEnvironment.getExecutionEnvironment

    val input = env.socketTextStream("localhost", 1234)
    val out = input.map { x=>
      val in=x.split(" ")
      (in(0), in(1).toInt)
    }.keyBy(_._1).reduce { (x,y) =>
      (x._1, x._2+y._2)
    }
    out.print()
    val input2 = env2.socketTextStream("localhost",12345)
    val out2 = input2.map(x=>(1,x.toInt)).keyBy(_._1).reduce{ (x,y) =>
      (x._1,x._2+y._2)
    }.map(_._2)
    out2.print()
    //env.execute("Stream WordCount") // Will Nerver Return ?
    env2.execute("Stream WC")
  }
}