package com.blackmanatee.lagoon;

import java.util.ArrayDeque;

/**
 * Created by DeCorben on 12/13/2017.
 */

public class LagoonParser {
    private static String state;
    private static ArrayDeque<Tag> stack;

    public static Tag parse(String x){
        String a = x;
        state = "no_document";
        stack = new ArrayDeque<>();
        int i = 0;
        String b = "";
        while(a.length() > 0) {
            switch (state) {
                case "no_document":
                    i = a.indexOf("<");
                    b = a.substring(0, i);
                    a = a.substring(i++);
                    if(a.charAt(0) != '?')
                        state = "no_tag";
                    else {
                        stack.add(new Tag());
                        state = "new_tag";
                    }
                    break;
                case "no_tag":
                    i = a.indexOf("<");
                    b = a.substring(0, i);
                    a = a.substring(i++);
                    if (stack.peek() != null)
                        stack.peek().setContent(a);
                    stack.add(new Tag());
                    state = "new_tag";
                    break;
                case "new_tag":
                    i = Lagoon.smallest(new int[]{a.indexOf(">"), a.indexOf(" "),a.indexOf("/")});
                    b = a.substring(0, i);
                    if(a.charAt(i) == ' ')
                        i++;
                    a = a.substring(i);
                    stack
                    break;
                default:
                    System.out.println("Unhandled state:" + state);
                    break;
            }
        }
        return stack.peekLast();
    }
}
