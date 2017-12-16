package com.blackmanatee.lagoon;

import java.util.ArrayDeque;

/**
 * Created by DeCorben on 12/13/2017.
 */

public class LagoonParser {
    private static final boolean debug = false;
    private static String state;
    private static ArrayDeque<Tag> stack;

    public static Tag parse(String x){
        String a = x;
        state = "no_document";
        stack = new ArrayDeque<>();
        int i = 0;
        String b = "";
        boolean ending = false;
        while(a.length() > 0) {
            String prev_state = state;
            switch (state) {
                case "no_document":
                    i = a.indexOf("<");
                    b = a.substring(0, i);
                    i++;
                    a = a.substring(i);
                    if(a.charAt(0) != '?'){
                        stack.push(new Tag());
                        state = "new_tag";
                    }
                    break;
                case "no_tag":
                    i = a.indexOf("<");
                    b = a.substring(0, i).trim();
                    i++;
                    a = a.substring(i);
                    if (b.length() > 0) {
                        if(debug)
                            System.out.println("Content to "+stack.peek().getTag_name()+":"+b);
                        stack.peek().setContent(b);
                    }
                    if(a.charAt(0) != '/') {
                        Tag t = new Tag();
                        stack.peek().addTag(t);
                        if (debug)
                            System.out.println("child of " + stack.peek().getTag_name());
                        stack.push(t);
                    }
                    state = "new_tag";
                    break;
                case "new_tag":
                    i = Lagoon.smallestPositive(new int[]{a.indexOf(">"), a.indexOf(" "),a.indexOf("/")});
                    b = a.substring(0, i);
                    if(a.charAt(i) == ' ')
                        i++;
                    else if(a.charAt(i) == '/'){
                        state = "end_of_tag";
                        ending = true;
                    }
                    if(b.length() > 0) {
                        stack.peek().setTag_name(b);
                        state = "named_tag";
                    }
                    a = a.substring(i);
                    break;
                case "named_tag":
                    i = Lagoon.smallestPositive(new int[]{a.indexOf(">"),a.indexOf("/")});
                    b = a.substring(0,i).trim();
                    while(b.length() > 0){
                        int e = b.indexOf("=");
                        String c = b.substring(0,e==-1?b.length():e);
                        b = b.substring(e==-1?b.length():e+1);
                        b.replaceFirst("\"","");
                        e = b.indexOf("\"");
                        String d = b.substring(0,e==-1?b.length():e);
                        b = b.substring(e==-1?b.length():e+1);
                        stack.peek().addAttribute(c,d);
                    }
                    state = "end_of_tag";
                    a = a.substring(i);
                    if(a.startsWith("/>"))
                        ending = true;
                    break;
                case "end_document":
                    a = "";
                    break;
                case "end_of_tag":
                    i = a.indexOf(">");
                    b = a.substring(0,i);
                    //should only pop on endING tags, not the end of all tags
                    if(ending && stack.size() > 1) {
                        if(debug)
                            System.out.println("popping "+stack.pop().getTag_name());
                        else
                            stack.pop();
                    }
                    int z = 0;
                    i++;
                    a = a.substring(i);
                    state = "no_tag";
                    ending = false;
                    break;
                default:
                    System.out.println("Unhandled state:" + state);
                    break;
            }
            if(debug)
                System.out.println("parse:"+prev_state+"-"+b);
        }
        return stack.peekLast();
    }
}
