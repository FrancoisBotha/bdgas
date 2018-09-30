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
import spark.jobserver._
import spark.jobserver.api._
import scala.util.{Failure, Success, Try}


object SelectDataSourcePlugin extends SparkSessionJob with NamedObjectSupport {

  type JobData = Array[String]
  type JobOutput = Array[String]

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {

    //**************
    //* PARAMETERS *
    //**************/
    val filePath  = data(0)
    val fileDelim = data(1)
    val fileAlias = data(2)


    //Resolve delimiter based on code supplied
    //Eliminates problem where comma (,) is included in json, etc
    def matchDelimiter(x: String): String = x match {
      case "CM" => ","
      case "PI" => "|"
      case _ => "," //Default is comma
    }

    val df = sparkSession
      .read
      .option("delimiter", matchDelimiter(fileDelim))     // E.g. "|"
      .option("inferSchema", "true")
      .option("header", "true")
      .csv(filePath)                     //  E.g. .csv("file:///mnt/data/Invoices100.txt")

    df.createOrReplaceTempView(fileAlias)

    val a = sparkSession.sql("desc formatted " + fileAlias).toJSON

    a.collect()

  }

  //**************
  //* VALIDATION *
  //**************/

  // Documentation on Scalactic Or
  // http://doc.scalactic.org/3.0.1/#org.scalactic.Or
  def validate(sparkSession: SparkSession, runtime: JobEnvironment, config: Config):
  JobData Or Every[ValidationProblem] = {

    var returnVal:Array[String] = new Array[String](3)

    var validationProblem = false

    var errMsg = ""

    val input0 = "param0"
    val input1 = "param1"
    val input2 = "param2"

    def getParam(name: String): Try[String] = {
      Try(config.getString(name))
    }

    getParam(input0) match {
      case Success(value1) => {
        returnVal(0) = value1
      }
      case Failure(f) => {
        validationProblem = true
        errMsg = errMsg.concat("Input paramter 1 not supplied | ")
      }
    }

    getParam(input1) match {
      case Success(value2) => {
        returnVal(1) = value2
      }
      case Failure(f) => {
        validationProblem = true
        errMsg = errMsg.concat("Input paramter 2 not supplied | ")
      }
    }

    getParam(input2) match {
      case Success(value3) => {
        returnVal(2) = value3
      }
      case Failure(f) => {
        validationProblem = true
        errMsg = errMsg.concat("Input paramter 3 not supplied | ")
      }
    }

    if (!validationProblem) {
      Good(returnVal)
    } else {
      Bad(One(SingleProblem(errMsg)))
    }

  }

}



