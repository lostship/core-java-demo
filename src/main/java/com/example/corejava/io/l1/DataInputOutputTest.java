package com.example.corejava.io.l1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DataInputOutputTest {

    public static void main(String[] args) {
        try (RandomAccessFile inOut = new RandomAccessFile("d:/employee.dat", "rw")) {
            inOut.length();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length()) {
                ch = s.charAt(i);
            }
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            char ch = in.readChar();
            if (ch == 0) {
                break;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

}
