// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Tank extends SubsystemBase {

  private final TankIO m_io;
  private final TankIOInputsAutoLogged m_inputs = new TankIOInputsAutoLogged();
  private final DifferentialDriveOdometry m_odometry =
      new DifferentialDriveOdometry(new Rotation2d(), 0, 0);

  /** Creates a new Tank. */
  public Tank(TankIO io) {
    m_io = io;
  }

  @Override
  public void periodic() {
    m_io.updateInputs(m_inputs);
    Logger.getInstance().processInputs("Drive", m_inputs);

    // Update odometry and log the new pose
    m_odometry.update(
        new Rotation2d(-m_inputs.m_gyroYawRad),
        m_inputs.m_leftPositionMeters,
        m_inputs.m_rightPositionMeters);
    Logger.getInstance().recordOutput("Odometry", m_odometry.getPoseMeters());
  }

  public void driveTank(double leftSpeed, double rightSpeed, double voltageMax) {
    final double deadzone = 0.075;
    if (Math.abs(leftSpeed) < deadzone) leftSpeed = 0;
    if (Math.abs(rightSpeed) < deadzone) rightSpeed = 0;

    m_io.setVoltage(
        (leftSpeed * voltageMax) * (1 / (1 - deadzone)) - deadzone,
        (rightSpeed * voltageMax) * (1 / (1 - deadzone)) - deadzone);
  }

  public double getMeters() {
    return (m_inputs.m_leftPositionMeters + m_inputs.m_rightPositionMeters) / 2;
  }
}
