package org.example.java8;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Main {

	public static void main(String[] args) {

		DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		LocalDateTime dt = LocalDateTime.now();
		System.out.println(dtf.format(dt));
		
	}

}
