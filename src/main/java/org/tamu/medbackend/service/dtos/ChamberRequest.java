package org.tamu.medbackend.service.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChamberRequest {
    private String chamberName;
    private String address;
    private String contactNumber;
    private String visitingHours;

}
