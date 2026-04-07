package com.example.springbootapi.leetcode;

/*
Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.

The algorithm for myAtoi(string s) is as follows:

Whitespace: Ignore any leading whitespace (" ").
Signedness: Determine the sign by checking if the next character is '-' or '+', assuming positivity if neither present.
Conversion: Read the integer by skipping leading zeros until a non-digit character is encountered or the end of the string is reached. If no digits were read, then the result is 0.
Rounding: If the integer is out of the 32-bit signed integer range [-231, 231 - 1], then round the integer to remain in the range. Specifically, integers less than -231 should be rounded to -231, and integers greater than 231 - 1 should be rounded to 231 - 1.
Return the integer as the final result.

Example 1:

Input: s = "42"

Output: 42

Explanation:

The underlined characters are what is read in and the caret is the current reader position.
Step 1: "42" (no characters read because there is no leading whitespace)
         ^
Step 2: "42" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "42" ("42" is read in)
*/
public class StringToInteger {
    public Integer myAtoi(String s) {
        //reading stops at the first non-digit char, reading reads - and +, ignore leading white spaces.
        s = s.trim();  // Ignore leading white spaces
        String result = "";

        
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i); //see if first character is 0
            if (Character.isDigit(c)) {
                result += c;
            } else if ((c == '-' || c == '+') && result.isEmpty()) {
                // Only allow sign if result is empty (beginning only)
                result += c;
            } else {                                                                                            
                break;  // Stop at first non-digit/non-sign character
            }
        }
        
        if (result.isEmpty() || result.equals("+") || result.equals("-")) {
            return 0;  // No digits found
        }
        
        if (Integer.parseInt(result) > Integer.MAX_VALUE) {
            if (result.charAt(0) == '+') {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
                }
        }

        return Integer.parseInt(result);
    }   


    //remember s.trim() remmeber reading it as a char char c = s.charAt(i) <-- reading string. Integer.parseInt(); int to string would be String.valueOf(integer);
}


