package starter.resources

import cats.effect.{Blocker, ContextShift, Sync}
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.Router
import org.http4s.server.staticcontent.resourceServiceBuilder
import starter.model.Ping
import starter.services.PingService

// See
// https://blog.rockthejvm.com/http4s-tutorial/
// https://github.com/http4s/http4s/blob/series/0.23/examples/src/main/scala/com/example/http4s/ExampleService.scala

object Resources {

  def httpApp[F[_] : Sync : ContextShift](blocker: Blocker, pingService: PingService): HttpApp[F] = {

    import io.circe.generic.auto._
    import org.http4s.circe.CirceEntityCodec._

    val apiRoutes: HttpRoutes[F] = {
      val dsl = new Http4sDsl[F] {}
      import dsl._

      HttpRoutes.of[F] {
        case req@POST -> Root / "ping" =>
          req.decode[Ping] { ping =>
            Ok(pingService.processPing(ping).asJson)
          }
      }
    }

    val staticContentRoutes: HttpRoutes[F] =
      resourceServiceBuilder("web", blocker).toRoutes

    val httpApp: HttpApp[F] =
      Router(
        "/api" -> apiRoutes,
        "/" -> staticContentRoutes
      ).orNotFound

    httpApp
  }
}
