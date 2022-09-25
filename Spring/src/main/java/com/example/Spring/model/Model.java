package com.example.Spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Model {

    private String name;
    private Double temp;
    private Double humidity;
    private String main;


}
