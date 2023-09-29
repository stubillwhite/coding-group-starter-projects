package org.starter.testcommon;

import org.starter.Application;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class TestApplicationServer implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static boolean started = false;
    private static ConfigurableApplicationContext applicationContext = null;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            logger.info("Starting server");

            applicationContext = SpringApplication.run(Application.class);

            final String closeHookKey = this.getClass().getSimpleName() + "-close-hook";
            context.getRoot().getStore(GLOBAL).put(closeHookKey, this);
        }
    }

    @Override
    public void close() {
        logger.info("Stopping server");
        applicationContext.close();
    }
}