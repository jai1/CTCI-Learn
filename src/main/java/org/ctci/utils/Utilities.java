package org.ctci.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utilities {
	public static int factorial(int n) {
	    int x = 1;
	    int y = 1;
	    for (int i = 1; i <= n; i++) {
	        y = x * i;
	        x = y;
	    }
	    return y;
	}

	public static <T>boolean containsDuplicate(List<T> list) {
		Set<T> set = new HashSet<T>(list);
		return set.size() != list.size();
	}

	public static <T>boolean isEqual(T[][] array1, T[][] array2) {
		if(array1.length != array2.length)
			return false;
		for(int i = 0; i<array2.length; i++) {
			if(array2[i].length != array1[i].length)
				return false;
			for(int j = 0; j< array2[i].length ; j++) {
				if(array2[i][j] != array1[i][j])
					return false;
			}
		}
		return true;
	}

}
