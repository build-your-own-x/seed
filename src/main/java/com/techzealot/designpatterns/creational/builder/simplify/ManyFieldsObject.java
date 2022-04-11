package com.techzealot.designpatterns.creational.builder.simplify;

import lombok.Data;

@Data
public class ManyFieldsObject {

    private String s;
    private int i;
    private boolean b;
    private double d;

    private ManyFieldsObject() {
    }

    public static Builder builder(){
        return new Builder();
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
