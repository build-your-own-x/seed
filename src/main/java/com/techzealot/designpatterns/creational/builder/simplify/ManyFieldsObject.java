package com.techzealot.designpatterns.creational.builder.simplify;

public class ManyFieldsObject {

    private String s;
    private int i;
    private boolean b;
    private double d;

    private ManyFieldsObject() {
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "ManyFieldsObject{" +
                "s='" + s + '\'' +
                ", i=" + i +
                ", b=" + b +
                ", d=" + d +
                '}';
    }

    public static class Builder {
        private String s;
        private int i;
        private boolean b;
        private double d;

        public Builder s(String s) {
            this.s = s;
            return this;
        }

        public Builder i(int i) {
            this.i = i;
            return this;
        }

        public Builder b(boolean b) {
            this.b = b;
            return this;
        }

        public Builder d(double d) {
            this.d = d;
            return this;
        }

        public ManyFieldsObject build() {
            ManyFieldsObject manyFieldsObject = new ManyFieldsObject();
            manyFieldsObject.setS(s);
            manyFieldsObject.setI(i);
            manyFieldsObject.setB(b);
            manyFieldsObject.setD(d);
            return manyFieldsObject;
        }

    }

}
