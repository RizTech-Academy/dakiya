package com.riztech.dakiya;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParcelDtos {
    public record CreateParcelRequest(
        @NotBlank String recipientName,
        @NotBlank @Size(min = 2, max = 60) String destinationCity
    ) {}

    public record ParcelResponse(Long id, String trackingCode, String recipientName,
                                 String destinationCity, String status) {
        static ParcelResponse from(Parcel p) {
            return new ParcelResponse(p.getId(), p.getTrackingCode(), p.getRecipientName(),
                                      p.getDestinationCity(), p.getStatus().name());
        }
    }
}
