package com.bosch.robot;

import com.bosch.robot.instruction.Instruction;

import org.junit.Test;

import static com.bosch.robot.Orientation.*;
import static com.bosch.robot.instruction.Instructions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RobotCleanerTest {
    @Test
    public void left_instruction() {
        WHEN_I_send_instruction(left());
        THEN_the_robot_should_be_in(position(0, 0, WEST));
    }
    
    @Test
    public void right_instruction() {
        WHEN_I_send_instruction(right());
        THEN_the_robot_should_be_in(position(0, 0, EAST));
    }
    
    @Test
    public void left_right_instruction() {
        WHEN_I_send_instruction(left());
        AND_I_send_instruction(right());
        THEN_the_robot_should_be_in(position(0, 0, NORTH));
    }
    
    @Test
    public void right_left_instruction() {
        WHEN_I_send_instruction(right());
        AND_I_send_instruction(left());
        THEN_the_robot_should_be_in(position(0, 0, NORTH));
    }
    
    @Test
    public void forward_instruction() {
        WHEN_I_send_instruction(forward());
        THEN_the_robot_should_be_in(position(0, 1, NORTH));
    }
    
    @Test
    public void double_forward_instruction() {
        WHEN_I_send_instruction(forward());
        AND_I_send_instruction(forward());
        THEN_the_robot_should_be_in(position(0, 2, NORTH));
    }
    
    @Test
    public void backward_instruction() {
        WHEN_I_send_instruction(backward());
        THEN_the_robot_should_be_in(position(0, -1, NORTH));
    }
    
    @Test
    public void double_backward_instruction() {
        WHEN_I_send_instruction(backward());
        AND_I_send_instruction(backward());
        THEN_the_robot_should_be_in(position(0, -2, NORTH));
    }
    
    @Test
    public void forward_backward_instruction() {
        WHEN_I_send_instruction(forward());
        AND_I_send_instruction(backward());
        THEN_the_robot_should_be_in(position(0, 0, NORTH));
    }
    
    @Test
    public void forward_n_instruction() {
        WHEN_I_send_instruction(forward_n(2));
        THEN_the_robot_should_be_in(position(0, 2, NORTH));
    }
    
    @Test
    public void backward_n_instruction() {
        WHEN_I_send_instruction(backward_n(2));
        THEN_the_robot_should_be_in(position(0, -2, NORTH));
    }
    
    @Test
    public void forward_n_then_backward_n_instruction() {
        WHEN_I_send_instruction(forward_n(2));
        AND_I_send_instruction(backward_n(2));
        THEN_the_robot_should_be_in(position(0, 0, NORTH));
    }
    
    @Test
    public void round_instruction() {
        WHEN_I_send_instruction(round());
        THEN_the_robot_should_be_in(position(0, 0, SOUTH));
    }
    
    @Test
    public void double_round_instruction() {
        WHEN_I_send_instruction(round());
        AND_I_send_instruction(round());
        THEN_the_robot_should_be_in(position(0, 0, NORTH));
    }
    
    @Test
    public void sequential_instruction() {
        WHEN_I_send_instruction(sequential(left(), right(), 
                forward(), backward(), forward_n(10), backward_n(10), round()));
        THEN_the_robot_should_be_in(position(0, 0, SOUTH));
    }
    
    @Test
    public void repeat_instruction() {
        WHEN_I_send_instruction(repeat(forward(), 2));
        THEN_the_robot_should_be_in(position(0, 2, NORTH));
    }
        
    private static Position position(int x, int y, Orientation orientation) {
        return new Position(x, y, orientation);
    }
    
    private void WHEN_I_send_instruction(Instruction instruction) {
        robot.exec(instruction);
    }
    
    private void AND_I_send_instruction(Instruction instruction) {
        robot.exec(instruction);
    }
    
    private void THEN_the_robot_should_be_in(Position position) {
        assertThat(robot.getPosition(), is(position));        
    }
    
    private RobotCleaner robot = new RobotCleaner();
}
