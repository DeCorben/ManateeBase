package com.blackmanatee.lagoon;

/**
 * Created by DeCorben on 7/31/2017.
 */

public final class Lagoon {
    public static boolean compString(String[] a,String[] b){
        if(!(a.length == b.length))
            return false;
        for(int z=0;z<a.length;z++){
            if(!a[z].equals(b[z]))
                return false;
        }
        return true;
    }

    public static boolean compInt(int[] a,int[] b){
        if(!(a.length == b.length))
            return false;
        for(int z=0;z<a.length;z++){
            if(!(a[z] == b[z]))
                return false;
        }
        return true;
    }
	
	public static int largest(int[] l){
		int out = l[0];
		for(int i:l){
			if(out < i)
				out = i;
		}
		return out;
	}

	public static int smallestPositive(int[] s){
        int out = s[0];
        for(int i:s){
            if(out > i && i >= 0)
                out = i;
        }
        return out;
    }
}
