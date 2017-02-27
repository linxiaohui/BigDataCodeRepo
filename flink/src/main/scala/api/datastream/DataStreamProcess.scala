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

    val input = env.socketTextStream("localhost", 1234)
    val out = input.map((x:String)=> x.toInt)

    env.execute("Window Stream WordCount")

  }
}