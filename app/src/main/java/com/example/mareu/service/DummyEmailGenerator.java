package com.example.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyEmailGenerator {

    public static List<String> DUMMY_EMAIL = Arrays.asList(
            "maxime@lamzone.com",
            "alex@lamzone.com",
            "paul@lamzone.com",
            "viviane@lamzone.com",
            "amandine@lamzone.com",
            "luc@lamzone.com"
    );

    static List<String> generateEmail(){return new ArrayList<>(DUMMY_EMAIL);}
}
