package com.marimon.meetup.providers

import spray.json.DefaultJsonProtocol
import spray.json.JsonFormat
import spray.json.NullOptions

class Item(name : String) {}
case class CDDVD[T](author : T, name : String, songs : List[Song])
case class Book(author : Person, name : String, editor : String, pages : Int)
trait Author
case class Person(name : String) extends Author
case class Group(name : String, members : Option[List[Person]]) extends Author
case class Song(name : String, durationSeconds : Int)

object MyBookStoreProtocol extends DefaultJsonProtocol with NullOptions {
  implicit val songFormat = jsonFormat2(Song)
  implicit val personFormat = jsonFormat1(Person)
  implicit val groupFormat = jsonFormat2(Group)
  implicit val bookFormat = jsonFormat4(Book)
  implicit def cdFormat[A : JsonFormat] = jsonFormat3(CDDVD.apply[A])
}

case class Hello(msg : String)

object MyProtocol extends DefaultJsonProtocol {
  implicit val helloFormat = jsonFormat1(Hello)
}