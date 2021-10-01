package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import frc.robot.BaseEnclosure;
import frc.robot.SwerveEnclosure;

/**
 * An implementation of the SwerveEnclosure using CanTalon motors and encoders
 */
public class CanTalonSwerveEnclosure extends BaseEnclosure implements SwerveEnclosure {

	private TalonFX driveMotor;
	private TalonFX steerMotor;
	private CANCoder encoder;
	
	private boolean reverseEncoder = false;
	private boolean reverseSteer = false;

    public CanTalonSwerveEnclosure(String name, TalonFX driveMotor, TalonFX steerMotor, CANCoder encoder, double gearRatio) {

        super(name, gearRatio);

        this.driveMotor = driveMotor;
		this.steerMotor = steerMotor;
		this.encoder = encoder;

        // this.driveMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor, 0, 0);
		// this.driveMotor.config_kP(0, 0.00002);
        // this.driveMotor.config_kI(0, 0.00005);
        // this.driveMotor.config_kD(0, 0.0);
        // this.driveMotor.config_kF(0, 0.00005);
		
		// // this.steerMotor.configRemoteFeedbackFilter(encoder, 0);
		// this.steerMotor.configSelectedFeedbackSensor(TalonFXFeedbackDevice.RemoteSensor0, 0, 0);
		// this.steerMotor.config_kP(0, 0.1);
        // this.steerMotor.config_kI(0, 0.01);
        // this.steerMotor.config_kD(0, 0.001);
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
		encoder.setPosition(0);
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