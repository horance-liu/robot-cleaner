package com.bosch.robot.instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bosch.robot.Position;

class SequentialInstruction implements Instruction {
    SequentialInstruction(Instruction... cmds) {
        instructions.addAll(Arrays.asList(cmds));
    }
    
    @Override
    public void exec(Position position) {
        for (Instruction instruction : instructions) {
            instruction.exec(position);
        }
    }

    private List<Instruction> instructions = new ArrayList<Instruction>();
}
