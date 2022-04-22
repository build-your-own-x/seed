package com.techzealot.designpatterns.structural.decorator.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DecryptOutputStream extends FilterOutputStream {

    public DecryptOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * 输出流所有输出方法都会调用该方法
     *
     * @param b the {@code byte}.
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        //将字母循环右移一位
        if (b >= 65 && b <= 90) {
            b += 1;
            if (b > 90) {
                b = 65;
            }
        }
        if (b >= 97 && b <= 122) {
            b += 1;
            if (b > 122) {
                b = 97;
            }
        }
        super.write(b);
    }
}
