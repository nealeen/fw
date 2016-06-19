package net.zdsoft.framework.utils;

public class Tester {

    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        if(cs[0] > 'z' || cs[0] < 'a')
            return name;
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static void main(String[] args) {
        System.out.println(captureName("Asdfev"));
    }
}
