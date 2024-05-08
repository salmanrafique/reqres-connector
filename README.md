# ReqRes Connector Example

A Camunda 8 example connector working with https://reqres.in/


> * [Element Template](./element-templates/template-connector.json)
> * [POM](./pom.xml) (artifact name, id, description)
> * [Connector Function](src/main/java/io/camunda/example/ReqResConnectorFunction.java)
> * [Service Provider Interface (SPI)](./src/main/resources/META-INF/services/io.camunda.connector.api.outbound.OutboundConnectorFunction)
> 
> Read more about [creating Connectors](https://docs.camunda.io/docs/components/connectors/custom-built-connectors/connector-sdk/#creating-a-custom-connector)
>
> Check out the [Connectors SDK](https://github.com/camunda/connector-sdk)


## Build

You can package the Connector by running:

```bash
mvn clean package
```

This will create the following artifacts:

- A thin JAR without dependencies.
- An fat JAR containing all dependencies, potentially shaded to avoid classpath conflicts. This will not include the SDK artifacts since those are in scope `provided` and will be brought along by the respective Connector Runtime executing the Connector.

### Shading dependencies

You can use the `maven-shade-plugin` defined in the [Maven configuration](./pom.xml) to relocate common dependencies
that are used in other Connectors and the [Connector Runtime](https://github.com/camunda-community-hub/spring-zeebe/tree/master/connector-runtime#building-connector-runtime-bundles).
This helps to avoid classpath conflicts when the Connector is executed. 

Use the `relocations` configuration in the Maven Shade plugin to define the dependencies that should be shaded.
The [Maven Shade documentation](https://maven.apache.org/plugins/maven-shade-plugin/examples/class-relocation.html) 
provides more details on relocations.

## API

### Input

| Name     | Description      | Example           | Notes                                                                      |
|----------|------------------|-------------------|----------------------------------------------------------------------------|
| page | controll page number to fetch    | `2`           |  Affects which entries will be returned |
| per_page    | Controll how many entries will be retruned per page | `3` | Affcets the numbe rof entries on a page and the result set size |

### Output

"Response received: " + http response body

Response Body: 
```json
{"page":2,"per_page":3,"total":12,"total_pages":4,"data":[{"id":4,"email":"eve.holt@reqres.in","first_name":"Eve","last_name":"Holt","avatar":"https://reqres.in/img/faces/4-image.jpg"},{"id":5,"email":"charles.morris@reqres.in","first_name":"Charles","last_name":"Morris","avatar":"https://reqres.in/img/faces/5-image.jpg"},{"id":6,"email":"tracey.ramos@reqres.in","first_name":"Tracey","last_name":"Ramos","avatar":"https://reqres.in/img/faces/6-image.jpg"}],"support":{"url":"https://reqres.in/#support-heading","text":"To keep ReqRes free, contributions towards server costs are appreciated!"}}
```

### Error codes

| Code | Description                                |
|------|--------------------------------------------|
| FAIL | returns errro message of errro caught      |

## Test locally

Run unit tests

```bash
mvn clean verify
```

### Test with local runtime

Use the [Camunda Connector Runtime](https://github.com/camunda-community-hub/spring-zeebe/tree/master/connector-runtime#building-connector-runtime-bundles) to run your function as a local Java application.

In your IDE you can also simply navigate to the `LocalContainerRuntime` class in test scope and run it via your IDE.
If necessary, you can adjust `application.properties` in test scope.

## Element Template

The element template for this sample connector is generated automatically based on the connector
input class using the [Element Template Generator](https://github.com/camunda/connectors/tree/main/element-template-generator/core).
The generation is embedded in the Maven build and can be triggered by running `mvn clean package`.

It is not mandatory to generate the element template for your connector and you can also create it manually.
However, the generator provides a convenient way to create the template and keep it in sync with the connector input class
and empowers you to prototype and iterate quickly.

The generated element template can be found in [element-templates/req-res-connector.json](./element-templates/req-res-connector.json).
