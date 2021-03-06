// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OTBSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  static Joystick joystick = new Joystick(0);
  static XboxController xc = new XboxController(4);
  static CANSparkMax in;
  static int stuckCurrent = 1; //placeholder

  public OTBSubsystem() {
    in = new CANSparkMax(14 /*I think*/, CANSparkMaxLowLevel.MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // if(joystick.getRawButton(5)){
    //   startIn();
    // }else{
    //   stopIn();
    // }
    // if(joystick.getRawButton(6)){
    //   reverse();
    // }

    if(xc.getBumper(Hand.kRight)){
      startIn();
    }else{
      stopIn();
    }
    if(xc.getBumper(Hand.kLeft)){
      reverse();
    }

    // This method will be called once per scheduler run

  }

  public static void startIn(){
    in.set(-0.3);
  }

  public static void stopIn(){
    in.set(0);
  }

  public static void reverse(){
    in.set(0.3);
  }

  public static boolean isStuck(){
    return in.getOutputCurrent() > stuckCurrent;
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
