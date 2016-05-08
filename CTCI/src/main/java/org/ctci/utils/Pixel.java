package org.ctci.utils;

public class Pixel extends Point {
	public static enum ColorEnum { 
		Red, Green, Blue;
	
		@Override
		public String toString() {
			switch(this) {
		      case Red: return "Red";
		      case Green: return "Green";
		      case Blue: return "Blue";
		      default: throw new IllegalArgumentException();
			}
		}
	};
	ColorEnum colorEnum = ColorEnum.Red;
	
	public Pixel(int x, int y,ColorEnum c) {
		super(x, y);
		colorEnum = c;
	}
	
	public boolean equals(Pixel other) {
		return this.x.equals(other.x) && this.y.equals(other.y) && this.colorEnum.equals(other.colorEnum);
	}

	@Override
	public boolean equals(Object other) {
		return (other instanceof Pixel) && equals((Pixel) other);
	}

	@Override
	public Pixel add(int x, int y) {
		x += this.x;
		y += this.y;
		return new Pixel(x, y, this.colorEnum);
	}

	@Override
	public String toString() {
		return new String("X = " + x + ", Y = " + y + ", Color = " + colorEnum);
	}

}
