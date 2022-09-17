package com.challenge.vendingmachine.utils;

import java.util.Arrays;
import java.util.Collections;

public class Utils {

	public static final int[] allowedCoins = {5,10,20,50,100};
	
	public static int[] addX(int n, int arr[], int x) {
		int i;
		int newarr[] = new int[n + 1];
	   
		for (i = 0; i < n; i++)
			newarr[i] = arr[i];
	   
	    newarr[n] = x;
	    return newarr;
	}
	
	public static int[] sortDesc(int arr[]) {
		return Arrays.stream(arr)            
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
	}
	
	public static int[] coinConvertor(int value) {
		int change[] = new int[0];
		
		while(value > 0) {
			if(value > 100) {
				change = addX(change.length, change, 100);
				value -= 100;
			}
			if(value > 50) {
				change = addX(change.length, change, 50);
				value -= 50;
			}
			if(value > 20) {
				change = addX(change.length, change, 20);
				value -= 20;
			}
			if(value > 10) {
				change = addX(change.length, change, 10);
				value -= 10;
			}
			if(value >= 5) {
				change = addX(change.length, change, 5);
				value -= 5;
			}
		}
		return change;
	}
}
