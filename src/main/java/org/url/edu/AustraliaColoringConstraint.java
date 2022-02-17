package org.url.edu;

import java.util.List;
import java.util.Map;

public class AustraliaColoringConstraint extends Constraint<String, String>{

    private String place1, place2;

    public AustraliaColoringConstraint(String place1, String place2) {
        super(List.of(place1, place2));

        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        // Must check that the variable isn't assigned yet
        if (!assignment.containsKey(place2) || !assignment.containsKey(place1)) {
            // is consistent
            return true;
        }

        // place1 != place2
        // if true, it is consistent
        return !assignment.get(place1).equals(assignment.get(place2));
    }
}
