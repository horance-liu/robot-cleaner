package com.bosch.robot.instruction;

import com.bosch.robot.Position;

enum TurnToInstruction implements Instruction {
    LEFT(true), RIGHT(false);
        
    @Override
    public void exec(Position position) {
        position.turnTo(left);
    }
    
    private TurnToInstruction(boolean left) {
        this.left = left;
    }

    private boolean left;
}
