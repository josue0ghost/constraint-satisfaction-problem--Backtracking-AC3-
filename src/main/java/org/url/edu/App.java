package org.url.edu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App 
{
    public static void main( String[] args )
    {
        // Variables
        List<String> variables = List.of("Western Australia", "Northern Territory", "Queensland", "South Australia",
                "New South Wales", "Victoria", "Tasmania");

        // Domains
        Map<String, List<String>> domains = new HashMap<>();
        for (var variable: variables) {
            domains.put(variable, List.of("R", "G", "B"));
        }

        // CSP
        CSP<String, String> problem = new CSP<>(variables, domains);

        // Constraints
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

        // Result
        var solution = problem.backtrack();
        System.out.println(solution);
    }
}
