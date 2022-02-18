package org.url.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        /*
        // Variables
        List<String> variables = List.of("Western Australia", "Northern Territory", "Queensland", "South Australia",
                "New South Wales", "Victoria", "Tasmania");

        // Domains
        Map<String, List<String>> domains = new HashMap<>();
        for (var variable: variables) {
            domains.put(variable, List.of("R", "G", "B"));
        }
        */

        // Variables
        List<String> variables = List.of(
                "A1", "A2", "A3", "A4",
                "B1", "B2", "B3", "B4",
                "C1", "C2", "C3", "C4",
                "D1", "D2", "D3", "D4"
        );

        // Domains
        Map<String, List<String>> domains = new HashMap<>();
        for (var variable: variables) {
            domains.put(variable, List.of("Queen", "Blocked"));
        }


        // CSP
        CSP<String, String> problem = new CSP<>(variables, domains);

        String[] rows = {"A", "B", "C", "D"};
        String[] columns = {"1", "2", "3", "4"};

        for (int col = 0; col < columns.length; col++) {
            for (int row = 0; row < rows.length; row ++) {

                // linea horizontal
                for (int i = 0; i < rows.length; i++) {
                    if (row != i) {
                        problem.addConstraint(new QueensProblemConstraint(rows[row]+columns[col], rows[i]+columns[col]));
                        problem.addArc(rows[row]+columns[col], rows[i]+columns[col]);
                    }
                }

                // linea vertical
                for (int i = 0; i < columns.length; i++) {
                    if (col != i) {
                        problem.addConstraint(new QueensProblemConstraint(rows[row] + columns[col], rows[row] + columns[i]));
                        problem.addArc(rows[row]+columns[col], rows[row]+columns[i]);
                    }
                }

                // diagonal 1 -> v
                var dRow = row;
                for (int i = col; i < columns.length; i++) {
                    if (col != i && dRow < rows.length) {
                        problem.addConstraint(new QueensProblemConstraint(rows[dRow]+columns[col], rows[dRow]+columns[i]));
                        problem.addArc(rows[dRow]+columns[col], rows[dRow]+columns[i]);
                    }
                    dRow++;
                }


                // diagonal 2 -> ^
                dRow = row;
                for (int i = col; i < columns.length; i++) {
                    if (col != i && dRow >= 0) {
                        problem.addConstraint(new QueensProblemConstraint(rows[dRow]+columns[col], rows[dRow]+columns[i]));
                        problem.addArc(rows[dRow]+columns[col], rows[dRow]+columns[i]);
                    }

                    dRow--;
                }

                // diagonal 1
                dRow = row;
                for (int i = col; i > 0; i--) {
                    if (col != i && dRow < rows.length) {
                        problem.addConstraint(new QueensProblemConstraint(rows[dRow]+columns[col], rows[dRow]+columns[i]));
                        problem.addArc(rows[dRow]+columns[col], rows[dRow]+columns[i]);
                    }
                    dRow++;
                }


                // diagonal 2
                dRow = row;
                for (int i = col; i > 0; i--) {
                    if (col != i && dRow >= 0) {
                        problem.addConstraint(new QueensProblemConstraint(rows[dRow]+columns[col], rows[dRow]+columns[i]));
                        problem.addArc(rows[dRow]+columns[col], rows[dRow]+columns[i]);
                    }
                    dRow--;
                }
            }
        }

        // Constraints
        /*
        // WA != NT
        problem.addConstraint(new AustraliaColoringConstraint("Western Australia", "Northern Territory"));
        // WA != SA
        problem.addConstraint(new AustraliaColoringConstraint("Western Australia", "South Australia"));
        // NT != SA
        problem.addConstraint(new AustraliaColoringConstraint("Northern Territory", "South Australia"));
        // NT != Q
        problem.addConstraint(new AustraliaColoringConstraint("Northern Territory", "Queensland"));
        // SA != Q
        problem.addConstraint(new AustraliaColoringConstraint("South Australia", "Queensland"));
        // NSW != Q
        problem.addConstraint(new AustraliaColoringConstraint("New South Wales", "Queensland"));
        // NSW != SA
        problem.addConstraint(new AustraliaColoringConstraint("New South Wales", "South Australia"));
        // V != SA
        problem.addConstraint(new AustraliaColoringConstraint("Victoria", "South Australia"));
        // NSW != V
        problem.addConstraint(new AustraliaColoringConstraint("New South Wales", "Victoria"));
        // T != V
        problem.addConstraint(new AustraliaColoringConstraint("Tasmania", "Victoria"));


        problem.addArc("Western Australia", "Northern Territory");
        problem.addArc("Western Australia", "South Australia");
        problem.addArc("Northern Territory", "South Australia");
        problem.addArc("Northern Territory", "Queensland");
        problem.addArc("South Australia", "Queensland");
        problem.addArc("New South Wales", "Queensland");
        problem.addArc("New South Wales", "South Australia");
        problem.addArc("Victoria", "South Australia");
        problem.addArc("New South Wales", "Victoria");
        problem.addArc("Tasmania", "Victoria");
        */
        // Result
        var solution = problem.backtrack();
        System.out.println(solution);
    }
}
