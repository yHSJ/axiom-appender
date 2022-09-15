# Axiom Appender

This custom logback appender sends logs to Axiom. It was built and tested with Logback-Core version 1.2.3 and relies on logstash-logback-encoder version 7.2. Requires Java 11.

### Getting Started

To get started, you'll need to add this library as a dependency via Maven/Gradle.

 With Gradle:
```
dependencies {
    implementation('io.github.yhsj:axiom-appender:1.0.2')
}

repositories {
    mavenCentral() 
}
```
### logback.xml Example Configuration
```xml
<configuration>
    <appender name="axiom" class="io.github.yhsj.axiom.AxiomAppender">
        <endpointUrl>The Endpoint URL</endpointUrl>
        <apiToken>your API Token</apiToken>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
    </appender>

    <root level="debug">
        <appender-ref ref="axiom"/>
    </root>
</configuration>
```

### Parameters
| Parameter     | Default | Explained                                                                                                                | Required |
|---------------|---------|--------------------------------------------------------------------------------------------------------------------------|----------|
| `endpointUrl` | _None_  | The endpoint URL for your axiom Dataset. Can be found after creating a new Dataset and going to "stream"                 | `TRUE`   |   
| `apiToken`    | _None_  | The API Token you've created for your Axiom account. This can be found, or generated, under "settings" and "API Tokens"  | `TRUE`   |


### Example Usage
```java
public class Main {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        LOGGER.info("Hello World!");
    }
}
```
