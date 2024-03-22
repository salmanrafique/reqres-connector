package io.camunda.example.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.NotNull;

public record ReqResConnectorRequest(
    @NotNull @TemplateProperty(group = "pagination", type = PropertyType.Text) int page, 
    @NotNull @TemplateProperty(group = "pagination", type = PropertyType.Text) int per_page)
    {}
