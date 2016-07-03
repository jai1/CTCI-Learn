package org.ctci.utils;

//This is immutatble class
	public class Point {
		public Integer x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean equals(Point other) {
			return this.x.equals(other.x) && this.y.equals(other.y);
		}

		@Override
		public boolean equals(Object other) {
			return (other instanceof Point) && equals((Point) other);
		}

		public Point add(int x, int y) {
			x += this.x;
			y += this.y;
			return new Point(x, y);
		}

		@Override
		public String toString() {
			return new String("X = " + x + ", Y = " + y);
		}
	}
