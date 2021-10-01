package frc.robot;

import frc.robot.math.CentricMode;
import frc.robot.math.SwerveDirective;
import frc.robot.math.SwerveMath;

import java.util.List;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * The main class for the SwerveDrive subsystem: This class handles all aspects of controlling the swerve drive.
 * Use this class in your program if you want the easiest way to integrate swerve drive into your robot.
 */
public class SwerveDriveSubsystem extends SubsystemBase {
    private Joystick joystick = new Joystick(0);
    private static AHRS gyro;

    // Enclosures 1-4 are the drive/steer combos
    private CanTalonSwerveEnclosure swerveEnclosureFL;
    private CanTalonSwerveEnclosure swerveEnclosureFR;
    private CanTalonSwerveEnclosure swerveEnclosureBL;
    private CanTalonSwerveEnclosure swerveEnclosureBR;

    private final SwerveMath swerveMath;

    public SwerveDriveSubsystem(double width, double length) {

        int gearRatio = 2048;

        swerveEnclosureFL = new CanTalonSwerveEnclosure("FL", new TalonFX(6), new TalonFX(5), gearRatio);
        swerveEnclosureFR = new CanTalonSwerveEnclosure("FR", new TalonFX(4), new TalonFX(3), gearRatio);
        swerveEnclosureBL = new CanTalonSwerveEnclosure("BL", new TalonFX(8), new TalonFX(7), gearRatio);
        swerveEnclosureBR = new CanTalonSwerveEnclosure("BR", new TalonFX(2), new TalonFX(1), gearRatio);

        // instantiate the swerve library with a gyro provider using pigeon1
        swerveMath = new SwerveMath(width, length);
        // swerveEnclosureFL.setEncPosition(0);
        // swerveEnclosureFR.setEncPosition(0);
        // swerveEnclosureBL.setEncPosition(0);
        // swerveEnclosureBR.setEncPosition(0);

        gyro = new AHRS(Port.kMXP);

    }

    @Override
    public void periodic() {
        this.setCentricMode(CentricMode.FIELD);

        double x = joystick.getX();
        double y = joystick.getY();
        double z = joystick.getRawAxis(3);

        // double yaw = (double)(-gyro.getYaw());
        double yaw = 0;
        move(y, x, z, yaw);
    }

    /**
     * move
     * Moves the robot based on 3 inputs - fwd (forward), str(strafe), and rcw(rotation clockwise)
     * Inputs are between -1 and 1, with 1 being full power, -1 being full reverse, and 0 being neutral.
     * The method uses gyro for field centric driving, if it is enabled.
     *
     * @param fwd
     * @param str
     * @param rcw
     * @param gyroValue the value of the gyro input to be used by the calculation. Optional. Only used when the robot is in field-centric mode.
     */
    public void move(double fwd, double str, double rcw, Double gyroValue) {
        // Get the move command calculated
        List<SwerveDirective> swerveDirectives = swerveMath.move(fwd, str, rcw, gyroValue);

        swerveEnclosureFL.move(swerveDirectives.get(0).getSpeed(), swerveDirectives.get(0).getAngle());
        swerveEnclosureFR.move(swerveDirectives.get(1).getSpeed(), swerveDirectives.get(1).getAngle());
        swerveEnclosureBL.move(swerveDirectives.get(2).getSpeed(), swerveDirectives.get(2).getAngle());
        swerveEnclosureBR.move(swerveDirectives.get(3).getSpeed(), swerveDirectives.get(3).getAngle());
    }

    /**
     * Stop the robot (set speed to 0)
     * @throws Exception 
     */
    public void stop() {
        swerveEnclosureFL.stop();
        swerveEnclosureFR.stop();
        swerveEnclosureBL.stop();
        swerveEnclosureBR.stop();
    }

    /**
     * Change the centric-mode of the robot (this can be done dynamically any time and will affect
     * the robot behavior from that point on)
     */
    public void setCentricMode(CentricMode centricMode) {
        this.swerveMath.setCentricMode(centricMode);
    }
    
    public void setModeField() {
		this.swerveMath.setModeField();
	}

}