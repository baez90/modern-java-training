package com.github.baez90.training.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public abstract class Program {
    private Program() {
    }

    private static final Logger logger = LoggerFactory.getLogger(Program.class);

    public static void main(String[] args) {
        path("/api", () -> {
            before("/*", (q, a) -> logger.debug(String.format("Path: %s", q.pathInfo())));

            get("/hello/:name", (req, res) -> String.format("Hello, %s", req.params(":name")));
        });
    }
}
