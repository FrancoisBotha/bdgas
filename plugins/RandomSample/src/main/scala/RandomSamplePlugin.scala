import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.scalactic._
import spark.jobserver.SparkSessionJob
import spark.jobserver.api.{JobEnvironment, SingleProblem, ValidationProblem}

import scala.util.Try


object RandomSamplePlugin extends SparkSessionJob {
  type JobData = Seq[String]
  type JobOutput = Long

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {
//    val s3aRdd = sc.textFile("s3a://sparkour-data/random_numbers.txt")

//    import org.apache.spark.sql.SQLContext
//    val sqlContext = new SQLContext(sc)
//    val spark = sqlContext.sparkSession
//    sc.parallelize(data).countByValue


      val df = sparkSession
          .read
          .option(" inferSchema", "false")
          .option(" header", "false")
          .csv("C:\\tmp\\data\\Invoices10K.txt")
      df.count()

  }

  def validate(sparkSession: SparkSession, runtime: JobEnvironment, config: Config):
  JobData Or Every[ValidationProblem] = {
    Try(config.getString("input.string").split(" ").toSeq)
      .map(words => Good(words))
      .getOrElse(Bad(One(SingleProblem("No input.string param"))))
  }
}
