package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.Debug;
import frc.robot.Constants.SwerveConstants;
import frc.robot.Constants.SwerveConstants.FL;
import frc.robot.Constants.SwerveConstants.FR;
import frc.robot.Constants.SwerveConstants.BR;
import frc.robot.Constants.SwerveConstants.BL;
import frc.robot.math.Gyroscope;
import frc.robot.math.Pose2D;
import frc.robot.subsystems.SwerveController;
import frc.robot.math.Util;
import frc.robot.subsystems.SwerveController.Module;

public class SwerveDriveSubsystem extends SubsystemBase {
    private final SwerveController swerve;
    private Joystick joystick;
    private boolean fieldRelative;
    private Pose2D robotSpeed;
    // private XboxController xb;

    /**
     * Creates a new ExampleSubsystem.
     */
    public SwerveDriveSubsystem() {
        joystick = new Joystick(0);
        // xb = new XboxController(2);
        robotSpeed = new Pose2D();
        double offsetX = SwerveConstants.offsetX;
        double offsetY = SwerveConstants.offsetY;
        swerve = new SwerveController(SwerveConstants.DRIVE_RATIO, SwerveConstants.WHEEL_RADIUS,
                new Gyroscope(SPI.Port.kMXP),
                new Module(BR.T, BR.D, BR.E, new Pose2D(+offsetX, +offsetY, Util.normalizeAngle(BR.offset, Math.PI)),
                        "backRight"),
                new Module(FR.T, FR.D, FR.E, new Pose2D(-offsetX, +offsetY, Util.normalizeAngle(FR.offset, Math.PI)),
                        "frontRight"),
                new Module(FL.T, FL.D, FL.E, new Pose2D(-offsetX, -offsetY, Util.normalizeAngle(FL.offset, Math.PI)),
                        "frontLeft"),
                new Module(BL.T, BL.D, BL.E, new Pose2D(+offsetX, -offsetY, Util.normalizeAngle(BL.offset, Math.PI)),
                        "backLeft"));
    }

    @Override
    public void periodic() {
        double x = joystick.getX();
        // double x = xb.getX(Hand.kLeft);
        double y = joystick.getY();
        // double y = xb.getY(Hand.kLeft);
        double z = joystick.getRawAxis(3);
        // double z = xb.getX(Hand.kRight);

        double speed = Math.sqrt(x*x + y*y);
        

        swerve.nyoom(robotSpeed, fieldRelative);
        // TODO: make chassis angle PID controlled to prevent drift
        // TODO: Work with chis to add a estimated chassis angle in order to actually be
        // able to measure error of it
        if (Debug.swerveDebug) {
            for (int i = 0; i < swerve.getModules().length; i++) {
                log(swerve.getModules()[i].getName() + " target",
                        Util.normalizeAngle(swerve.getModules()[i].getTargetAngle(), Math.PI));
                log(swerve.getModules()[i].getName() + " current",
                        Util.normalizeAngle(swerve.getModules()[i].getCurrentAngle(), Math.PI));
            }
            log("Current angle", swerve.getCurrentAngle());
            log("Target angle", swerve.getTargetTheta());
        }
    }

    public SwerveController getController() {
        return swerve;
    }

    public void recalibrateOdometry() {
        swerve.recalibrateOdometry();
    }

    public void zeroWheels() {
        swerve.zeroWheels();
    }

    public void drive(Pose2D robotSpeed, boolean fieldRelative) {
        this.robotSpeed = robotSpeed;
        this.fieldRelative = fieldRelative;
    }

    public void log(String key, double val) {
        SmartDashboard.putNumber(key, val);
    }
}