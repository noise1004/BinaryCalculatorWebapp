package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 */
public class Binary
{
    private String number="0";  // string containing the binary value '0' or '1'

    /**
     * A constructor that generates a binary object.
     *
     * @param number a String of the binary values. It should contain only zeros or ones with any length and order.
     * otherwise, the value of "0" will be stored. Leading zeros will be excluded and empty string will be considered as zero.
     */
    public Binary(String number) {
        if (number == null || number.isEmpty()) {
            this.number = "0";
            return;
        }

        // Validate binary string
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            if (ch != '0' && ch != '1') {
                this.number = "0";
                return;
            }
        }

        // Remove leading zeros
        int beg;
        for (beg = 0; beg < number.length(); beg++) {
            if (number.charAt(beg) != '0') {
                break;
            }
        }

        this.number = (beg == number.length()) ? "0" : number.substring(beg);

        if (this.number.isEmpty()) this.number = "0";
    }

    public String getValue()
    {
        return this.number;
    }

    public static Binary add(Binary num1,Binary num2)
    {
        int ind1=num1.number.length()-1;
        int ind2=num2.number.length()-1;
        int carry=0;
        String num3="";
        while(ind1>=0 ||  ind2>=0 || carry!=0)
        {
            int sum=carry;
            if(ind1>=0){
                sum += (num1.number.charAt(ind1)=='1')? 1:0;
                ind1--;
            }
            if(ind2>=0){
                sum += (num2.number.charAt(ind2)=='1')? 1:0;
                ind2--;
            }
            carry=sum/2;
            sum=sum%2;
            num3 = ((sum==0)? "0":"1") + num3;
        }
        return new Binary(num3);
    }

    // & operator (bitwise AND, align by left-padding zeros)
    public static Binary and(Binary num1, Binary num2) {
        String a = num1.number;
        String b = num2.number;

        int max = Math.max(a.length(), b.length());
        a = leftPadZeros(a, max);
        b = leftPadZeros(b, max);

        StringBuilder out = new StringBuilder(max);
        for (int i = 0; i < max; i++) {
            out.append((a.charAt(i) == '1' && b.charAt(i) == '1') ? '1' : '0');
        }
        return new Binary(out.toString());
    }

    // I operator (inclusive OR) — implemented as "or"
    public static Binary or(Binary num1, Binary num2) {
        String a = num1.number;
        String b = num2.number;

        int max = Math.max(a.length(), b.length());
        a = leftPadZeros(a, max);
        b = leftPadZeros(b, max);

        StringBuilder out = new StringBuilder(max);
        for (int i = 0; i < max; i++) {
            out.append((a.charAt(i) == '1' || b.charAt(i) == '1') ? '1' : '0');
        }
        return new Binary(out.toString());
    }

    // * operator (multiply using shift+add)
    public static Binary multiply(Binary num1, Binary num2) {
        if (num1.number.equals("0") || num2.number.equals("0")) return new Binary("0");

        Binary result = new Binary("0");
        String multiplier = num2.number;
        String multiplicand = num1.number;

        int shift = 0;
        for (int i = multiplier.length() - 1; i >= 0; i--) {
            if (multiplier.charAt(i) == '1') {
                Binary shifted = new Binary(multiplicand + "0".repeat(shift));
                result = add(result, shifted);
            }
            shift++;
        }
        return result;
    }

    private static String leftPadZeros(String s, int len) {
        if (s.length() >= len) return s;
        return "0".repeat(len - s.length()) + s;
    }
}