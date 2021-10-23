package frc.robot.math;

import java.util.TreeMap;

public class InterpolatedTreeMap extends TreeMap<Double, Double /* TODO: replace with shooter state later*/> {
    public Double getInterpolated(double key) {
        Double val = get(key);
        if (val == null) {
            Double ceilingKey = ceilingKey(key);
            Double floorKey = floorKey(key);

            if (ceilingKey == null && floorKey == null) {
                return null;
            }
            if (ceilingKey == null) {
                return floorKey;
            }
            if (floorKey == null) {
                return ceilingKey;
            }
            Double ceiling = get(ceilingKey);
            Double floor = get(floorKey);

            return interpolate(ceiling, floor, inverseInterpolate(ceiling, key, floor));
        } else return val;
    }

    public Double interpolate(Double val1, Double val2, double d) {
        double dydx = val2 - val1;
        return dydx * d + val1;
    }

    public double inverseInterpolate(Double up, double q, Double down) {
        double upper_to_lower = up - down;
        if (upper_to_lower <= 0) {
            return 0;
        }
        double query_to_lower = q - down;
        if (query_to_lower <= 0) {
            return 0;
        }
        return query_to_lower / upper_to_lower;
    }
}
