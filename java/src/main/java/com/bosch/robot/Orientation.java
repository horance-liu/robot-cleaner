package com.bosch.robot;

public enum Orientation {
  EAST(1, 0), SOUTH(0, -1), WEST(-1, 0), NORTH(0, 1);

  private int xOffset;
  private int yOffset;

  private Orientation(int xOffset, int yOffset) {
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public Orientation turnTo(int num) {
    return values()[(ordinal() + num) % 4];
  }

  public Point moveTo(int step, Point point) {
    return point.move(step * xOffset, step * yOffset);
  }
}
