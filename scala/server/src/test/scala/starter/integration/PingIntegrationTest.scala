package starter.integration

import cats.effect._
import io.circe.generic.auto._
import io.circe.syntax.EncoderOps
import org.http4s.circe._
import org.http4s.client.dsl.io._
import org.http4s.dsl.io.POST
import org.http4s.headers._
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{MediaType, Status}
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import starter.model.{Ping, Pong}
import starter.resources.Resources.httpApp
import starter.services.PingService

import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext.global

// See
// https://http4s.org/v0.20/client/

class PingIntegrationTest extends AnyFlatSpecLike with BeforeAndAfter with Matchers {

  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  behavior of "ping endpoint"

  it should "should return pong" in {
    // When
    val pingService = new PingService()

    val blockingPool = Executors.newFixedThreadPool(4)
    val blocker = Blocker.liftExecutorService(blockingPool)
    val app = httpApp[IO](blocker, pingService)

    val request = POST(
      uri"http://localhost:8080/api/ping",
      Accept(MediaType.application.json)
    ).withEntity(Ping("message", 23).asJson)

    // When
    val response = app.run(request).unsafeRunSync()

    // Then
    response.status should be(Status.Ok)
    val pong = response.decodeJson[Pong].unsafeRunSync()
    pong shouldEqual Pong("message", 23)
  }

}
