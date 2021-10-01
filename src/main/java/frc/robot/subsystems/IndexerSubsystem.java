// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  static CANSparkMax revolver;
  static CANSparkMax revolver2;
  static TalonFX balltube;

  static final int motorPort = 3;

  static Joystick joystick = new Joystick(0);


  public IndexerSubsystem() {
    revolver = new CANSparkMax(18, MotorType.kBrushless);
    revolver2 = new CANSparkMax(21, MotorType.kBrushless);
    balltube = new TalonFX(12);
  }

  public void setPower(double power){
    revolver.set(power);
  }

  public void start(){
    revolver.set(1);
    revolver2.set(-0.05);
    balltube.set(ControlMode.Velocity, 0.5);
  }

  public void stop(){
    revolver.set(0);
    revolver2.set(0);
    balltube.set(ControlMode.Velocity, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(joystick.getRawButton(6)){
      start();
    }else{
      stop();
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
