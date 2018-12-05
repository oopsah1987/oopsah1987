package test;

import com.haniln.hicp.components.utils.Util;

public class Test {
    public static void main(String[] args) {
        try {
            Test.test();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() throws Exception {
        System.out.println(Util.getTime("yyyyMMddHHmm"));
        String time = Util.toLocaleTimeString(Util.getTime("yyyyMMddHHmm"), "ko");
        System.out.println("한글 = "+time);
    }
}