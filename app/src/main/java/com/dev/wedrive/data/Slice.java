package com.dev.wedrive.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Slice {

    private int offset = 0;
    private int limit = 20;

    public Slice(int offset) {
        this.offset = offset;
    }

}
