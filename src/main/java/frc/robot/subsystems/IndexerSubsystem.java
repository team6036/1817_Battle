// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  static CANSparkMax shooterRight;
  static CANSparkMax shooterLeft;
  static CANSparkMax indexer;
  static TalonFX balltube;
  static Servo pushUp;
  static Spark hood1;

  static boolean up = false;

  static final int motorPort = 3;
  static final double scalef = 0.8;

  static Joystick joystick = new Joystick(2);


  public IndexerSubsystem() {
    shooterRight = new CANSparkMax(6, MotorType.kBrushless);
    shooterLeft = new CANSparkMax(18, MotorType.kBrushless);
    balltube = new TalonFX(21);
    indexer = new CANSparkMax(21, MotorType.kBrushless);
    pushUp = new Servo(2);
    hood1 = new Spark(0);
  }


  public void start(){
    shooterRight.set(-scalef*Math.abs(joystick.getRawAxis(3)));
    shooterLeft.set(scalef*Math.abs(joystick.getRawAxis(3)));
    indexer.set(-0.05);
    balltube.set(ControlMode.PercentOutput, 0.7);
  }

  public void stop(){
    shooterRight.set(0);
    shooterLeft.set(0);
    indexer.set(0);
    balltube.set(ControlMode.PercentOutput, 0);
    hood1.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(joystick.getRawButton(11)){
      start();
    }else{
      stop();
      shooterRight.set(-0.2);
      shooterLeft.set(0.2);
      hood1.set(joystick.getRawAxis(3));
    }

    // push up thing
    if (joystick.getRawButton(12)) {
      up = !up;
      if (up)
        pushUp.setAngle(90);
      else
        pushUp.setAngle(0);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
