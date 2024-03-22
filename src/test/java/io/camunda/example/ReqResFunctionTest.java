package io.camunda.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.connector.api.error.ConnectorInputException;
import io.camunda.connector.test.outbound.OutboundConnectorContextBuilder;
import io.camunda.example.dto.ReqResConnectorRequest;
import io.camunda.example.dto.ReqResConnectorResult;
import org.junit.jupiter.api.Test;

public class ReqResFunctionTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturnReceivedResultWhenExecute() throws Exception {
    // given
    var input = new ReqResConnectorRequest(
            2,3
    );
    var function = new ReqResConnectorFunction();
    var context = OutboundConnectorContextBuilder.create()
      .variables(objectMapper.writeValueAsString(input))
      .build();
    // when
    var result = function.execute(context);
    // then
    assertThat(result)
      .isInstanceOf(ReqResConnectorResult.class)
      .extracting("response")
      .asString().contains("\"page\":2,\"per_page\":3,\"total\":12");
  }

  @Test
  void shouldThrowWithErrorCodeWhenPageZero() throws Exception {
    // given
    var input = new ReqResConnectorRequest(0, 3);
    var function = new ReqResConnectorFunction();
    var context = OutboundConnectorContextBuilder.create()
        .variables(objectMapper.writeValueAsString(input))
        .build();
    // when
    var result = catchThrowable(() -> function.execute(context));
    // then
    assertThat(result)
        .isInstanceOf(ConnectorInputException.class)
        .hasMessageContaining("page: Validation failed");
  }
}