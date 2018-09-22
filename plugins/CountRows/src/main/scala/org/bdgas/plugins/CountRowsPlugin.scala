package org.bdgas.plugins

import com.typesafe.config.Config
import org.apache.spark.sql.{SparkSession}
import org.scalactic._
import spark.jobserver.SparkSessionJob
import spark.jobserver.api.{JobEnvironment, SingleProblem, ValidationProblem}

import scala.util.Try

object CountRowsPlugin  extends SparkSessionJob {
  type JobData = Seq[String]
  type JobOutput = Long

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {

    val df = sparkSession
      .read
      .option(" inferSchema", "true")
      .option(" header", "true")
      .csv("file:///mnt/data/Invoices100.txt")

    df.count()

  }

  def validate(sparkSession: SparkSession, runtime: JobEnvironment, config: Config):
  JobData Or Every[ValidationProblem] = {
    Try(config.getString("input.string").split(" ").toSeq)
      .map(words => Good(words))
      .getOrElse(Bad(One(SingleProblem("No input.string param"))))
  }
}