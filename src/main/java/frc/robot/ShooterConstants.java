package frc.robot;

import frc.robot.math.InterpolatedTreeMap;

import java.util.HashMap;
import java.util.Map;

public class ShooterConstants {
    public static Map<Integer, InterpolatedTreeMap> hoodToShooterSpeed = new HashMap<>();
    public static double idleSpeed = 0.1;

    static {

        //low and high hood state
        InterpolatedTreeMap low = new InterpolatedTreeMap();
        InterpolatedTreeMap high = new InterpolatedTreeMap();

        low.put(0.0, 10.0); //distance to speed 0-1
        low.put(24.0, 25.0);
        low.put(48.0, 45.0);

        high.put(0.0, 10.0);
        high.put(12.0, 20.0);

        hoodToShooterSpeed.put(0, low);
        hoodToShooterSpeed.put(1, high);

    }
}
