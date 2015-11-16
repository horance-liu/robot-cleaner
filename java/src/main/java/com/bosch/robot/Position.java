package com.bosch.robot;

public class Position {
  public Position(int x, int y, Orientation orientation) {
    this(new Point(x, y), orientation);
  }

  public Position(Point point, Orientation orientation) {
    this.point = point;
    this.orientation = orientation;
  }

  public Point point() {
    return point;
  }
  
  public Orientation orientation() {
    return orientation;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position other = (Position) obj;
      return point.equals(other.point) && orientation == other.orientation;
    }
    return false;
  }

  private Point point;
  private Orientation orientation;
}
