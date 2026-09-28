package com.riztech.dakiya;

import jakarta.persistence.*;

@Entity
public class Parcel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String trackingCode;
    private String recipientName;
    private String destinationCity;
    @Enumerated(EnumType.STRING)
    private Status status = Status.BOOKED;

    @ManyToOne(fetch = FetchType.LAZY)      // lazy: the hub is not loaded until accessed
    private Hub hub;

    public enum Status { BOOKED, IN_TRANSIT, DELIVERED, CANCELLED }

    protected Parcel() {}
    public Parcel(String trackingCode, String recipientName, String destinationCity) {
        this.trackingCode = trackingCode;
        this.recipientName = recipientName;
        this.destinationCity = destinationCity;
    }
    public Long getId() { return id; }
    public String getTrackingCode() { return trackingCode; }
    public String getRecipientName() { return recipientName; }
    public String getDestinationCity() { return destinationCity; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Hub getHub() { return hub; }
    public void setHub(Hub hub) { this.hub = hub; }
}
