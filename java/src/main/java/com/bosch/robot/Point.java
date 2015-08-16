package com.bosch.robot;

class Point {    
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    Point move(int xOffset, int yOffset) {
        return new Point(x+xOffset, y+yOffset);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj.getClass() != Point.class)
            return false;
        
        Point other = (Point)obj;
        
        return x == other.x && y == other.y;
    }
    
    private int x;
    private int y;
}
