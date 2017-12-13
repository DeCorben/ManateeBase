package com.blackmanatee.lagoon;

/**
 * Created by DeCorben on 12/13/2017.
 */

public abstract interface LagoonListener {
    public void tagStart(Tag t);
    public void tagEnd(Tag t);
    public void content(String c);
}
