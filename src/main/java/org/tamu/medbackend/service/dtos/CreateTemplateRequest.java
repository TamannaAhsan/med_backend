package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTemplateRequest {

    private String title;

    private String content;

    private String description;

    private Boolean testTemplate;

    private Long doctorId;

    private Long chamberId;
}
