package com.bosch.robot;

public class Point {
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Point move(int xOffset, int yOffset) {
    return new Point(x + xOffset, y + yOffset);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point) {
      Point other = (Point) obj;
      return x == other.x && y == other.y;
    }
    return false;
  }
  
  private int x;
  private int y;
}
