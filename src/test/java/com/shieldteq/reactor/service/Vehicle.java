package com.shieldteq.reactor.service;

public class Vehicle {
    public void printVehicle() {
        System.out.println("This is a vehicle");
    }
}
class Car extends Vehicle {
    @Override
    public void printVehicle() {
        System.out.println("This is a car");
    }
}
class Bicycle extends Vehicle {
    @Override
    public void printVehicle() {
        System.out.println("This is a bicycle");
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Car();
        vehicle.printVehicle();
        vehicle = new Bicycle();
        vehicle.printVehicle();
    }
}
