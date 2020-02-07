package be.doubbel.sudo.common;

public class RowCol {
		private Integer row;
		private Integer col;

		public RowCol(Integer row, Integer col) {
				this.row = row;
				this.col = col;
		}

		@Override public boolean equals(Object obj) {
				System.out.println("Pos 1");
				if (obj == null) return false;
				System.out.println("Pos 2");
				if (obj == this) return true;
				RowCol test = (RowCol) obj;
				System.out.println("Row " + getRow()+" Col " + getCol());
				return test.getCol().equals(getCol()) && test.getRow().equals(getRow());
		}

		public Integer getRow() {
				return row;
		}

		public Integer getCol() {
				return col;
		}
}

