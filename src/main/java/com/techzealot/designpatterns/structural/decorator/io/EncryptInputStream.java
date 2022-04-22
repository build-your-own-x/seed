package com.techzealot.designpatterns.structural.decorator.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流必须重写两个read方法保障所有输入渠道都被增强(InputStream中这两个方法为native,所有读取都调用这两个方法)
 */
public class EncryptInputStream extends FilterInputStream {

    protected EncryptInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        return shiftAsciiRight(in.read());
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = in.read(b, off, len);
        if (read == -1) {
            return -1;
        }
        for (int i = 0; i < read; i++) {
            b[i] = (byte) shiftAsciiRight(b[i]);
        }
        return read;
    }

    private int shiftAsciiRight(int b) {
        //将字母循环左移一位
        if (b >= 65 && b <= 90) {
            b -= 1;
            if (b < 65) {
                b = 90;
            }
        } else if (b >= 97 && b <= 122) {
            b -= 1;
            if (b < 97) {
                b = 122;
            }
        }
        return b;
    }
}
