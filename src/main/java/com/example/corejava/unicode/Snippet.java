package com.example.corejava.unicode;

/**
 * Unicode字符集中每个字符对应一个码点（code point），也就是一个编码表中的代码值。码点用前缀U+加上十六进制表示。
 * 
 * 早期Unicode字符集设计为最大范围只有65536个字符。
 * 因此Java中的char类型设计为占2个字节，范围为U+0000到U+FFFF，可以表示所有早期Unicode字符。
 * 
 * 后来Unicode字符集进行了扩充，2个字节的char就无法表示所有字符了。
 * 
 * 现在的Unicode的码点分成了17个代码级别（code plane）：
 * 第一个代码级别成为基本的多语言级别（basic multilingual plane）从U+0000到U+FFFF，其中包含经典的Unicode字符。
 * 其余的代码级别从U+10000到U+10FFFF，包含辅助字符（supplementary character）。
 *
 * UTF-16编码采用不同长度的编码表示所有Unicode码点：
 * 1. 在基本的多语言级别中，每个Unicode字符用2个字节表示，称为代码单元（code unit）
 * 2. 而辅助字符采用2个代码单元来编码，使用特定的替代区域（surrogate area）：第一个码元范围U+D800到U+DBFF，第二个码元范围U+DC00到U+DFFF。
 * 这样就可以通过码元的数值区域，知道一个码元是一个Unicode字符的编码，还是一个辅助字符的第一或第二部分。
 * 
 * char类型表示了UTF-16编码中的一个码元。String.length方法返回的是char的数量，这个数值不一定等于Unicode字符数量。
 */
public class Snippet {

    public static void main(String[] args) {
        String x = new StringBuilder().appendCodePoint(0x10ffff).toString();
        System.out.println(x);
        System.out.println("\udbff\udfff");
        System.out.println(x.length());
        System.out.println(x.codePointCount(0, x.length()));
        System.out.println(x.getBytes().length);
        x.codePoints().forEach(System.out::println);
        x.chars().forEach(c -> System.out.println(Integer.toHexString(c)));

        int[] codePoints = x.codePoints().toArray();
        String x1 = new String(codePoints, 0, codePoints.length);
        System.out.println(x.equals(x1));
    }

}
