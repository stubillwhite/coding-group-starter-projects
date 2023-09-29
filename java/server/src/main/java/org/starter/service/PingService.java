package org.starter.service;

import org.starter.domain.Ping;
import org.starter.domain.Pong;
import org.springframework.stereotype.Component;

@Component
public class PingService {

    public Pong processPing(Ping ping) {
        return new Pong(ping.message(), ping.number());
    }
}
