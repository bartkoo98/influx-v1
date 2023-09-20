package com.bartkoo98.influxv1.category;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CategoryDto {
    private Long id;
    private String name;
}
