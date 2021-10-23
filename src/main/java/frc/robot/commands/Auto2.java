package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.HolonomicDriveController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.trajectory.*;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.math.Pose2D;
import frc.robot.subsystems.OTBSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.Constants.Debug;

public class Auto2 extends CommandBase {
    private final Timer timer = new Timer();
    private SwerveDriveSubsystem swerve;
    private HolonomicDriveController m_controller;
    private OTBSubsystem otb;
    private Trajectory trajectory;
    private Pose2d initialPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
    private BooleanSupplier odoRecalibrate;
    private TrajectoryConfig trajectoryConfig;

    /**
     *
     * @param odoRecalibrate Supplier to recalibrate odometry
     * @param swerve         Swerve Subsystem
     */

    public Auto2(BooleanSupplier odoRecalibrate, SwerveDriveSubsystem swerve, OTBSubsystem otb) {
        this.odoRecalibrate = odoRecalibrate;

        // ! For anyone else who reads this: I know these are concerningly high. It
        // ! works, so do not remove without testing. Signed, Ben K.
        m_controller = new HolonomicDriveController(new PIDController(.8, 0.16, 0.08),
                new PIDController(.8, 0.16, 0.08),
                new ProfiledPIDController(.2, 0, 0, new TrapezoidProfile.Constraints(3 * Math.PI, Math.PI)));

        this.swerve = swerve;
        this.otb = otb;

        trajectoryConfig = new TrajectoryConfig(1, 1);

        trajectory = TrajectoryGenerator.generateTrajectory(
                Arrays.asList(
                        new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
                        new Pose2d(1.2, 0, Rotation2d.fromDegrees(0)),
                        new Pose2d(-0.3, -2, Rotation2d.fromDegrees(0))
                        ),
                trajectoryConfig
        );

    }

    @Override
    public void initialize() {
        swerve.recalibrateOdometry();
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        otb.startIn();
        if (odoRecalibrate.getAsBoolean()) {
            swerve.recalibrateOdometry();
        }
        double curTime = timer.get();
        Trajectory.State desiredState = trajectory.sample(curTime);

        ChassisSpeeds targetChassisSpeeds = m_controller.calculate(
                initialPose.plus(new Transform2d(new Pose2d(), swerve.getController().odo.getCurrentPose().toPose2d())),
                desiredState, new Rotation2d());
        if (Debug.odometryDebug) {
            System.out.println("desiredState: " + new Pose2D(desiredState.poseMeters.getX() - initialPose.getX(),
                    desiredState.poseMeters.getY() - initialPose.getY(), 0));
            System.out.println("currentState: " + swerve.getController().odo.getCurrentPose());
        }
        swerve.drive(new Pose2D(targetChassisSpeeds.vxMetersPerSecond, targetChassisSpeeds.vyMetersPerSecond,
                targetChassisSpeeds.omegaRadiansPerSecond), false);

    }

    @Override
    public void end(boolean interrupted) {
        swerve.drive(new Pose2D(0, 0, 0), true);
        otb.stopIn();
    }

    public void reset() {
        initialize();
    }


}