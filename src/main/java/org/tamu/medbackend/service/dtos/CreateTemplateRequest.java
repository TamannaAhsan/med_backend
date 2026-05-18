package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTemplateRequest {

    private String title;

    private String content;

    private Boolean testTemplate;

    private Long doctorId;

    private Long chamberId;
}
