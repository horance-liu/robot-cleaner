package com.bosch.robot.instruction;

import com.bosch.robot.Position;

class RepeatedInstruction implements Instruction {    
    RepeatedInstruction(Instruction instruction, int numOfRepeated) {
        this.instruction = instruction;
        this.numOfRepeated = numOfRepeated;
    }
    
    @Override
    public void exec(Position position) {
        for (int i=0; i<numOfRepeated; i++) {
            instruction.exec(position);
        }
    }

    private Instruction instruction;
    private int numOfRepeated;
}
