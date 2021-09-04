package io.polygonal.plugin

class Logger implements io.polygonal.Logger.LoggerMethods {
    private final org.gradle.api.logging.Logger logger;

    Logger(org.gradle.api.logging.Logger logger) {
        this.logger = logger
    }

    @Override
    void info(String msg, Object... args) {
        logger.info(msg, args);
    }

    @Override
    void error(String msg, Object... args) {
        logger.error(msg, args);
    }

    @Override
    void warn(String msg, Object... args) {
        logger.warn(msg, args);
    }

    @Override
    void debug(String msg, Object... args) {
        logger.debug(msg, args);
    }
}
