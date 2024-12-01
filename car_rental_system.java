 /*------------------Project-->>Car Rental System using Java Oops concepts----------*/

//All the necessary classes for this project
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//Represents the cars available for rent.

class Car
{
    //creating all the private data members of the Car class
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    //creating constructor to initialize the all the data members of the Car class
    //parameterised constructor 
    public Car(String cardId, String brand, String model, double basePricePerDay)
    {
        this.carId = cardId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

  //using the getter concept to get all the data members values
    //id suppose -->> 001
  public String getCarId()
  {
      return carId;
  }
//brank like mahindra , safari
  public String getBrand()
  {
      return brand;
  }

    //model like Thar
  public String getModel() {
        return model;
    }
//calculating the total price
  public double calculatePrice(int rentDays)
  {
      return rentDays*basePricePerDay;
  }
  public boolean isAvailable()
  {
      return isAvailable;
  }
  public void rent()
  {
      isAvailable = false;
  }
  public void returnCar()
  {
      isAvailable = true;
  }
}

//Represents the customers who rent cars
class Customer
{
    //creating the customer class data members that are private
    private String customerId;
    private String name;

    //creating the constructor of the customer class that is initialize the data members
    public Customer(String customerId, String name)
    {
        this.customerId = customerId;
        this.name = name;
    }
    //using the getter to get the data members values

    public String getCustomerId()
    {
        return customerId;
    }
    public String getName() {
        return name;
    }
}

//Represents a rental transaction.
class Rental
{
    //creating the data members of the Rental class
    private Car car;
    private Customer customer;
    private int days;

    //creating constructor of the Rental class
    public Rental(Car car,Customer customer,int days)
    {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    //using getters to get the values of the data members
    public Car getCar()
    {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays()
    {
        return days;
    }
}

//Core of the project, managing cars, customers, and rentals.
class CarRentalSystem {
    //creating the list of the above classes
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    //initializing the list using constructor of this class
    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    //using add method to add the cars,customers and rentals
    // Adds a new car to the system.
    public void addCar(Car car) {
        //add method used to add the car in the list
        cars.add(car);
    }

    //Registers a new customer.
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Handles the rental process.
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();//this merthod false the car in inventory means now this car is not avaible because it is already rented from the customer
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    //Handles returning a rented car.
    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    //Displays the menu for user interaction.
    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
        
            System.out.print("Enter your choice: ");    ETFU

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Car ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                    }
                }

                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }

}
public class Main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        // Adding initial cars
        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        Car car4 = new Car("C004", "Ford", "Mustang", 120.0);
        Car car5 = new Car("C005", "Hyundai", "Creta", 80.0);
        Car car6 = new Car("C006", "BMW", "X5", 200.0);
        Car car7 = new Car("C007", "Tesla", "Model 3", 150.0);
        Car car8 = new Car("C008", "Audi", "Q7", 250.0);
        Car car9 = new Car("C009", "Maruti", "Swift", 50.0);
        Car car10 = new Car("C010", "Chevrolet", "Cruze", 90.0);
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);
        rentalSystem.addCar(car7);
        rentalSystem.addCar(car8);
        rentalSystem.addCar(car9);
        rentalSystem.addCar(car10);

        // Launching the menu
        rentalSystem.menu();
    }
}
