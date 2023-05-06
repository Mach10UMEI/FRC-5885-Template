package frc.robot.subsystems.tank;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants;

public class TankTalonSRX implements TankIO {

  private final WPI_TalonSRX m_leftFrontMotor;
  private final WPI_TalonSRX m_leftRearMotor;
  private final WPI_TalonSRX m_rightFrontMotor;
  private final WPI_TalonSRX m_rightRearMotor;

  private final MotorControllerGroup m_leftMotors;
  private final MotorControllerGroup m_rightMotors;

  private final Encoder m_leftEncoder;
  private final Encoder m_rightEncoder;

  private final AHRS m_gyro;

  public TankTalonSRX() {
    m_leftFrontMotor = new WPI_TalonSRX(Constants.Tank.kLeftFrontMotorID);
    m_leftRearMotor = new WPI_TalonSRX(Constants.Tank.kLeftRearMotorID);
    m_rightFrontMotor = new WPI_TalonSRX(Constants.Tank.kRightFrontMotorID);
    m_rightRearMotor = new WPI_TalonSRX(Constants.Tank.kRightRearMotorID);

    m_leftMotors = new MotorControllerGroup(m_leftFrontMotor, m_leftRearMotor);
    m_rightMotors = new MotorControllerGroup(m_rightFrontMotor, m_rightRearMotor);

    m_leftMotors.setInverted(Constants.Tank.kLeftMotorsInverted);
    m_rightMotors.setInverted(Constants.Tank.kRightMotorsInverted);

    m_leftEncoder =
        new Encoder(
            Constants.Tank.kLeftEncoderAPort,
            Constants.Tank.kLeftEncoderBPort,
            Constants.Tank.kLeftEncoderInverted);
    m_rightEncoder =
        new Encoder(
            Constants.Tank.kRightEncoderAPort,
            Constants.Tank.kRightEncoderBPort,
            Constants.Tank.kRightEncoderInverted);

    m_leftEncoder.setDistancePerPulse(Constants.Tank.kEncoderMetersPerPulse);
    m_rightEncoder.setDistancePerPulse(Constants.Tank.kEncoderMetersPerPulse);

    m_gyro = new AHRS(SPI.Port.kMXP);
  }

  @Override
  public void updateInputs(TankIOInputs inputs) {
    inputs.m_leftPositionMeters = m_leftEncoder.getDistance();
    inputs.m_leftVelocityMetersPerSec = m_leftEncoder.getRate();
    inputs.m_rightPositionMeters = m_rightEncoder.getDistance();
    inputs.m_rightVelocityMetersPerSec = m_rightEncoder.getRate();
    inputs.m_gyroYawRad = m_gyro.getYaw();
  }

  @Override
  public void setVoltage(double leftVolts, double rightVolts) {
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
  }
}
