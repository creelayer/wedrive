package com.dev.wedrive.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pager {

    private int page = 1;
    private int perPage = 20;

    public Pager(int count) {
        page = (count - (count % perPage)) / perPage + 1;
    }

}
