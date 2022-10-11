package com.lzb.demo.common.id.enity;

import lombok.Data;

@Data
public class LeafAlloc {
    private String key;
    private long maxId;
    private int step;
    private String updateTime;
}
