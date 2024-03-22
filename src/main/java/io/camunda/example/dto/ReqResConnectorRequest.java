package io.camunda.example.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.Positive;

public record ReqResConnectorRequest(
    @Positive @TemplateProperty(group = "pagination", type = PropertyType.Text) int page, 
    @Positive @TemplateProperty(group = "pagination", type = PropertyType.Text) int per_page)
    {}
