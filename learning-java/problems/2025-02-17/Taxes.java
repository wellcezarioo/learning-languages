package Aplication;

import java.util.ArrayList;
import java.util.Scanner;

abstract class TaxPayer {
    protected String name;
    protected double anualIncome;

    public TaxPayer(String name, double anualIncome) {
        this.name = name;
        this.anualIncome = anualIncome;
    }

    public String getName() {
        return name;
    }

    public double getAnualIncome() {
        return anualIncome;
    }

    public void setAnualIncome(double anualIncome) {
        this.anualIncome = anualIncome;
    }

    public abstract double calculateTaxes();

}

class Individual extends TaxPayer {
    private double healthExpenditures;

    public Individual(String name, double anualIncome, double healthExpenditures){
        super(name, anualIncome);
        this.healthExpenditures = healthExpenditures;
    }

    public double getHealthExpenditures() {
        return healthExpenditures;
    }

    public void setHealthExpenditures(double healthExpenditures) {
        this.healthExpenditures = healthExpenditures;
    }

    @Override
    public double calculateTaxes() {

        if(anualIncome < 20000 ){
            return anualIncome*0.15 - healthExpenditures*0.5;
        }

        return anualIncome*0.25 - healthExpenditures*0.5;
    }
}

class Company extends TaxPayer {
    private int employees;

    public Company(String name, double anualIncome, int employees){
        super(name, anualIncome);
        this.employees = employees;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    @Override
    public double calculateTaxes() {
        if(employees < 10){
            return anualIncome * 0.16;
        }

        return anualIncome * 0.14;
    }
}


public class Taxes {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of taxes payers: ");
        int payers = sc.nextInt();

        sc.nextLine();

        ArrayList <TaxPayer> payerlist= new ArrayList <TaxPayer>();

        for(int i = 1; i <= payers; i++){
            System.out.println("Payer #" + i + " data:");
            String payerType;

            System.out.print("Individual or company (i/c)? ");
            payerType = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Anual income: ");
            double anualIncome = sc.nextDouble();

            if(payerType.equals("i")){

                System.out.print("Health expenditures: ");
                double expenditures = sc.nextDouble();
                payerlist.add(new Individual(name, anualIncome, expenditures));
            }
            else if(payerType.equals("c")){

                System.out.print("Number of employees: ");
                int employees = sc.nextInt();
                payerlist.add(new Company(name, anualIncome, employees));
            }

            sc.nextLine();
        }

        double sum = 0;
        System.out.println("\nTAXES PAID: ");
        for(TaxPayer i: payerlist){
            System.out.println(i.getName() + " $ " + String.format("%.2f", i.calculateTaxes()));
            sum += i.calculateTaxes();
        }

        System.out.print("\nTOTAL TAXES: $ " + String.format("%.2f", sum));
        sc.close();
    }
}
