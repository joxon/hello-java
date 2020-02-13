package edu.uci.swe244p.ex21_display;

public interface HardWareInterface {

  public int getRowCounts();

  public int getColCounts();

  public void write(int row, int col, char c);

}
