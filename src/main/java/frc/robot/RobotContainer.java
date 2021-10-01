// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.OTBCommand;
import frc.robot.commands.TeleCommand;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.OTBSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveDriveSubsystem m_swerveDriveSubsystem = new SwerveDriveSubsystem(0.6096, 0.5588);
  private final IndexerSubsystem m_indexerSubsystem = new IndexerSubsystem();
  private final OTBSubsystem m_otbSubsystem = new OTBSubsystem();

  private final TeleCommand m_teleCommand = new TeleCommand(m_swerveDriveSubsystem);
  private final ExampleCommand m_indexerCommand = new ExampleCommand(m_indexerSubsystem);
  private final OTBCommand m_otbCommand = new OTBCommand(m_otbSubsystem);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_teleCommand;
  }

  public Command getIndexerCommand(){
    return m_indexerCommand;
  }

  public Command getIntakeCommand() {
    return m_otbCommand;
  }
}
