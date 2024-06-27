package com.ngleanhvu.shopapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
