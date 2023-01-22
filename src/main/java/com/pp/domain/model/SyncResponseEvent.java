package com.pp.domain.model;

import lombok.Data;

@Data
public class SyncResponseEvent {
    private String correlationId;
    private String externalId;
    private ResponseEventResultCode resultCode;
}
