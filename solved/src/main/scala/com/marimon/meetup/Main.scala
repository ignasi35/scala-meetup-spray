package com.marimon.meetup

import com.marimon.meetup.providers.Hello
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import spray.routing.Directive.pimpApply
import spray.routing.SimpleRoutingApp
import spray.routing.directives.CompletionMagnet.fromObject
import com.marimon.meetup.providers.CDDVD
import com.marimon.meetup.providers.CDDVD
import com.marimon.meetup.providers.Group
import com.marimon.meetup.providers.Person
import com.marimon.meetup.providers.Song
import com.marimon.meetup.providers.Book

object Main extends App with SimpleRoutingApp {

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
          complete{
            import com.marimon.meetup.providers.MyBookStoreProtocol._
            val u2 = Group("U2", Some(List(Person("Bono"), Person("TheEdge"))))
            val gorillaz = Group("Gorillaz", None)

            List(CDDVD(u2,
              "Joshua Tree",
              List(Song("track1", 165), Song("track 2", 230))),
              CDDVD(gorillaz,
                "Clint Eastwood",
                List(Song("track3 ", 165), Song("track 4", 230))))

          }
        }
      }

  }

}
