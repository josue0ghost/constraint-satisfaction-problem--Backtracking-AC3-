package org.url.edu;

import java.util.*;
import java.util.stream.Collectors;

public class CSP <V, D>{

    private List<V> variables;

    // an assignation where each variable can have compatibility with distinct values for a domain
    private Map<V, List<D>> domains;

    // there can be a constraint for each variable
    private Map<V, List<Constraint<V, D>>> constraints = new HashMap<>();

    // there are arcs for each variable
    private List<Arc<V, D>> arcs = new ArrayList<>();

    public CSP (List<V> variables, Map<V, List<D>> domains) {
        this.variables = variables;
        this.domains = domains;

        for (V variable: variables) {
            // initialize variables with empty constraints
            constraints.put(variable, new ArrayList<Constraint<V, D>>());
            // initialize variables with empty arcs
            // arcs.put(variable, new ArrayList<Arc<V, A>>());

            // each variable must have a domain
            if (!domains.containsKey(variable)){
                throw  new IllegalArgumentException("La variable "+ variable + " no contiene un dominio");
            }
        }
    }

    public void addConstraint(Constraint<V, D> constraint) {
        for (V variable: constraint.variables) {
            // The constraint's variable must be part of the CSP
            if (!this.variables.contains(variable)) {
                throw new IllegalArgumentException("La variable " + variable + "no se encuentra en el CSP");
            }

            constraints.get(variable).add(constraint);
        }
    }

    public void addArc(V place1, V place2) {
        Arc<V, D> newArc = new Arc<V, D>(place1, place2) {
            @Override
            public boolean satisfied(Map<V, D> assignment) {
                return false;
            }
        };

        this.arcs.add(newArc);

        Arc<V, D> reverseArc = new Arc<V, D>(place2, place1) {
            @Override
            public boolean satisfied(Map<V, D> assignment) {
                return false;
            }
        };

        this.arcs.add(reverseArc);
    }

    public boolean consistent(V variable, Map<V,D> assignment) {
        for (Constraint<V, D> constraint :this.constraints.get(variable)) {
            if (!constraint.satisfied(assignment)) {
                return false;
            }
        }
        return true;
    }

    public Map<V, D> backtrack() {
        return backtrack(new HashMap<>());
    }

    public Map<V, D> backtrack(Map<V, D> assignment) {
        // If each variable has a value
        // end of solution
        if (assignment.size() == variables.size()) {
            return assignment;
        }

        // Select an unassigned variable
        V unassigned = variables.stream()
            .filter(v -> !assignment.containsKey(v)) // first one that is not contained in assignment
            .findFirst().get();

        for (var value: domains.get(unassigned)) {
            // Processing this variable and this domian
            System.out.println("Variable: " + unassigned + ", dominio: " + value);

            // Test an assignation
            // 1. Create a copy of the last assignation
            Map<V,D> localAssignment = new HashMap<>(assignment);

            // 2. Test (aka assign a value)
            localAssignment.put(unassigned, value);

            domains.put(unassigned, List.of(value));
            // 3. Verify assignation consistency
            if (consistent(unassigned, localAssignment) && ac3()){

                // Unassigned node is now assigned, so its domains reduces to 1 element
                domains.put(unassigned, domains.get(unassigned).stream().filter(v -> v.equals(value)).collect(Collectors.toList()));

                Map<V, D> result = backtrack(localAssignment);

                if (result != null) {
                    return  result;
                }
            }
        }

        // no solution
        return null;
    }

    public Boolean ac3() {
        Queue<Arc<V, D>> arcs = new ArrayDeque<>(this.arcs);

        while (!arcs.isEmpty()) {
            var arc = arcs.remove();
            
            if (revise(arc)) {
                if (domains.get(arc.tail).isEmpty()) {
                    print("El dominio de la cola esta vacio");
                    return false;
                }

                print("Agregando nuevos arcos a la cola");
                // for each head neighbor - { tail } -> add arc to queue
                this.arcs.stream().filter(v -> (v.head == arc.head && v.tail != arc.tail))
                        .forEach(vdArc -> arcs.add(vdArc));
            }
        }

        return true;
    }

    public Boolean revise(Arc<V, D> arc) {
        Boolean revised = false;

        var headDomains = domains.get(arc.head);
        var tailDomains = domains.get(arc.tail);

        for (var tailVariable : tailDomains) {
            // if no value in tailDomain is satisfied by any other value from headDomain
            if (headDomains.stream().filter(v -> !v.equals(tailVariable)).count() == 0) {
                // delete the variable from tailDomain
                print("Se eliminara " + tailVariable + " de " + tailDomains.toString());

                //tailDomains.remove(tailVariable);
                domains.put(arc.tail, tailDomains.stream().filter(v -> !v.equals(tailVariable)).collect(Collectors.toList()));
                revised = true;
            }
        }

        return revised;
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
