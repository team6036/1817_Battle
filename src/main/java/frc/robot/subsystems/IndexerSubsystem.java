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
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.InterpolatedTreeMap;
import frc.robot.LimeLight;
import frc.robot.ControlLime.LedMode;

public class IndexerSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  static CANSparkMax shooterRight;
  static CANSparkMax shooterLeft;
  static CANSparkMax indexer;
  static TalonFX balltube;
  static Servo pushUp;
  static Spark hood2;
  static Spark hood1;
  static CANSparkMax turret;
  

  static boolean up = true;

  static final int motorPort = 3;
  static final double scalef = 1;

  static Joystick joystick;
  static XboxController xb = new XboxController(4);

  static LimeLight lm;

  static InterpolatedTreeMap itm = new InterpolatedTreeMap();


  public IndexerSubsystem() {
    joystick  = new Joystick(2);
    shooterRight = new CANSparkMax(6, MotorType.kBrushless);
    shooterLeft = new CANSparkMax(18, MotorType.kBrushless);
    balltube = new TalonFX(21);
    indexer = new CANSparkMax(21, MotorType.kBrushless);
    pushUp = new Servo(2);
    hood1 = new Spark(0);
    hood2 = new Spark(1);
    turret = new CANSparkMax(22, MotorType.kBrushless);
    shooterLeft.setOpenLoopRampRate(1);
    shooterRight.setOpenLoopRampRate(1);

    lm = new LimeLight();
    lm.setPipeline(1);

    double offset = 0.00;

    itm.put(3.0, 0.625 + offset);
    itm.put(3.5, 0.656 + offset);
    itm.put(4.0, 0.695 + offset);
    itm.put(4.5, 0.77 + offset);
 
  }


  public void start(){
    // shooterRight.set(-scalef*Math.abs(joystick.getRawAxis(3)));
    // shooterLeft.set(scalef*Math.abs(joystick.getRawAxis(3)));
    shooterRight.set(-itm.getInterpolated(lm.estimateDis()));
    shooterLeft.set(itm.getInterpolated(lm.estimateDis()));
    
    SmartDashboard.putNumber("s", scalef*Math.abs(joystick.getRawAxis(3)));
    SmartDashboard.putNumber("draw", shooterLeft.getBusVoltage());
    balltube.set(ControlMode.PercentOutput, 0.95);
    System.out.println(balltube.getMotorOutputPercent());
  }

  public void start(double power){
    shooterRight.set(-scalef*power);
    shooterLeft.set(scalef*power);
    SmartDashboard.putNumber("s", scalef*power);
    SmartDashboard.putNumber("draw", shooterLeft.getAppliedOutput());
    indexer.set(-0.03);
    balltube.set(ControlMode.PercentOutput, 0.95);
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

  public boolean finding = true;

  @Override
  public void periodic() {
    // System.out.println(hood1.getPosition());

    // This method will be called once per scheduler run
    if(joystick.getTrigger() || xb.getTriggerAxis(Hand.kRight)>0.05){
      start();
    }else {
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
    if (joystick.getRawButton(2) || xb.getBButton()) {
      revole();
    }
    else if (joystick.getRawButton(3) || xb.getYButton()) {
      indexer.set(0.08);
    }
    else indexer.set(0);
      
    
    turret.set(xb.getY(Hand.kRight)/10000000);
    if(xb.getXButtonPressed()) {
      finding = !finding;
    }
    SmartDashboard.putBoolean("finding", finding);
    thing();

    // if(shooterLeft.getAppliedOutput() > 10.2){
      // xb.setRumble(RumbleType.kRightRumble, 0.6);
    // }else{
      // xb.setRumble(RumbleType.kRightRumble, 0);
    // }

    // SmartDashboard.putNumber("some dogshit", turret.get());

    
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
  public void revole(){
    indexer.set(-0.08);
  }

  public void thing(){
    if(finding){
      lm.setLEDMode(LedMode.kforceOn);
      if(lm.getIsTargetFound()){

        double val = lm.getdegRotationToTarget()/27;
        if(val < 0.02){
          val = Math.min(Math.max(val, -0.18), -0.01);
        }else if(val > 0.02){
          val = Math.max(Math.min(val, 0.18), 0.01);
        }
        turret.set(val);
      }
    }else{
      lm.setLEDMode(LedMode.kforceOff);
    }
  }
}
