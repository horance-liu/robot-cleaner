package com.bosch.robot;

import com.bosch.robot.instruction.Instruction;
import com.bosch.robot.Orientation;

public class RobotCleaner {
  public Position exec(Instruction instruction) {
    return instruction.exec(point, orientation);
  }

  private Point point = new Point(0, 0);
  private Orientation orientation = Orientation.NORTH;
}
