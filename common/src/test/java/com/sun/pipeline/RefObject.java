package com.sun.pipeline;

/**
 * Created by zksun on 21/05/2017.
 */
public class RefObject {
    private Integer testInt = 0;

    public Integer getTestInt() {
        return testInt;
    }

    public void setTestInt(RefObject value) {
        value.testInt = 100;
        System.out.println(value);
    }

    public static void main(String[] args) {
        RefObject refObject = new RefObject();
        refObject.setTestInt(refObject);

        System.out.println(refObject.getTestInt());
    }

}
