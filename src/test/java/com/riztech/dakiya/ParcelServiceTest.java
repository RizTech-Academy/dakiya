package com.riztech.dakiya;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelServiceTest {
    @Mock ParcelRepository repository;
    @InjectMocks ParcelService service;

    @Test
    void book_savesAParcelWithGeneratedTrackingCode() {
        when(repository.save(any(Parcel.class))).thenAnswer(inv -> inv.getArgument(0));
        Parcel p = service.book("Asha", "Pune");
        assertThat(p.getRecipientName()).isEqualTo("Asha");
        assertThat(p.getTrackingCode()).startsWith("PKG-");
        assertThat(p.getStatus()).isEqualTo(Parcel.Status.BOOKED);
    }

    @Test
    void inCity_delegatesToRepository() {
        when(repository.findByDestinationCity("Pune"))
            .thenReturn(List.of(new Parcel("PKG-1", "Asha", "Pune")));
        assertThat(service.inCity("Pune")).hasSize(1);
    }
}
