package org.starter.resource;

import org.starter.domain.Ping;
import org.starter.domain.Pong;
import org.starter.service.PingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping("/ping")
public class PingResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PingService pingService;

    @Inject
    public PingResource(PingService pingService) {
        this.pingService = pingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Pong create(@RequestBody Ping ping) {
        return pingService.processPing(ping);
    }
}
