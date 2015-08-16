package com.bosch.robot.instruction;

import static com.bosch.robot.instruction.Instruction.*;
import static com.bosch.robot.instruction.TurnToInstruction.*;

public class Instructions {
    private Instructions() {
    }
    
    public static Instruction left() {
        return LEFT;
    }
    
    public static Instruction right() {
        return RIGHT;
    }

    private static boolean between(int num, int min, int max) {
        return min <= num  && num <= max;
    }
    
    private static final int MIN_MOVE_NUM = 1;
    private static final int MAX_MOVE_NUM = 10;
    
    private static Instruction move_n(boolean forward, int num) {
        return between(num, MIN_MOVE_NUM, MAX_MOVE_NUM) ? 
                new MoveToInstruction(forward, num) : EMPTY;
    }
       
    public static Instruction forward_n(int num) {
        return move_n(true, num);
    }
    
    public static Instruction backward_n(int num) {
        return move_n(false, num);
    }
    
    public static Instruction forward() {
        return forward_n(1);
    }
    
    public static Instruction backward() {
        return backward_n(1);
    }
    
    private static final int MIN_REPEAT_NUM = 1;
    private static final int MAX_REPEAT_NUM = 10;
    
    public static Instruction repeat(Instruction instruction, int num) {
        return between(num, MIN_REPEAT_NUM, MAX_REPEAT_NUM) ? 
                new RepeatedInstruction(instruction, num) : EMPTY;
    }
    
    public static Instruction round() {
        return repeat(right(), 2);
    }
    
    public static Instruction sequential(Instruction... instructions) {
        return new SequentialInstruction(instructions);
    }
}
