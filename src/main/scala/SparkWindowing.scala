import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent

object SparkWindowing {
 def main(args:Array[String]) = {
   val ss = SparkSession.builder().appName("Spark Window Streaming").getOrCreate()
   val sc = new StreamingContext(ss.sparkContext,Seconds(1))
   val topics  = args(0).split(",").toSet
   val kafkaParams = Map[String,Object](ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
       ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer],
       ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG->classOf[StringDeserializer],
       ConsumerConfig.AUTO_OFFSET_RESET_CONFIG->"latest",
       ConsumerConfig.GROUP_ID_CONFIG->"windows",
       ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG->(false:java.lang.Boolean))
       val dstream = KafkaUtils.createDirectStream(sc, PreferConsistent, 
       Subscribe[String,String](topics, kafkaParams))
       val s = dstream.map(_.value()).flatMap(x=>x.split(" ")).map(x=>(x,1)).reduceByKeyAndWindow({(x:Int,y:Int)=>x+y}, Seconds(60), Seconds(5))
       s.print()
       sc.start()
       sc.awaitTermination()
 } 
}