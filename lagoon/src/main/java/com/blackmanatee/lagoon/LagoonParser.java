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
                    if(a.charAt(0) == '?'){
                        stack.push(new Tag());
                        state = "new_tag";
                    }
                    break;
                case "no_tag":
                    i = a.indexOf("<");
                    b = a.substring(0, i).trim();
                    a = a.substring(i++);
                    if (b.length() > 0)
                        stack.peek().setContent(b);
                    Tag t = new Tag();
                    stack.peek().addTag(t);
                    stack.push(t);
                    state = "new_tag";
                    break;
                case "new_tag":
                    i = Lagoon.smallestPositive(new int[]{a.indexOf(">"), a.indexOf(" "),a.indexOf("/")});
                    b = a.substring(0, i);
                    if(a.charAt(i) == ' ')
                        i++;
                    else if(a.charAt(i) == '/'){
                        state = "end_tag";
                    }
                    if(b.length() > 0) {
                        if (stack.peekLast().getTag_name().equals(b)) {
                            state = "end_document";
                        } else {
                            stack.peek().setTag_name(b);
                            state = "named_tag";
                        }
                    }
                    a = a.substring(i);
                    break;
                case "named_tag":
                    i = Lagoon.smallestPositive(new int[]{a.indexOf(">"),a.indexOf("/")});
                    b = a.substring(0,i).trim();
                    while(b.length() > 0){
                        int e = b.indexOf("=");
                        String c = b.substring(0,e==-1?b.length():e);
                        b = b.substring(e==-1?b.length():e++);
                        b.replaceFirst("\"","");
                        e = b.indexOf("\"");
                        String d = b.substring(0,e==-1?b.length():e);
                        b = b.substring(e==-1?b.length():e++);
                        stack.peek().addAttribute(c,d);
                    }
                    if(a.charAt(i) == '/') {
                        state = "end_tag";
                    }
                    a = a.substring(i);
                    break;
                case "end_document":
                    a = "";
                    break;
                case "end_tag":
                    stack.pop();
                    int z = 0;
                    while(z<a.length()&&(a.charAt(z)=='/'||a.charAt(z)=='>')){
                        z++;
                    }
                    a = a.substring(z);
                    break;
                default:
                    System.out.println("Unhandled state:" + state);
                    break;
            }
        }
        return stack.peekLast();
    }
}
