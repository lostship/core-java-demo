package com.example.corejava.classes.class1;

import java.io.IOException;

public class Manager extends Employee implements Greetable, Byeable {

    private double bonus;

    public Manager(String name, double salary, double bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    @Override
    public double getSalary() {
        double baseSalary = super.getSalary();
        return baseSalary + bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) {
            return false;
        }

        Manager other = (Manager) otherObject;
        return bonus == other.bonus;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + 17 * Double.hashCode(bonus);
    }

    @Override
    public String toString() {
        return super.toString()
                + "[bonus=" + bonus
                + "]";
    }

    @Override
    public void bye(String name) {
        try {
            Byeable.super.bye(name);
        } catch (IOException e) {
            RuntimeException re = new RuntimeException();
            re.initCause(e);
            throw re;
        }
    }

    @Override
    protected void protect() throws IOException {
        // TODO Auto-generated method stub
        try {
            super.protect();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

}
