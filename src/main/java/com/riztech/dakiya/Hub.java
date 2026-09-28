package com.riztech.dakiya;

import jakarta.persistence.*;

@Entity
public class Hub {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    @Version
    private Long version;      // optimistic locking

    protected Hub() {}
    public Hub(String name, String city) { this.name = name; this.city = city; }
    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCity() { return city; }
    public Long getVersion() { return version; }
}
