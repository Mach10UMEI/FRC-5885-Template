// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank;

import org.littletonrobotics.junction.AutoLog;

public interface TankIO {

  @AutoLog
  public static class TankIOInputs {
    public double m_leftPositionMeters = 0.0;
    public double m_leftVelocityMetersPerSec = 0.0;
    public double m_rightPositionMeters = 0.0;
    public double m_rightVelocityMetersPerSec = 0.0;
    public double m_gyroYawRad = 0.0;
  }

  /** Updates the set of loggable inputs. */
  public default void updateInputs(TankIOInputs inputs) {}

  /** Run open loop at the specified voltage. */
  public default void setVoltage(double leftVolts, double rightVolts) {}
}
