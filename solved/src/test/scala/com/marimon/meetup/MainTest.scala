package com.marimon.meetup

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import akka.actor.ActorSystem
import spray.can.client.HttpClient
import spray.client.HttpConduit
import spray.io.IOExtension
import akka.actor.Props
import spray.httpx.SprayJsonSupport
import spray.http._
import spray.util._
import scala.util.Success
import scala.util.Failure
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MainTest extends FlatSpec with ShouldMatchers {

  "A Main" should "create a JSON with a message when I GET /hello" in {

    implicit val system = ActorSystem()
    val ioBridge = IOExtension(system).ioBridge()
    val httpClient = system.actorOf(Props(new HttpClient(ioBridge)))

    val host = "localhost"
    val port = 8080

    val conduit = system.actorOf(
      props = Props(new HttpConduit(httpClient, host, port)),
      name = "http-conduit")

    val pipeline = HttpConduit.sendReceive(conduit)
    val response = pipeline(HttpRequest(method = HttpMethods.GET, uri = "/hello.json"))

    response onComplete {
      case Success(x) => {
        x should be ("""{
            "msg": "world"
            }""")
        system.shutdown
      }
      case Failure(y) => {
        println(y)
        system.shutdown
      }
    }
  }

  it should "also do it using rapture" in {
    import rapture.io._
    (Http./("localhost", 8080) / "hello.json").slurp[Char] should be ("{\n  \"msg\": \"world\"\n}")

  }
}