package com.example.corejava.classes.class1;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Employee extends Person implements Comparable<Employee>, Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    private double salary;
    private transient Point2D.Double point;

    public Employee(String name, double salary) {
        super(name);
        this.salary = salary;

        this.point = new Point2D.Double(Math.random(), Math.random());
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public Integer getNumber() {
        return 1;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public void hello(String somebody) {
        System.out.println("hello " + somebody);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }

        if (otherObject == null) {
            return false;
        }

        // 如果子类能够拥有自己的equals概念，则对称性需求将强制采用getClass进行检测
        if (getClass() != otherObject.getClass()) {
            return false;
        }

        // 如果子类有超类决定equals概念，就可以使用instanceof进行检测，并将equals方法声明为final
        // public final boolean equals(Object otherObject) {
        // ...
        // if (!(otherObject instanceof Employee)) return false;
        // ...
        // }

        Employee other = (Employee) otherObject;

        return Objects.equals(getName(), other.getName())
                && salary == other.salary;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), salary);
    }

    @Override
    public String toString() {
        return getClass().getName()
                + "[name=" + getName()
                + ",salary=" + salary
                + "]";
    }

    // 同equals方法一样，要考虑子类定义不同含义比较的情况
    @Override
    public final int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }

    protected void protect() throws Exception {

    }

    @Override
    protected Employee clone() throws CloneNotSupportedException {
        Employee cloned = (Employee) super.clone();

        // 引用类型实例域拷贝

        return cloned;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(point.getX());
        out.writeDouble(point.getY());
    }

    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        double x = in.readDouble();
        double y = in.readDouble();
        point = new Point2D.Double(x, y);
    }

}
