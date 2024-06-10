package io.camunda.example;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.example.dto.RandomJokeConnectorRequest;
import io.camunda.example.dto.ReqResConnectorResult;
import io.camunda.example.RandomJokeConnectorFunction;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "RANDOMJOKECONNECTOR", 
    inputVariables = {"county"},
    type = "io.camunda:reqres:1")
@ElementTemplate(
    id = "io.camunda.connector.ReqRes.v1",
    name = "RandomJoke Connector",
    version = 1,
    description = "Helps to connect to the RandomJoke API",
    icon = "icon.svg",
    documentationRef = "https://official-joke-api.appspot.com/random_joke",
    propertyGroups = {
      @ElementTemplate.PropertyGroup(id = "joke", label = "joke")
    },
    inputDataClass = RandomJokeConnectorRequest.class)
public class RandomJokeConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(RandomJokeConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) {
    final var connectorRequest = context.bindVariables(RandomJokeConnectorRequest.class);
    return executeConnector(connectorRequest);
  }

  private ReqResConnectorResult executeConnector(final RandomJokeConnectorRequest connectorRequest) {
    LOGGER.info("Executing reqres connector with request {}", connectorRequest);
    String randomJoke = connectorRequest.joke();
    LOGGER.info("randomJoke", randomJoke);

    if (randomJoke !=null && randomJoke.length() <1 ) {
      throw new ConnectorException("FAIL", "country value was " + randomJoke);
    }

    URI uri = URI.create("https://official-joke-api.appspot.com/" + randomJoke); 
    HttpRequest httpRequest = HttpRequest.newBuilder() 
        .GET() 
        .uri(uri) 
        .build();
 
    try (HttpClient httpClient = HttpClient.newHttpClient()) { 
     
      HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
      LOGGER.info("Status Code: " + httpResponse.statusCode()); 
      LOGGER.info("Response Body: " + httpResponse.body()); 

      return new ReqResConnectorResult("Response received: " + httpResponse.body());

    } catch (Exception e) { 
      e.printStackTrace();
      throw new ConnectorException("FAIL", e.getMessage());
    } 


   
  }
}
