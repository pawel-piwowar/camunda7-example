package com.pp.adapters.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pp.domain.model.SyncResponseEvent;
import com.pp.domain.ports.in.CustomerService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Component
public class SyncResponseReceiverAdapter {
    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    @Bean
    Consumer<String> syncResponseEvent() {
        return this::processEvent;
    }

    @SneakyThrows
    private void processEvent(String message) {
        log.info("Received the value {} in Consumer", message);
        SyncResponseEvent syncResponseEvent = objectMapper.readValue(message, SyncResponseEvent.class);
        customerService.processSyncResponseEvent(syncResponseEvent);
    }

}
