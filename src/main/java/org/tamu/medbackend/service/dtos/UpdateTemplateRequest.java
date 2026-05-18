package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTemplateRequest {

    private String title;

    private String content;

    private String description;

    private Boolean testTemplate;

    private Boolean active;
}