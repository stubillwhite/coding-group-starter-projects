package starter

import cats.effect._
import org.http4s.server.blaze._
import org.http4s.server.middleware._
import starter.resources.Resources.httpApp
import starter.services.PingService

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext.global

object StarterApplication extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val port = 8080
    val host = "localhost"

    val pingService = new PingService()

    val blockingPool = Executors.newFixedThreadPool(4)
    val blocker = Blocker.liftExecutorService(blockingPool)
    val app = httpApp[IO](blocker, pingService)

    BlazeServerBuilder[IO](global)
      .bindHttp(port, host)
      .withHttpApp(CORS(app))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
} 