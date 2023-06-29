package org.example;

public class JdbcMain {
    public static void main(String[] args) {
        System.out.println(isPalindrome("bO н,е к-4574857373,,96-73к8+56    ()еН#^&ob"));
    }

    public static boolean isPalindrome(String ex) {
        String st = ex.replaceAll("[^a-zA-Z_А-Яа-я]", "").toUpperCase();
        StringBuilder sb = new StringBuilder(st);
        StringBuilder reverse = sb.reverse();

        for (int i = 0; i < st.length()/2; i++) {
            if (st.charAt(i) != reverse.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
