package com.bosch.robot.instruction;

import com.bosch.robot.Position;

public final class Instructions {

  public static Instruction nothing() {
    return (point, orientation) -> new Position(point, orientation);
  }

  private static Instruction turnTo(int num) {
    return (point, orientation) -> {
      return new Position(point, orientation.turnTo(num));
    };
  }

  public static Instruction left() {
    return turnTo(3);
  }

  public static Instruction right() {
    return turnTo(1);
  }

  public static Instruction sequential(Instruction... instructions) {
    return (point, orientation) -> {
      Position pos = new Position(point, orientation);
      for (Instruction instruction : instructions) {
        pos = instruction.exec(pos.point(), pos.orientation());
      }
      return pos;
    };
  }

  private static boolean between(int num, int min, int max) {
    return min <= num && num <= max;
  }

  private static final int MIN_MOVE_NUM = 1;
  private static final int MAX_MOVE_NUM = 10;

  private static Instruction safe_move_n(int step) {
    return (point, orientation) -> new Position(orientation.moveTo(step, point), orientation);
  }

  private static Instruction move_n(int step) {
    return between(Math.abs(step), MIN_MOVE_NUM, MAX_MOVE_NUM) ? safe_move_n(step) : nothing();
  }

  private static int step(boolean forward, int num) {
    return (forward ? 1 : -1) * num;
  }

  public static Instruction forward_n(int num) {
    return move_n(step(true, num));
  }

  public static Instruction backward_n(int num) {
    return move_n(step(false, num));
  }

  public static Instruction forward() {
    return forward_n(1);
  }

  public static Instruction backward() {
    return backward_n(1);
  }

  private static final int MIN_REPEAT_NUM = 1;
  private static final int MAX_REPEAT_NUM = 10;

  private static Instruction doRepeat(Instruction instruction, int num) {
    return (point, orientation) -> {
      Position pos = new Position(point, orientation);
      for (int i = 0; i < num; i++) {
        pos = instruction.exec(pos.point(), pos.orientation());
      }
      return pos;
    };
  }

  public static Instruction repeat(Instruction instruction, int num) {
    return between(num, MIN_REPEAT_NUM, MAX_REPEAT_NUM) ? doRepeat(instruction, num) : nothing();
  }

  public static Instruction round() {
    return repeat(right(), 2);
  }

  private Instructions() {
  }
}
