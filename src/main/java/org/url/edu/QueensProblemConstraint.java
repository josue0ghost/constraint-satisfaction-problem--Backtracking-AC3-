package org.url.edu;

import java.util.List;
import java.util.Map;

public class QueensProblemConstraint  extends Constraint<String, String> {

    private String casilla1, casilla2;

    public QueensProblemConstraint(String place1, String place2) {
        super(List.of(place1, place2));

        this.casilla1 = place1;
        this.casilla2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
        // Must check that the variable isn't assigned yet
        if (!assignment.containsKey(casilla1) || !assignment.containsKey(casilla2)) {
            // is consistent
            return true;
        }

        // place1 != place2
        // no pueder rojo con rojo
        var c1 = assignment.get(casilla1);
        var c2 = assignment.get(casilla2);

        if (c1.equals(c2)) {
            if (c1 == "Queen") {
                return false;
            }
        }
        return true;
        // if true, it is consistent
        //return !assignment.get(casilla1).equals(assignment.get(casilla2));
    }
}