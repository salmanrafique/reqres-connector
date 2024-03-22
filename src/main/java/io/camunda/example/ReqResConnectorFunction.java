package io.camunda.example;

import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.error.ConnectorException;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.example.dto.ReqResConnectorRequest;
import io.camunda.example.dto.ReqResConnectorResult;

import org.hibernate.validator.internal.util.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OutboundConnector(
    name = "REQRESCONNECTOR",
    inputVariables = {"pagination", "page","pagination", "per_page"},
    type = "io.camunda:reqres:1")
@ElementTemplate(
    id = "io.camunda.connector.Template.v1",
    name = "Reqres Connector",
    version = 1,
    description = "Helps to connect to the reqres API",
    icon = "icon.svg",
    documentationRef = "https://reqres.in/",
    propertyGroups = {
      @ElementTemplate.PropertyGroup(id = "pagination", label = "Paginatioin"),
      @ElementTemplate.PropertyGroup(id = "compose", label = "Compose")
    },
    inputDataClass = ReqResConnectorRequest.class)
public class ReqResConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReqResConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) {
    final var connectorRequest = context.bindVariables(ReqResConnectorRequest.class);
    return executeConnector(connectorRequest);
  }

  private ReqResConnectorResult executeConnector(final ReqResConnectorRequest connectorRequest) {
    // TODO: implement connector logic
    LOGGER.info("Executing reqres connector with request {}", connectorRequest);
    int page = connectorRequest.page();
    int perPage = connectorRequest.per_page();
    if (page <1 ) {
      throw new ConnectorException("FAIL", "page value was " + page);
    }



    return new ReqResConnectorResult("Response received: " + page);
  }
}
