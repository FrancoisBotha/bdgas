/*****************************************************************************
  * Copyright 2018 Francois Botha                                             *
  *                                                                           *
  * Licensed under the Apache License, Version 2.0 (the "License");           *
  * you may not use this file except in compliance with the License.          *
  * You may obtain a copy of the License at                                   *
  *                                                                           *
  * http://www.apache.org/licenses/LICENSE-2.0                                *
  *                                                                           *
  * Unless required by applicable law or agreed to in writing, software       *
  * distributed under the License is distributed on an "AS IS" BASIS,         *
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
  * See the License for the specific language governing permissions and       *
  * limitations under the License.                                            *
  *                                                                           *
  * ****************************************************************************/
package org.bdgas.plugins

import com.typesafe.config.Config
import org.apache.spark.sql.SparkSession
import org.scalactic._
import spark.jobserver.SparkSessionJob
import spark.jobserver.api._


object SelectDataSourcePlugin extends SparkSessionJob {
  type JobData = String
  type JobOutput = Array[String]

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {

    val df = sparkSession
      .read
      .option("delimiter", "|")
      .option("inferSchema", "true")
      .option("header", "true")
      .csv(data.toString)
//      .csv("file:///mnt/data/Invoices100.txt")

    df.createOrReplaceTempView("dataFile")

    val a = sparkSession.sql("desc formatted dataFile").toJSON

    a.collect()

  }

  def validate(sparkSession: SparkSession, runtime: JobEnvironment, config: Config):
  JobData Or Every[ValidationProblem] = {
    val path = config.getString("fileFullPath")
    Good(path)
//    Good(config.getString("fileFullPath"))
  }
}



