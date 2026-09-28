package com.riztech.dakiya;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ParcelService {
    private final ParcelRepository repository;
    public ParcelService(ParcelRepository repository) { this.repository = repository; }

    public Parcel book(String recipientName, String destinationCity) {
        Parcel p = new Parcel("PKG-" + UUID.randomUUID().toString().substring(0, 8),
                              recipientName, destinationCity);
        return repository.save(p);
    }

    public List<Parcel> inCity(String city) {
        return repository.findByDestinationCity(city);
    }
}
