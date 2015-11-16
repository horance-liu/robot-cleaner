package com.bosch.robot;

import org.junit.Test;

import com.bosch.robot.RobotCleaner;
import com.bosch.robot.instruction.Instruction;

import static com.bosch.robot.Orientation.*;
import static com.bosch.robot.instruction.Instructions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RobotCleanerTest {
  @Test
  public void left_instruction() {
    exec_instruction_at_initial(left(), position(0, 0, WEST));
  }

  @Test
  public void right_instruction() {
    exec_instruction_at_initial(right(), position(0, 0, EAST));
  }

  @Test
  public void left_right_instruction() {
    exec_instruction_at_initial(sequential(left(), right()), position(0, 0, NORTH));
  }

  @Test
  public void right_left_instruction() {
    exec_instruction_at_initial(sequential(right(), left()), position(0, 0, NORTH));
  }

  @Test
  public void forward_instruction() {
    exec_instruction_at_initial(forward(), position(0, 1, NORTH));
  }

  @Test
  public void double_forward_instruction() {
    exec_instruction_at_initial(repeat(forward(), 2), position(0, 2, NORTH));
  }

  @Test
  public void backward_instruction() {
    exec_instruction_at_initial(backward(), position(0, -1, NORTH));
  }

  @Test
  public void double_backward_instruction() {
    exec_instruction_at_initial(repeat(backward(), 2), position(0, -2, NORTH));
  }

  @Test
  public void forward_backward_instruction() {
    exec_instruction_at_initial(sequential(forward(), backward()), position(0, 0, NORTH));
  }

  @Test
  public void forward_n_instruction() {
    exec_instruction_at_initial(forward_n(2), position(0, 2, NORTH));
  }

  @Test
  public void backward_n_instruction() {
    exec_instruction_at_initial(backward_n(2), position(0, -2, NORTH));
  }

  @Test
  public void forward_n_then_backward_n_instruction() {
    exec_instruction_at_initial(sequential(forward_n(2), backward_n(2)), position(0, 0, NORTH));
  }

  @Test
  public void round_instruction() {
    exec_instruction_at_initial(round(), position(0, 0, SOUTH));
  }

  @Test
  public void double_round_instruction() {
    exec_instruction_at_initial(sequential(round(), round()), position(0, 0, NORTH));
  }

  @Test
  public void repeat_instruction() {
    exec_instruction_at_initial(repeat(forward(), 2), position(0, 2, NORTH));
  }
  
  private void exec_instruction_at_initial(Instruction instruction, Position newPosition) {
    assertThat(robot.exec(position(0, 0, NORTH), instruction), equalTo(newPosition));
  }

  private static Position position(int x, int y, Orientation orientation) {
    return new Position(x, y, orientation);
  }

  private RobotCleaner robot = new RobotCleaner();
}
