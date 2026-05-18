package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorLoginResponse {

    private String token;

    private Long doctorId;

    private Long selectedChamberId;

    private List<ChamberSummary> chambers;

    @Getter
    @Setter
    public static class ChamberSummary {
        private Long id;
        private String chamberName;
    }
}
