package com.bosch.robot.instruction;

import com.bosch.robot.Point;
import com.bosch.robot.Position;
import com.bosch.robot.Orientation;

@FunctionalInterface
public interface Instruction {
  Position exec(Point point, Orientation orientation);
}
