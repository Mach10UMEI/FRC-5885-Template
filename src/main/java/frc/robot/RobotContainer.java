// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tank.TankIO;
import frc.robot.subsystems.tank.TankTalonSRX;

public class RobotContainer {

  private final Tank m_tank;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);

  public RobotContainer() {

    switch (Constants.currentMode) {
        // Real robot, instantiate hardware IO implementations
      case REAL:
        m_tank = new Tank(new TankTalonSRX());
        break;

        // Sim robot, instantiate physics sim IO implementations
      case SIM:
        // tank = new Drive(new DriveIOSim());
        throw new NoSuchMethodError("Not Implemented");
        // break;

        // Replayed robot, disable IO implementations
      default:
        m_tank = new Tank(new TankIO() {});
        break;
    }

    configureBindings();
  }

  private void configureBindings() {
    m_tank.setDefaultCommand(
        new RunCommand(
            () -> m_tank.driveTank(-controller.getLeftY(), controller.getRightY(), 7), m_tank));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
