package starter.services

import starter.model.{Ping, Pong}

class PingService {
  def processPing(ping: Ping): Pong = {
    Pong(ping.message, ping.number)
  }
}
