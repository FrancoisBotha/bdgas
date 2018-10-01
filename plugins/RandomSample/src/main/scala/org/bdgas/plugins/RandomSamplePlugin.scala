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
import org.apache.spark.sql.{Row, SparkSession}
import org.scalactic._
import spark.jobserver._
import spark.jobserver.api._

import scala.util.{Failure, Success, Try}


object RandomSamplePlugin extends SparkSessionJob with NamedObjectSupport {

  type JobData = Array[String]
  type JobOutput = Array[String]

  def runJob(sparkSession: SparkSession, runtime: JobEnvironment, data: JobData): JobOutput = {

    //**************
    //* PARAMETERS *
    //**************/
    val dummy1     = data(0) //Not used: File name
    val fileAlias  = data(1)
    val fraction   = data(2).toInt

    val frc = fraction.toDouble / 100.toDouble

    val a = sparkSession.sql(s"SELECT * FROM $fileAlias")
    val b = a.sample(false, frc)
    val c = b.toJSON

    c.collect()

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
