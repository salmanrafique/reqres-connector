package io.camunda.example.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.Positive;

public record RandomJokeConnectorRequest(
    @Positive @TemplateProperty(group = "joke", type = PropertyType.Text) String joke)
    {}
