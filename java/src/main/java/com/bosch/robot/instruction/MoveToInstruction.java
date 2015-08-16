package com.bosch.robot.instruction;

import com.bosch.robot.Position;

class MoveToInstruction implements Instruction {  
    MoveToInstruction(boolean forward, int step) {
        this.step = prefix(forward)*step;
    }
    
    private static int prefix(boolean forward) {
        return forward ? 1 : -1;
    }
            
    @Override
    public void exec(Position position) {
        position.moveTo(step);
    }

    private int step;
}
