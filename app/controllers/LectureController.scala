package controllers

import play.api.mvc.Controller
import play.api.mvc.Action
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import models.Lecture
import models.Participant
import play.api.data.validation.Constraint
import play.api.data.validation.Valid
import play.api.data.validation.Invalid
import views.html.defaultpages.badRequest

object LectureController extends Controller {

  val form: Form[Participant] = Form (
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "lectureId" -> number.verifying("lecture.invalid", Lecture.exists(_)).verifying("lecture.no.seats", Lecture.freeSeats(_) > 0)
    )(Participant.apply)(Participant.unapply)
  )
  
  def index = Action { implicit request =>
    Logger.debug("Wyswietalm liste kursów")
    Ok(views.html.lecture.list(
        Lecture.listAll
    ))
  }
  
  def startEnroll(id: Int) = Action {
    Ok(views.html.lecture.enroll(
        form.fill(null),
        Lecture.findById(id)
      )
    )
  }
  
  def saveEnroll(id: Int) = Action { implicit request =>
    form.bindFromRequest.fold(
      errors    => BadRequest(views.html.lecture.enroll(
                     errors, 
                     Lecture.findById(id)
    		       )),
      succcess 	=> Redirect(routes.LectureController.index).flashing("message" -> "Nie zapomnij przyjść")
    )
  }
  
}