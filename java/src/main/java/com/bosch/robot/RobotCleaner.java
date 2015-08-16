package com.bosch.robot;

import com.bosch.robot.Orientation;
import com.bosch.robot.instruction.Instruction;

public class RobotCleaner {
    public void exec(Instruction instruction) {
        instruction.exec(position);
    }
    
    public Position getPosition() {
        return position;        
    }
    
    private Position position = new Position(0, 0, Orientation.NORTH);
}
