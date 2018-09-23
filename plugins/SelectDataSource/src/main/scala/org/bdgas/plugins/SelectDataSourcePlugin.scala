package org.bdgas.plugins

import com.typesafe.config.Config
import org.apache.commons.lang.StringUtils
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.mortbay.util.ajax.JSON
import org.scalactic._
import spark.jobserver.SparkSessionJob
import spark.jobserver.api.{JobEnvironment, SingleProblem, ValidationProblem}

import scala.util.Try

object SelectDataSourcePlugin extends SparkSessionJob {
  type JobData = Seq[String]
  type JobOutput = Array[String]

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {

    val df = sparkSession
      .read
      .option("delimiter", "|")
      .option("inferSchema", "true")
      .option("header", "true")
      .csv("file:///mnt/data/Invoices100.txt")

    df.createOrReplaceTempView("dataFile")

    val a = sparkSession.sql("desc formatted dataFile").toJSON

    a.collect()

  }

  def validate(sparkSession: SparkSession, runtime: JobEnvironment, config: Config):
  JobData Or Every[ValidationProblem] = {
    Try(config.getString("filePath").split(" ").toSeq)
      .map(words => Good(words))
      .getOrElse(Bad(One(SingleProblem("No input.string param"))))
  }

}



