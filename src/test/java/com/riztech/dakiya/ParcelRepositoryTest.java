package com.riztech.dakiya;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParcelRepositoryTest {
    @Autowired ParcelRepository repository;

    @Test
    void findByStatus_returnsMatching() {
        repository.save(new Parcel("PKG-1", "Asha", "Pune"));
        repository.save(new Parcel("PKG-2", "Bhavna", "Mumbai"));
        List<Parcel> booked = repository.findByStatus(Parcel.Status.BOOKED);
        assertThat(booked).hasSize(2);
        assertThat(repository.findByDestinationCity("Pune")).hasSize(1);
    }
}
