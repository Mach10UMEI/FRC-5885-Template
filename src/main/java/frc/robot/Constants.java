package frc.robot;

import edu.wpi.first.math.util.Units;

public final class Constants {

  public static final Mode currentMode = Mode.REAL;

  public static enum Mode {
    REAL,
    SIM,
    REPLAY
  }

  // Tank drive constants
  public static final class Tank {

    // Motors
    public static final int kLeftFrontMotorID = 3;
    public static final int kLeftRearMotorID = 4;
    public static final int kRightFrontMotorID = 1;
    public static final int kRightRearMotorID = 2;

    public static final Boolean kLeftMotorsInverted = false;
    public static final Boolean kRightMotorsInverted = false;

    // Wheel & frame
    public static final double kWheelDiameterMeters = Units.inchesToMeters(6.0);
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    public static final double kTrackWidthMeters = Units.inchesToMeters(31.0);

    // Encoders
    public static final int kLeftEncoderAPort = 8;
    public static final int kLeftEncoderBPort = 9;
    public static final int kRightEncoderAPort = 6;
    public static final int kRightEncoderBPort = 7;

    public static final Boolean kLeftEncoderInverted = true;
    public static final Boolean kRightEncoderInverted = false;

    public static final int kEncoderCPM = 400;
    public static final double kEncoderGearRatio = 1.0;
    public static final double kEncoderMetersPerPulse = kWheelCircumferenceMeters / kEncoderCPM;

    public static final class Auto {
      public static final double ksVolts = 0.89792;
      public static final double kvVoltSecondsPerMeter = 2.2403;
      public static final double kaVoltSecondsSquaredPerMeter = 0.60985;

      public static final double kPDriveVel = 3.2178;

      public static final double kMaxSpeedMetersPerSecond = 1.5;
      public static final double kMaxAccelerationMetersPerSecondSquared = 0.5;

      public static final double kRamseteB = 2;
      public static final double kRamseteZeta = 0.7;
    }
  }
}
