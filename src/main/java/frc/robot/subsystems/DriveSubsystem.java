// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.awt.Color;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.Motor;
import frc.robot.Util;
import frc.robot.wrappers.AHRS;
import frc.robot.wrappers.CANCoder;
import frc.robot.wrappers.ControlMode;
import frc.robot.wrappers.Joystick;
import frc.robot.wrappers.SPI;
import frc.robot.wrappers.SensorInitializationStrategy;
import frc.robot.wrappers.TalonFXFeedbackDevice;
import frc.robot.wrappers.WPI_TalonFX;;


public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public DriveSubsystem() {
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    gyro = new AHRS(SPI.Port.kMXP);

        //FRONT LEFT MODULE
        fl_turn = new WPI_TalonFX(1);
        fl_drive = new WPI_TalonFX(2);
        fl_encoder = new CANCoder(9);

        //BACK LEFT MODULE
        bl_turn = new WPI_TalonFX(3);
        bl_drive = new WPI_TalonFX(4);
        bl_encoder = new CANCoder(10);

        //BACK RIGHT MODULE
        br_turn = new WPI_TalonFX(5);
        br_drive = new WPI_TalonFX(6);
        br_encoder = new CANCoder(11);

        //FRONT RIGHT MODULE
        fr_turn = new WPI_TalonFX(7);
        fr_drive = new WPI_TalonFX(8);
        fr_encoder = new CANCoder(12);

        //CONFIGURING ENCODERS FOR EACH MODULE
        fl_encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        fl_turn.configRemoteFeedbackFilter(fl_encoder, 0);
        fl_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
        fl_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        bl_encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        bl_turn.configRemoteFeedbackFilter(bl_encoder, 0);
        bl_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
        bl_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        br_encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        br_turn.configRemoteFeedbackFilter(br_encoder, 0);
        br_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
        br_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        fr_encoder.configSensorInitializationStrategy(SensorInitializationStrategy.BootToAbsolutePosition);
        fr_turn.configRemoteFeedbackFilter(fr_encoder, 0);
        fr_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
        fr_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);

        //EXAMPLE PID FOR FRONT LEFT MODULE ANGLE
        fl_turn.config_kP(0, 0.1);
        fl_turn.config_kI(0, 0.01);
        fl_turn.config_kD(0, 0.001);

        fr_turn.config_kP(0, 0.1);
        fr_turn.config_kI(0, 0.01);
        fr_turn.config_kD(0, 0.001);

        bl_turn.config_kP(0, 0.1);
        bl_turn.config_kI(0, 0.01);
        bl_turn.config_kD(0, 0.001);

        br_turn.config_kP(0, 0.1);
        br_turn.config_kI(0, 0.01);
        br_turn.config_kD(0, 0.001);

        //EXAMPLE PID FOR BACK RIGHT WHEEL VELOCITY
        fl_drive.config_kP(0, 0.00002);
        fl_drive.config_kI(0, 0.00005);
        fl_drive.config_kD(0, 0.0);
        fl_drive.config_kF(0, 0.00005);

        fr_drive.config_kP(0, 0.00002);
        fr_drive.config_kI(0, 0.00005);
        fr_drive.config_kD(0, 0.0);
        fr_drive.config_kF(0, 0.00005);

        bl_drive.config_kP(0, 0.00002);
        bl_drive.config_kI(0, 0.00005);
        bl_drive.config_kD(0, 0.0);
        bl_drive.config_kF(0, 0.00005);

        br_drive.config_kP(0, 0.00002);
        br_drive.config_kI(0, 0.00005);
        br_drive.config_kD(0, 0.0);
        br_drive.config_kF(0, 0.00005);
    }


    public static void teleopPeriodic(){

        double x = joystick.getX();
        double y = joystick.getY();
        double z = -joystick.getZ();

        // lock rotation
        if (Controls.buttons.get(1))
            z = 0;

        // double angle = Math.toDegrees(Math.atan2(-y, x));
        double fdir = Math.toDegrees(-gyro.getYaw());
        double angle = Math.toDegrees(Math.atan2(-y*Math.sqrt(1-0.5*x*x), x*Math.sqrt(1-0.5*y*y))) + fdir;


        double power = Math.sqrt(x*x + y*y);
        // precision mode - decrease power by 0.5x
        if (Controls.buttons.get(0)) {
            power /= 2.0;
            z /= 2.0;
        }

        
        Vector flt = Vector.angleMagTranslation(angle, power);
        Vector frt = Vector.angleMagTranslation(angle, power);
        Vector blt = Vector.angleMagTranslation(angle, power);
        Vector brt = Vector.angleMagTranslation(angle, power);
        
        Vector flr = Vector.angleMagTranslation(132.510447078, z*distBetweenWheelsDiag);
        Vector frr = Vector.angleMagTranslation(407.489552922, z*distBetweenWheelsDiag);
        Vector blr = Vector.angleMagTranslation(227.489552922, z*distBetweenWheelsDiag);
        Vector brr = Vector.angleMagTranslation(312.510447078, z*distBetweenWheelsDiag);

        Vector fl = flt.add(flr);
        Vector fr = frt.add(frr);
        Vector bl = blt.add(blr);
        Vector br = brt.add(brr);

        fl_turn.set(ControlMode.Position, cc(fl.getAngleDeg(), fl_turn.getSelectedSensorPosition()));
        fr_turn.set(ControlMode.Position, cc(fr.getAngleDeg(), fr_turn.getSelectedSensorPosition()));
        bl_turn.set(ControlMode.Position, cc(bl.getAngleDeg(), bl_turn.getSelectedSensorPosition()));
        br_turn.set(ControlMode.Position, cc(br.getAngleDeg(), br_turn.getSelectedSensorPosition()));

        fl_drive.set(ControlMode.PercentOutput, fl.magnitude());
        fr_drive.set(ControlMode.PercentOutput, fr.magnitude());
        bl_drive.set(ControlMode.PercentOutput, bl.magnitude());
        br_drive.set(ControlMode.PercentOutput, br.magnitude());
  }

  static double cc(double cangle, double pangle) {
    return Util.normalizeAngle(cangle - pangle) + pangle;
  } 



/**
 * @param encoder number of encoder ticks
 * @return distance a wheel has covered, in meters
 */
public static double driveEncoderToDist(double encoder){
    double driveGearRatio = 6.86;
    return encoder / Motor.MotorType.FALCON.TICKS_PER_REV / driveGearRatio * 2 * Math.PI * Constants.WHEEL_RADIUS.getDouble();
}
}