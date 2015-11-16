package com.bosch.robot;

import com.bosch.robot.instruction.Instruction;

public class RobotCleaner {
  public Position exec(Position position, Instruction instruction) {
    return instruction.exec(position.point(), position.orientation());
  }
}
