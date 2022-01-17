/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pstu;

/**
 *
 * @author ASUS
 */
public class OneClass {
    public static void main(String[] args) {
        //need reference to other object...
        AnotherClass another = new AnotherClass();
        System.out.println("In OneClass, sending a value to another object.");
        another.setStr("Here is my value.");
        another.printStr();
    }
}

    class AnotherClass {
        private String str;

        public void setStr(String str) {
          System.out.println("In another. Setting value...");
          this.str = str;
        }

        public void printStr() {
          System.out.println("In another. Printing value...");
          System.out.println(str);
        }
    }
