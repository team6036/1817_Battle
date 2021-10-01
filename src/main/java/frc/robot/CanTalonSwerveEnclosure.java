package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.BaseEnclosure;
import frc.robot.SwerveEnclosure;

/**
 * An implementation of the SwerveEnclosure using CanTalon motors and encoders
 */
public class CanTalonSwerveEnclosure extends BaseEnclosure implements SwerveEnclosure {

	private TalonFX driveMotor;
	private TalonFX steerMotor;
	
	private boolean reverseEncoder = false;
	private boolean reverseSteer = false;

    public CanTalonSwerveEnclosure(String name, TalonFX driveMotor, TalonFX steerMotor, double gearRatio) {

        super(name, gearRatio);

        this.driveMotor = driveMotor;
		this.steerMotor = steerMotor;
    }

    @Override
    public void stop() {
		driveMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setSpeed(double speed) {
    	driveMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public void setAngle(double angle) {
    	steerMotor.set(ControlMode.Position, (reverseSteer ? -1 : 1) * angle * gearRatio);
    }

    @Override
    public int getEncPosition() {
        int reverse = reverseEncoder ? -1 : 1;
        return reverse * (int)steerMotor.getSelectedSensorPosition(0);
    }	

    @Override
    public void setEncPosition(int position) {
    	steerMotor.setSelectedSensorPosition(position, 0, 10);
    }

    public TalonFX getDriveMotor()
	{
		return driveMotor;
	}
	
	public TalonFX getSteerMotor()
	{
		return steerMotor;
	}
	
	public boolean isReverseEncoder()
	{
		return reverseEncoder;
	}
	
	public void setReverseEncoder(boolean reverseEncoder)
	{
		this.reverseEncoder = reverseEncoder;
	}
	
	public void setReverseSteerMotor(boolean reverseSteer)
	{
		this.reverseSteer = reverseSteer;
	}	
}