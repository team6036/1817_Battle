package frc.robot;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.DigitalSource;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Motor {
    private CANSparkMax motor;
    private CANEncoder builtInEncoder;
    private DutyCycleEncoder externalEncoder;

    public Motor(int deviceID) {
            /**
         * deviceID is the CAN ID of the SPARK MAX you are using.
         * Change to match your setup
         */
        motor = new CANSparkMax(deviceID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();

        builtInEncoder = motor.getEncoder();
        externalEncoder = new DutyCycleEncoder(deviceID);

        double distancePerRotation = 2;
        externalEncoder.setDistancePerRotation(distancePerRotation);
    }

    public void setSpeed(int speed) {
        // maybe use sigmoid function to convert values to in between -1 and 1
        // instead of asserting value lies within domain
        motor.set(speed);
    }

    public void stop() {
        motor.set(0);
    }

    public CANEncoder getBuiltInEncoder() {
        return builtInEncoder;
    }

    public double getBIDistance() {
        return builtInEncoder.getPosition();
    }

    public DutyCycleEncoder getExternalEncoder() {
        return externalEncoder;
    }

    public double getExtDistance() {
        return externalEncoder.getDistance();
    }
}