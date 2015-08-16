package com.bosch.robot.instruction;

import com.bosch.robot.Position;

public interface Instruction {    
    
    void exec(Position position);
    
    static final Instruction EMPTY = new EmptyInstruction();
    
    static final class EmptyInstruction implements Instruction {
        @Override
        public void exec(Position position) {}
    }
}
