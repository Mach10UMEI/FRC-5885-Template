// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.SwerveDriveSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

/** Add your docs here. */
public class SwerveModuleNEO implements SwerveModuleIO {

  private CANSparkMax m_driveMotor;
  private CANSparkMax m_turnMotor;

  private AnalogInput m_turnAbsoluteEncoder;
  private Rotation2d m_turnAbsoluteEncoderOffset;

  private final RelativeEncoder driveDefaultEncoder;
  private final RelativeEncoder turnRelativeEncoder;

  private final boolean rotateReversed;
  private final boolean driveReversed;

  public SwerveModuleNEO(
      int driveMotorId,
      int turnMotorId,
      int turnAbsoluteEncoderId,
      Rotation2d turnAbsoluteEncoderOffset,
      boolean rotateR,
      boolean driveR) {
    m_driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless);
    m_turnMotor = new CANSparkMax(turnMotorId, MotorType.kBrushless);
    m_turnAbsoluteEncoder = new AnalogInput(turnAbsoluteEncoderId);
    m_turnAbsoluteEncoderOffset = turnAbsoluteEncoderOffset;

    driveDefaultEncoder = m_driveMotor.getEncoder();
    turnRelativeEncoder = m_turnMotor.getEncoder();

    m_driveMotor.setInverted(driveR);
    m_turnMotor.setInverted(rotateR);

    rotateReversed = rotateR;
    driveReversed = driveR;
  }

  public void updateInputs(SwerveModuleIOInputs inputs) {

    inputs.drivePositionMeters = driveDefaultEncoder.getPosition();
    inputs.driveVelocityMetersPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(driveDefaultEncoder.getVelocity())
            / ((50.0 / 14.0) * (17.0 / 27.0) * (45.0 / 15.0));
    ;
    inputs.driveTemperature = m_driveMotor.getMotorTemperature();
    inputs.driveCurrent = m_driveMotor.getOutputCurrent();
    inputs.driveVoltage = m_driveMotor.getAppliedOutput() * RobotController.getBatteryVoltage();

    double absolutePositionPercent =
        (m_turnAbsoluteEncoder.getVoltage() / RobotController.getVoltage5V());
    inputs.turnPositionRad =
        new Rotation2d(absolutePositionPercent * 2.0 * Math.PI)
            .minus(m_turnAbsoluteEncoderOffset)
            .getRadians();
    inputs.turnVelocityRadPerSec =
        Units.rotationsPerMinuteToRadiansPerSecond(turnRelativeEncoder.getVelocity())
            / (150.0 / 7.0);
    ;
    inputs.turnTemperature = m_turnMotor.getMotorTemperature();
    inputs.turnCurrent = m_turnMotor.getOutputCurrent();
    inputs.turnVoltage = m_turnMotor.getAppliedOutput() * RobotController.getBatteryVoltage();
  }

  public void setDriveVoltage(double voltage) {
    m_driveMotor.setVoltage(voltage);
  }

  public void setTurnVoltage(double voltage) {
    m_turnMotor.setVoltage(voltage);
  }

  public void setDriveBrakeMode(boolean enable) {
    m_driveMotor.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }

  public void setTurnBrakeMode(boolean enable) {
    m_turnMotor.setIdleMode(enable ? IdleMode.kBrake : IdleMode.kCoast);
  }
}