package org.url.edu;

import java.util.List;
import java.util.Map;

public class AustraliaColoringArc extends Arc<String, String>{

    private String place1, place2;

    public AustraliaColoringArc(String place1, String place2) {
        super(place1, place2);

        this.place1 = place1;
        this.place2 = place2;
    }

}
