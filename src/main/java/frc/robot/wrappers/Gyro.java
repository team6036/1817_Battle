package frc.robot.wrappers;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI.Port;
import frc.robot.Util;

public class Gyro {

    private double offset = 0;
    private AHRS gyro;

    public Gyro(Port port){
        gyro = new AHRS(port);
    }

    /** @return angle of gyrocope in radians */
    public double getYaw() {
        return Util.toRadians(gyro.getAngle()) - offset;
    }

    /** @return angle of gyrocope in radians per second */
    public double getRate(){
        return Util.toRadians(gyro.getRate());
    }

    /** zero the gyroscope */
    public void reset(){
        offset = Util.toRadians(gyro.getAngle());
    }
}
