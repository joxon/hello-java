package edu.uci.swe244p.ex34_display_v2;

public interface HWDisplay {
    public int getRows();

    public int getCols();

    public void write(int row, int col, char c);
}
