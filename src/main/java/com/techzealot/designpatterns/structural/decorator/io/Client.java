package com.techzealot.designpatterns.structural.decorator.io;

import com.google.common.io.ByteStreams;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Client {
    public static void main(String[] args) throws IOException {
        InputStream in = new BufferedInputStream(new EncryptInputStream(new ByteArrayInputStream("abcdeABCDE".getBytes())));
        byte[] bytes = ByteStreams.toByteArray(in);
        String output = new String(bytes);
        System.out.println(output);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        OutputStream out = new DecryptOutputStream(bao);
        out.write(bytes);
        out.flush();
        System.out.println(new String(bao.toByteArray()));
    }
}
