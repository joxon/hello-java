package edu.uci.swe244p.ex34_display_v2;

/**
 * Let's go back to exercise 2.1, the display. Start by downloading the code again into a new
 * location, adding the parts you wrote for 2.1, or make a copy of your 2.1 solution.
 *
 * However, the class JDisplay2 should be the original one, before your changes. 
 *
 * Previously, in 2.1, you made JDisplay2 thread safe.
 *
 * This time, you should not change JDisplay2, so, leave unsafe.
 *
 * Instead, solve the synchronization problems with a semaphore outside JDisplay2.
 */
public class JDisplay2 implements HighLevelDisplay {

	private JDisplay d;
	private String[] text;
	private int usedRows;

	public JDisplay2() {
		d = new JDisplay();
		text = new String[100];
		clear();
	}

	private void updateRow(int row, String str) {
		text[row] = str;
		if (row < d.getRows()) {
			for (int i = 0; i < str.length(); i++)
				d.write(row, i, str.charAt(i));
			for (int i = str.length(); i < d.getCols(); i++)
				d.write(row, i, ' ');
		}
	}

	private void flashRow(int row, int millisecs) {
		String txt = text[row];
		try {
			for (int i = 0; i * 200 < millisecs; i++) {
				updateRow(row, "");
				Thread.sleep(70);
				updateRow(row, txt);
				Thread.sleep(130);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void clear() {
		for (int i = 0; i < d.getRows(); i++)
			updateRow(i, "");
		usedRows = 0;
	}

	public void addRow(String str) {
		updateRow(usedRows, str);
		flashRow(usedRows, 1000);
		usedRows++;
	}

	public void deleteRow(int row) {
		if (row < usedRows) {
			for (int i = row + 1; i < usedRows; i++)
				updateRow(i - 1, text[i]);
			usedRows--;
			updateRow(usedRows, "");
			if (usedRows >= d.getRows())
				flashRow(d.getRows() - 1, 1000);
		}
	}


}
