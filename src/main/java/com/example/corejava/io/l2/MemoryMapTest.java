package com.example.corejava.io.l2;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.LongSupplier;
import java.util.zip.CRC32;

/**
 * 为了对比性能差异，示例中全部以一个字节为单位读取，实践中会一次性读取更多字节数组。
 */
public class MemoryMapTest {

    public static long checksumInputStream(Path filename) throws IOException {
        try (InputStream in = Files.newInputStream(filename)) {
            CRC32 crc = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumBufferedInputStream(Path filename) throws IOException {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filename))) {
            CRC32 crc = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumRandomAccessFileRead(Path filename) throws FileNotFoundException, IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename.toFile(), "r")) {
            CRC32 crc = new CRC32();

            int c;
            while ((c = file.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumRandomAccessFileSeek(Path filename) throws FileNotFoundException, IOException {
        try (RandomAccessFile file = new RandomAccessFile(filename.toFile(), "r")) {
            long length = file.length();
            CRC32 crc = new CRC32();

            int c;
            for (long p = 0; p < length; p++) {
                file.seek(p);
                c = file.readByte();
                crc.update(c);
            }
            return crc.getValue();
        }
    }

    public static long checksumMappedFile(Path filename) throws FileNotFoundException, IOException {
        try (FileChannel channel = FileChannel.open(filename)) {
            long length = channel.size();
            CRC32 crc = new CRC32();

            MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, length);
            for (int p = 0; p < length; p++) {
                crc.update(buffer.get(p));
            }
            return crc.getValue();
        }
    }

    public static void main(String[] args) throws IOException {
        Path filename = Paths.get(System.getenv("JAVA_HOME"), "lib", "src.zip");

        time(() -> {
            try {
                // return checksumInputStream(filename);
                // return checksumBufferedInputStream(filename);
                // return checksumRandomAccessFileRead(filename);
                // return checksumRandomAccessFileSeek(filename);
                return checksumMappedFile(filename);
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }

    public static void time(LongSupplier supplier) {
        long start = System.currentTimeMillis();
        long checksum = supplier.getAsLong();
        long end = System.currentTimeMillis();
        System.out.println(checksum);
        System.out.println((end - start) + " ms");
    }

}
