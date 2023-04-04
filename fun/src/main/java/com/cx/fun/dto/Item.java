package com.cx.fun.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
public class Item {
    private String id;
    private String info;
}
