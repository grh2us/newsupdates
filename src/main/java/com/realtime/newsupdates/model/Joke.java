package com.realtime.newsupdates.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Joke implements Serializable {
    private String setup;
    private String punchline;
}
