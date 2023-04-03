package com.cx.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
//@Jacksonized
@AllArgsConstructor
//@NoArgsConstructor
public class Item {

    private String id;
    private String info;

}

