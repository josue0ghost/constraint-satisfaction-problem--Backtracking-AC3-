package org.url.edu;

import java.util.Map;

public class QueensProblemArc extends Arc<String, String>{

    private String casilla1, casilla2;

    public QueensProblemArc(String casilla1, String casilla2) {
        super(casilla1, casilla2);

        this.casilla1 = casilla1;
        this.casilla2 = casilla2;
    }
}