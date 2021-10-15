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
  static Spark hood2;
  static Spark hood1;

  static boolean up = true;

  static final int motorPort = 3;
  static final double scalef = 1;

  static Joystick joystick;


  public IndexerSubsystem() {
    joystick  = new Joystick(2);
    shooterRight = new CANSparkMax(6, MotorType.kBrushless);
    shooterLeft = new CANSparkMax(18, MotorType.kBrushless);
    balltube = new TalonFX(21);
    indexer = new CANSparkMax(21, MotorType.kBrushless);
    pushUp = new Servo(2);
    hood1 = new Spark(0);
    hood2 = new Spark(1);
  }


  public void start(){
    shooterRight.set(-scalef*Math.abs(joystick.getRawAxis(3)));
    shooterLeft.set(scalef*Math.abs(joystick.getRawAxis(3)));
    balltube.set(ControlMode.PercentOutput, 0.7);
    System.out.println(balltube.getMotorOutputPercent());
  }

  public void start(double power){
    shooterRight.set(-scalef*power);
    shooterLeft.set(scalef*power);
    indexer.set(-0.05);
    balltube.set(ControlMode.PercentOutput, 0.7);
  }

  public void unjam(){
    indexer.set(0.05);
  }

  public void stop(){
    shooterRight.set(0);
    shooterLeft.set(0);
    balltube.set(ControlMode.PercentOutput, 0);
  }

  public void setHood() {
    hood1.setPosition(0.02);
    hood2.setPosition(0.02);
  }

  @Override
  public void periodic() {
    // System.out.println(hood1.getPosition());

    // This method will be called once per scheduler run
    if(joystick.getTrigger()){
      start();
    }
    else {
      stop();
      shooterRight.set(-0.2);
      shooterLeft.set(0.2);
      double pos1 = hood1.getPosition();
      double pos2 = hood2.getPosition();
      double z = (joystick.getRawAxis(3)+1)/2;
      if ((pos1 >= 0.02 && pos1 <= 0.5) && (pos2 >= 0.02 && pos2 <= 0.5)) {
        hood1.setPosition(z);
        hood2.setPosition(z);
      }
      else if (pos1 >= 0.5 || pos2 >= 0.5) {
        hood1.setPosition(0.49);
        hood2.setPosition(0.49);
      }
      else if (pos1 <= 0.02 || pos2 <= 0.02) {
        hood1.setPosition(0.03);
        hood2.setPosition(0.03);
      }
    }

    if (joystick.getRawButton(5)) {
      // indexer.set(0.2);
    }

    // revolver
    if (joystick.getRawButton(2)) {
      indexer.set(-0.05);
      // indexer.set(0.05);
    }
    else
      indexer.set(0);

    // push up thing
    // System.out.println(pushUp.getPosition());
    // if (joystick.getRawButton(12))
    //   up = !up;
    // if (up)
    //   pushUp.setPosition(1);
    // else
    //   pushUp.setPosition(0);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
