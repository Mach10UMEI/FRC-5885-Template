// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.tank.Tank;
import frc.robot.subsystems.tank.TankIO;
import frc.robot.subsystems.tank.TankTalonSRX;

public class RobotContainer {

  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final Tank m_tank;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);
  private final AddressableLEDBuffer m_ledBuffer;
  private final AddressableLED m_led;

  public RobotContainer() {

    m_chooser.setDefaultOption("Full Rainbow", "1");
    m_chooser.addOption("Off", "2");
    m_chooser.addOption("Red", "3");
    m_chooser.addOption("Blue", "4");
    m_chooser.addOption("Green", "5");
    SmartDashboard.putData("RGB Stuff", m_chooser);

    m_led = new AddressableLED(0);
    m_ledBuffer = new AddressableLEDBuffer(30);

    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);

    m_led.start();

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0, 250, 0);
    }

    m_led.setData(m_ledBuffer);

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
            () -> {
              m_tank.driveTank(-controller.getLeftY(), controller.getRightY(), 7);
              for (var i = 0; i < m_ledBuffer.getLength(); i++) {
                // Sets the specified LED to the RGB values for red
                switch (m_chooser.getSelected()) {
                  case "1":
                    m_ledBuffer.setHSV(
                        i,
                        (int) (Math.round(Math.abs(m_tank.getMeters() * 80) + 50 * i) % 180),
                        255,
                        128);
                    break;
                  case "2":
                    m_ledBuffer.setHSV(i, 0, 0, 0);
                    break;
                  case "3":
                    m_ledBuffer.setRGB(i, 255, 0, 0);
                    break;
                  case "4":
                    m_ledBuffer.setRGB(i, 0, 255, 0);
                    break;
                  case "5":
                    m_ledBuffer.setRGB(i, 0, 0, 255);
                    break;
                }
              }

              m_led.setData(m_ledBuffer);
            },
            m_tank));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
