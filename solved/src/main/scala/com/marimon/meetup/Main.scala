package com.marimon.meetup

import com.marimon.meetup.providers.Hello
import spray.httpx.SprayJsonSupport._
import spray.routing.Directive.pimpApply
import spray.routing.SimpleRoutingApp
import spray.routing.directives.CompletionMagnet.fromObject
import com.marimon.meetup.providers.CDDVD
import com.marimon.meetup.providers.CDDVD
import com.marimon.meetup.providers.Group
import com.marimon.meetup.providers.Person
import com.marimon.meetup.providers.Song
import com.marimon.meetup.providers.Book
import spray.http.MultipartFormData
import spray.httpx.marshalling.MultipartMarshallers
import spray.http.MultipartContent
import com.marimon.meetup.providers.CDDVD

object Main extends App with SimpleRoutingApp {

  val u2 = Group("U2", Some(List(Person("Bono"), Person("TheEdge"))))
  val gorillaz = Group("Gorillaz", None)

  var stock =
    List(CDDVD(u2,
      "Joshua Tree",
      List(Song("Where the streets have no name", 338), Song("With or without you", 296))),
      CDDVD(gorillaz,
        "Demon Days",
        List(Song("Intro", 63), Song("Feel Good Inc.", 221))))

  import com.marimon.meetup.providers.MyBookStoreProtocol._
  startServer(interface = "localhost", port = 8080) {

    path("hello"){
      get{
        complete{
          "hello"
        }
      }
    } ~
      path("hello.json"){
        get{
          complete{
            import com.marimon.meetup.providers.MyProtocol._
            Hello("world")
          }
        }
      } ~
      path("books.json"){
        get{
          complete(stock)
        } ~
          post(
            entity(as[CDDVD[Group]]) { newItem =>
            // invoke using curl -H "Content-Type: application/json" -X POST -d @book-sample.json http://localhost:8080/books.json
            // use book-sample.json from src/main/resources
              complete {
                stock = newItem :: stock
                "added: " + newItem
              }
            }
          )
      }

  }

}
