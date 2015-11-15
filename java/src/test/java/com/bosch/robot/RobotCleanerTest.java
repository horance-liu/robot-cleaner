package com.bosch.robot;

import org.junit.Test;

import com.bosch.robot.RobotCleaner;
import static com.bosch.robot.Orientation.*;
import static com.bosch.robot.instruction.Instructions.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class RobotCleanerTest {
  @Test
  public void left_instruction() {
    assertThat(robot.exec(left()), equalTo(position(0, 0, WEST)));
  }

  @Test
  public void right_instruction() {
    assertThat(robot.exec(right()), equalTo(position(0, 0, EAST)));
  }

  @Test
  public void left_right_instruction() {
    assertThat(robot.exec(sequential(left(), right())), equalTo(position(0, 0, NORTH)));
  }

  @Test
  public void right_left_instruction() {
    assertThat(robot.exec(sequential(right(), left())), equalTo(position(0, 0, NORTH)));
  }

  @Test
  public void forward_instruction() {
    assertThat(robot.exec(forward()), equalTo(position(0, 1, NORTH)));
  }

  @Test
  public void double_forward_instruction() {
    assertThat(robot.exec(repeat(forward(), 2)), equalTo(position(0, 2, NORTH)));
  }

  @Test
  public void backward_instruction() {
    assertThat(robot.exec(backward()), equalTo(position(0, -1, NORTH)));
  }

  @Test
  public void double_backward_instruction() {
    assertThat(robot.exec(repeat(backward(), 2)), equalTo(position(0, -2, NORTH)));
  }

  @Test
  public void forward_backward_instruction() {
    assertThat(robot.exec(sequential(forward(), backward())), equalTo(position(0, 0, NORTH)));
  }

  @Test
  public void forward_n_instruction() {
    assertThat(robot.exec(forward_n(2)), equalTo(position(0, 2, NORTH)));
  }

  @Test
  public void backward_n_instruction() {
    assertThat(robot.exec(backward_n(2)), equalTo(position(0, -2, NORTH)));
  }

  @Test
  public void forward_n_then_backward_n_instruction() {
    assertThat(robot.exec(sequential(forward_n(2), backward_n(2))), equalTo(position(0, 0, NORTH)));
  }

  @Test
  public void round_instruction() {
    assertThat(robot.exec(round()), equalTo(position(0, 0, SOUTH)));
  }

  @Test
  public void double_round_instruction() {
    assertThat(robot.exec(sequential(round(), round())), equalTo(position(0, 0, NORTH)));
  }

  @Test
  public void repeat_instruction() {
    assertThat(robot.exec(repeat(forward(), 2)), equalTo(position(0, 2, NORTH)));
  }

  private static Position position(int x, int y, Orientation orientation) {
    return new Position(x, y, orientation);
  }

  private RobotCleaner robot = new RobotCleaner();
}
