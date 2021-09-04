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