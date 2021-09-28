// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;
import frc.robot.Motor;
import frc.robot.Constants.SwerveConstants;
import frc.robot.math.Pose2D;
import frc.robot.math.Util;
import frc.robot.math.Vector;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveSubsystem extends SubsystemBase {

  //KNOWN VARIABLES
  static final double distBetweenWheelsX = 0.6096; //front to back, meters
  static final double distBetweenWheelsY = 0.5588; //left to right, meters
  static final double distBetweenWheelsDiag = 0.413482043141; // Diagonally, any corner to the center
  static final double mass = 45.0; //kilograms
  static final double wheelRadius = 0.0508; //meters

  //WPILIB DECLARATIONS
  static AHRS gyro;
  static TalonFX fl_turn, fl_drive, bl_turn, bl_drive, br_turn, br_drive, fr_turn, fr_drive;
  static CANCoder fl_encoder, bl_encoder, br_encoder, fr_encoder;
  static Pose2D fl_placement, fr_placement, bl_placement, br_placement;

  //JOYSTICK
  /**
   * Port 0 will search for USB joysticks. If none are found, it will use your mouse cursor coordinates.
   * 
   * Port 1 will listen for data sent with the Syntien smartphone app. To get the ip of your computer,
   * run Main.java and look at the title of the simulation window. Use port 6036. Create a new interface 
   * named "joystick", then insert one 2dslider and two sliders. The 2dslider simulates X and Y axes,
   * the first slider simulates joystick throttle, and the second slider simulates the Z axis.
  */
  static Joystick joystick = new Joystick(0);


  /** Creates a new ExampleSubsystem. */
  public DriveSubsystem() {
    // This method will be called once per scheduler run
    gyro = new AHRS(Port.kMXP);

    //FRONT LEFT MODULE
    fl_turn = new TalonFX(5);
    fl_drive = new TalonFX(6);
    fl_encoder = new CANCoder(11);

    //BACK LEFT MODULE
    bl_turn = new TalonFX(7);
    bl_drive = new TalonFX(8);
    bl_encoder = new CANCoder(12);

    //BACK RIGHT MODULE
    br_turn = new TalonFX(1);
    br_drive = new TalonFX(2);
    br_encoder = new CANCoder(9);

    //FRONT RIGHT MODULE
    fr_turn = new TalonFX(3);
    fr_drive = new TalonFX(4);
    fr_encoder = new CANCoder(10);

    double offsetX = SwerveConstants.offsetX;
    double offsetY = SwerveConstants.offsetY;
    br_placement = new Pose2D(+offsetX, +offsetY, Util.normalizeAngle(SwerveConstants.BR.offset, Math.PI));
    fr_placement = new Pose2D(-offsetX, +offsetY, Util.normalizeAngle(SwerveConstants.FR.offset, Math.PI));
    fl_placement = new Pose2D(-offsetX, -offsetY, Util.normalizeAngle(SwerveConstants.FL.offset, Math.PI));
    bl_placement = new Pose2D(+offsetX, -offsetY, Util.normalizeAngle(SwerveConstants.BL.offset, Math.PI));

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


    // TURN PID from manassas
    fl_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
    fl_turn.config_kF(0, 0.002, 0);
    fl_turn.config_kP(0, 0.50, 0);
    fl_turn.config_kI(0, 0.0005, 0);
    fl_turn.config_kD(0, 0, 0);

    fr_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
    fr_turn.config_kF(0, 0.002, 0);
    fr_turn.config_kP(0, 0.50, 0);
    fr_turn.config_kI(0, 0.0005, 0);
    fr_turn.config_kD(0, 0, 0);

    bl_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
    bl_turn.config_kF(0, 0.002, 0);
    bl_turn.config_kP(0, 0.50, 0);
    bl_turn.config_kI(0, 0.0005, 0);
    bl_turn.config_kD(0, 0, 0);

    br_turn.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
    br_turn.config_kF(0, 0.002, 0);
    br_turn.config_kP(0, 0.50, 0);
    br_turn.config_kI(0, 0.0005, 0);
    br_turn.config_kD(0, 0, 0);


    // DRIVE PID from manassas
    fl_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    fl_drive.config_kF(0, 1023.0 / 20660.0, 0);
    fl_drive.config_kP(0, 0.1, 0);
    fl_drive.config_kI(0, 0, 0);
    fl_drive.config_kD(0, 0, 0);

    fr_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    fr_drive.config_kF(0, 1023.0 / 20660.0, 0);
    fr_drive.config_kP(0, 0.1, 0);
    fr_drive.config_kI(0, 0, 0);
    fr_drive.config_kD(0, 0, 0);

    bl_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    bl_drive.config_kF(0, 1023.0 / 20660.0, 0);
    bl_drive.config_kP(0, 0.1, 0);
    bl_drive.config_kI(0, 0, 0);
    bl_drive.config_kD(0, 0, 0);

    br_drive.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
    br_drive.config_kF(0, 1023.0 / 20660.0, 0);
    br_drive.config_kP(0, 0.1, 0);
    br_drive.config_kI(0, 0, 0);
    br_drive.config_kD(0, 0, 0);
  }

  @Override
  public void periodic() {

    double x = joystick.getX();
    double y = joystick.getY();
    double z = -joystick.getZ();

    // lock rotation
    // if (joystick.getRawButtonPressed(1))
    //     z = 0;

    // double angle = Math.toDegrees(Math.atan2(-y, x));
    double offsetX = Constants.SwerveConstants.offsetX;
    double offsetY = Constants.SwerveConstants.offsetY;

    double fdir = Math.toDegrees(-gyro.getYaw());
    double offsetAngle = Math.toDegrees(Util.normalizeAngle(Math.atan2(offsetY, offsetX)));
    double angle = Math.toDegrees(Math.atan2(-y*Math.sqrt(1-0.5*x*x), x*Math.sqrt(1-0.5*y*y))) + fdir;


    double power = Math.sqrt(x*x + y*y);
    // // precision mode - decrease power by 0.5x
    // if (joystick.getRawButtonPressed(1)) {
    //     power /= 2.0;
    //     z /= 2.0;
    // }
    
    Vector flt = Vector.angleMagTranslation(angle, power);
    Vector frt = Vector.angleMagTranslation(angle, power);
    Vector blt = Vector.angleMagTranslation(angle, power);
    Vector brt = Vector.angleMagTranslation(angle, power);

    Vector flr = Vector.angleMagTranslation(132.510447078, z);
    Vector frr = Vector.angleMagTranslation(407.489552922, z);
    Vector blr = Vector.angleMagTranslation(227.489552922, z);
    Vector brr = Vector.angleMagTranslation(312.510447078, z);

    Vector fl = flt.add(flr);
    Vector fr = frt.add(frr);
    Vector bl = blt.add(blr);
    Vector br = brt.add(brr);

    fl_turn.set(ControlMode.Position, cc(fl.getAngleDeg() + Constants.SwerveConstants.FL.offset, getAngle(fl_turn, fl_placement)));
    fr_turn.set(ControlMode.Position, cc(fr.getAngleDeg() + Constants.SwerveConstants.FR.offset, getAngle(fr_turn, fr_placement)));
    bl_turn.set(ControlMode.Position, cc(bl.getAngleDeg() + Constants.SwerveConstants.BL.offset, getAngle(bl_turn, bl_placement)));
    br_turn.set(ControlMode.Position, cc(br.getAngleDeg() + Constants.SwerveConstants.BR.offset, getAngle(br_turn, br_placement)));

    fl_drive.set(ControlMode.PercentOutput, fl.magnitude());
    fr_drive.set(ControlMode.PercentOutput, fr.magnitude());
    bl_drive.set(ControlMode.PercentOutput, bl.magnitude());
    br_drive.set(ControlMode.PercentOutput, br.magnitude());
  }

  static double cc(double cangle, double pangle) {
    return Util.normalizeAngle(cangle - pangle) + pangle;
  }

  private static double getAngle(TalonFX motor, Pose2D placement) {
    double encoderPos = motor.getSelectedSensorPosition();
    return encoderPos / 4096 * (2 * Math.PI) + placement.ang;
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