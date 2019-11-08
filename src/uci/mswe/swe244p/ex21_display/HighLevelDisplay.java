package uci.mswe.swe244p.ex21_display;

/**
 * public class JDisplay2 implements HighLevelDisplay
 *
 * In fact, the class JDisplay2 is not thread safe; it does not guarantee correct behaviour when its
 * methods are accessed by concurrent threads.
 *
 * TODO: make JDisplay2 thread safe, so that those glitches are eliminated.
 */
public class HighLevelDisplay implements HighLevelInterface {

  private HardWareDisplay hardware;
  private String[] rows;
  private int usedRowCounts;

  public HighLevelDisplay() {
    hardware = new HardWareDisplay();
    rows = new String[100];
    clear();
  }

  private void updateRow(int rowId, String newRow) {
    rows[rowId] = newRow;
    if (rowId < hardware.getRowCounts()) {
      // copy contents to the row
      for (int colId = 0; colId < newRow.length(); colId++) {
        hardware.write(rowId, colId, newRow.charAt(colId));
      }
      // clear the following chars
      for (int i = newRow.length(); i < hardware.getColCounts(); i++) {
        hardware.write(rowId, i, ' ');
      }
    }
  }

  private void flashRow(int rowId, int millisecs) {
    String row = rows[rowId];
    try {
      for (int i = 0; i * 200 < millisecs; i++) {
        updateRow(rowId, "");
        Thread.sleep(50);
        updateRow(rowId, row);
        Thread.sleep(100); // make it faster
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void clear() {
    for (int i = 0; i < hardware.getRowCounts(); i++) {
      updateRow(i, "");
    }
    usedRowCounts = 0;
  }

  // TODO: make it thread-safe
  public void addRow(String newRow) {
    updateRow(usedRowCounts, newRow);
    flashRow(usedRowCounts, 100); // make it faster

    // ! usedRowCounts is accessed by addRow and deleteRow
    usedRowCounts++;
  }

  // TODO: make it thread-safe
  public void deleteRow(int rowId) {
    // ! usedRowCounts is accessed by addRow and deleteRow
    if (rowId < usedRowCounts) {
      // move rows backwards
      for (int i = rowId + 1; i < usedRowCounts; i++) {
        updateRow(i - 1, rows[i]);
      }
      usedRowCounts--;
      // clear the last row
      updateRow(usedRowCounts, "");
      // if there are too many rows, then flash the last row
      if (usedRowCounts >= hardware.getRowCounts()) {
        flashRow(hardware.getRowCounts() - 1, 100); // make it faster
      }
    }
  }

}
