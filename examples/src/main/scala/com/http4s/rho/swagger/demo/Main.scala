package com.http4s.rho.swagger.demo

import org.http4s.Service
import org.http4s.rho.swagger.SwaggerSupport
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}
import org.log4s.getLogger

import scalaz.concurrent.Task

object Main extends ServerApp {
  private val logger = getLogger

  val port = Option(System.getenv("HTTP_PORT"))
    .map(_.toInt)
    .getOrElse(8080)

  logger.info(s"Starting Swagger example on '$port'")

  def server(args: List[String]): Task[Server] = {
    val middleware = SwaggerSupport()

    BlazeBuilder
      .mountService(Service.withFallback(StaticContentService.routes)(MyService.toService(middleware)))
      .bindLocal(port)
      .start
  }
}
