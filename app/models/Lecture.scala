package models

import org.joda.time.DateTime
import scala.util.Random

case class Lecture( id: Int, name: String, start: DateTime)

object Lecture {
  
  def listAll: List[Lecture] = List(Lecture(1,"Play with Scala", DateTime.now),
    Lecture(2, "Unix shell", DateTime.now()))
 
  def exists(id: Int) = id >= 1 && id <= 10
  
  def freeSeats(id: Int) = Random.nextInt
  
  def findById(id: Int) = Lecture(id, "play", DateTime.now())
}