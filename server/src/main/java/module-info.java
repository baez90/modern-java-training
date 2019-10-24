module icndb.server {

    requires java.scripting;
    requires java.net.http;

    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires org.apache.commons.text;

    requires spark.core;
    requires com.google.gson;

    exports com.github.baez90.training.server.jokes;
    opens com.github.baez90.training.server.jokes to com.google.gson;
}