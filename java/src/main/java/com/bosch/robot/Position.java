package com.bosch.robot;

public class Position {    
    public Position(int x, int y, Orientation orientation) {
        this.point = new Point(x, y);
        this.orientation = orientation;
    }

    public void turnTo(boolean left) {
        orientation = orientation.turnTo(left);
    }
    
    public void moveTo(int step) {
        point = orientation.moveTo(point, step);
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) 
    		return true;
    	
    	if (obj.getClass() != Position.class)
    	    return false;
    	
    	Position other = (Position)obj;
    	
    	return point.equals(other.point) &&
    		   orientation == other.orientation;
    }
    
    private Point point;
    private Orientation orientation;
}
