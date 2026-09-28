package com.riztech.dakiya;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import com.riztech.dakiya.ParcelDtos.CreateParcelRequest;
import com.riztech.dakiya.ParcelDtos.ParcelResponse;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
    private final ParcelRepository repository;

    public ParcelController(ParcelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ParcelResponse> list(@RequestParam(required = false) String city) {
        List<Parcel> parcels = (city == null)
            ? repository.findAll()
            : repository.findByDestinationCity(city);
        return parcels.stream().map(ParcelResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ParcelResponse getOne(@PathVariable Long id) {
        Parcel p = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("No parcel with id " + id));
        return ParcelResponse.from(p);
    }

    @PostMapping
    public ResponseEntity<ParcelResponse> create(@Valid @RequestBody CreateParcelRequest req,
                                                 UriComponentsBuilder uri) {
        Parcel p = new Parcel("PKG-" + UUID.randomUUID().toString().substring(0, 8),
                              req.recipientName(), req.destinationCity());
        p = repository.save(p);
        var location = uri.path("/api/parcels/{id}").buildAndExpand(p.getId()).toUri();
        return ResponseEntity.created(location).body(ParcelResponse.from(p));  // 201 + Location
    }
}
