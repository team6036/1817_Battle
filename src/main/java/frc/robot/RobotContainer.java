// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.*;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.OTBSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import java.util.function.BooleanSupplier;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveDriveSubsystem m_swerveDriveSubsystem = new SwerveDriveSubsystem();
  private final IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
  private final OTBSubsystem m_otbSubsystem = new OTBSubsystem();

  private final Auto2 m_swerveAutoCommand;
  private final SwerveCommand m_swerveCommand;
  private final IndexerCommand m_indexerCommand = new IndexerCommand(m_indexerSubsystem);
  private final OTBCommand m_otbCommand = new OTBCommand(m_otbSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    Joystick j = new Joystick(0);
    XboxController xc = new XboxController(4);
    // m_swerveCommand = new SwerveCommand(m_swerveDriveSubsystem, () -> j.getX(), () -> j.getY(), () -> j.getZ(),
    // () -> j.getRawButton(7));

    m_swerveCommand = new SwerveCommand(m_swerveDriveSubsystem, () -> xc.getX(Hand.kLeft), () -> xc.getY(Hand.kLeft), () -> xc.getX(Hand.kRight),
    () -> xc.getAButton());
    LimeLight lm = new LimeLight();
    lm.setPipeline(1);
    m_swerveAutoCommand = new Auto2(new BooleanSupplier() {
      @Override
      public boolean getAsBoolean() {
        return false;
      }
    }, m_swerveDriveSubsystem, m_otbSubsystem);
//    m_swerveAutoCommand = new SwerveAutoCommand(m_swerveDriveSubsystem, m_indexerSubsystem, lm);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

  }

  public Command[] getTeleopCommands() {
    return new Command[] {m_swerveCommand, m_indexerCommand, m_otbCommand};
  }

  public Command[] getAutonomousCommands() {
    return new Command[] {m_swerveAutoCommand};
  }
}
