package com.example.promob;

public class Position {
    private int xpos;
    private int ypos;

    public Position(int xpos, int ypos){
        this.xpos=xpos;
        this.ypos=ypos;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }
}
