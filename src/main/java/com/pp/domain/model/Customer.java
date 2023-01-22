package com.pp.domain.model;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String externalId;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
    private SyncStatus syncStatus = SyncStatus.NOT_SYNCED;
    private String notes;
}
