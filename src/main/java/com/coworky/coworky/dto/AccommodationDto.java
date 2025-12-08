package com.coworky.coworky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AccommodationDto {

    private Long id;
    private String name;
    private String address;
    private Integer capacity;
    private String description;
    private Integer price;
    private Set<TagDto> tags;

    @Data
    @AllArgsConstructor
    public static class TagDto {
        private String code;
        private String label;
    }
}
