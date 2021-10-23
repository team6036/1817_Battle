package frc.robot.commands;

import frc.robot.LimeLight;
import frc.robot.ControlLime.LedMode;
import frc.robot.math.Pose2D;
import frc.robot.math.Util;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class SwerveAutoCommand extends CommandBase {
    private final SwerveDriveSubsystem m_swerveSubsystem;
    private final IndexerSubsystem m_indexerSubsystem;
    public LimeLight lm;

    /**
     * Creates a new ExampleCommand.
     *
//     * @param subsystem The subsystem used by this command.
     */
    public SwerveAutoCommand(SwerveDriveSubsystem swerve, IndexerSubsystem idSub, LimeLight limelight) {
        m_swerveSubsystem = swerve;
        m_indexerSubsystem = idSub;
        lm = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_swerveSubsystem, m_indexerSubsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_swerveSubsystem.recalibrateOdometry();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        lm.setLEDMode(LedMode.kforceOn);
        lm.setPipeline(1);

        double driveRotation = 0;
        //double driveRotation = lm.getdegRotationToTarget()/30;
        if (Math.abs(driveRotation) < 0.05)
            driveRotation = 0;
        Pose2D driveTranslation = Util
                .squareToCircle(new Pose2D(0, 0, driveRotation))
                .scalarMult(2);
        if (driveTranslation.getMagnitude() < 0.15) {
            driveTranslation = new Pose2D(0, 0, driveRotation);
        }
        // m_swerveSubsystem.drive(driveTranslation, true);
//        m_indexerSubsystem.setHood();
        m_indexerSubsystem.start();

        // while(true){

        
        m_swerveSubsystem.drive(new Pose2D(-0.4, 0, 0), true);

        // }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_indexerSubsystem.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    public LimeLight getLimeLight() {
        return lm;
    }
}