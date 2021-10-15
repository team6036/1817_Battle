package frc.robot.math;

import java.util.ArrayList;

public class Polynomial {
    ArrayList<Double[]> eq;
    Double[][] eqL;

    public Polynomial(Double...coffEx) {
        assert coffEx.length % 2 == 0;

        eq = new ArrayList<Double[]>(coffEx.length/2);

        int idx = 0;
        for (int i = 0; i <= coffEx.length; i+=2) {
            double c = coffEx[i];
            double e = coffEx[i+1];
            if (c != 0 && e != 0) {
                eq.set(idx, new Double[] {c, e});
            }
            idx++;
        }
        sort();

        // supports negative exponents at the moment, 1/x^2, 3/x^4
    }

    public void sort() {

    }

    public void addTerm(Double coefficient, Double exponent) {
        for (int i = 0; i < eq.size(); i++) {
            Double[] t = eq.get(i);
            if (t[1] == exponent) {
                eq.set(i, new Double[] {coefficient+t[0], exponent});
                return;
            }
        }
        eq.add(new Double[] {coefficient, exponent});
    }

    private Double[] addTermReturned(Double[] term) {
        for (int i = 0; i < eq.size(); i++) {
            Double[] t = getTerm(i);
            if (t[1] == term[1]) {
                return new Double[] {term[0]+t[0], term[1]};
            }
        }
        return term;
    }

    private void addTermForced(Double[] term) {
        eq.add(term);
    }

    public Polynomial add(Polynomial p) {
        Polynomial n = new Polynomial();

        int mSize = Math.max(getSize(), p.getSize());
        for (int i = 0; i < mSize; i++) {
            n.addTermForced(addTermReturned(p.getTerm(i)));
        }

        return n;
    }

    public Polynomial multiply(Polynomial p) {
        Polynomial n = new Polynomial();

        for (Double[] t1 : eq) {
            for (Double[] t2 : p.get()) {
                n.addTerm(t1[0]*t2[0], t1[1]+t2[1]);
            }
        }
        n.sort();

        return n;
    }

    public Double[] getMatrix() {
        // matrix polynomial will look like 5x0 + 3x0 + 2x0
        for (Double[] t : get()) {
            if (t[1] != 0) {
                return null;
            }
        }

        Double[] matrix = new Double[getSize()];
        for (int i = 0; i < getSize(); i++) {
            matrix[i] = getTerm(i)[0];
        }
        
        return matrix;
    }

    public int getSize() {
        return eq.size();
    }

    public Double[] getTerm(int index) {
        if (index >= getSize())
            return new Double[] {0.0, 0.0};
        return eq.get(index);
    }

    public Double[][] get() {
        eqL = (Double[][])eq.toArray();
        return eqL;
    }
}
