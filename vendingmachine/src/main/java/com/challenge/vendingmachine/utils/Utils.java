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
	
	/**
	 * Split the totalChange into available coins
	 * 
	 * @param value
	 * @return array with the possible coins change
	 */
	public static int[] coinConvertor(int value) {
		int change[] = new int[0];
		
		while(value > 0) {
			if(value > allowedCoins[4]) {
				change = addX(change.length, change, allowedCoins[4]);
				value -= allowedCoins[4];
			}
			if(value > allowedCoins[3]) {
				change = addX(change.length, change, allowedCoins[3]);
				value -= allowedCoins[3];
			}
			if(value > allowedCoins[2]) {
				change = addX(change.length, change, allowedCoins[2]);
				value -= allowedCoins[2];
			}
			if(value > allowedCoins[1]) {
				change = addX(change.length, change, allowedCoins[1]);
				value -= allowedCoins[1];
			}
			if(value >= allowedCoins[0]) {
				change = addX(change.length, change, allowedCoins[0]);
				value -= allowedCoins[0];
			}
		}
		return change;
	}
}
