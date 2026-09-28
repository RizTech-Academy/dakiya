package com.riztech.dakiya;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    List<Parcel> findByDestinationCity(String destinationCity);
    List<Parcel> findByStatus(Parcel.Status status);

    @Query("select p from Parcel p join fetch p.hub")
    List<Parcel> findAllWithHub();
}
