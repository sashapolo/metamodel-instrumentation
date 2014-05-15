import java.util.Scanner;

class While {
  public static void main(String[] args) {
    int n;

    Scanner input = new Scanner(System.in);
    System.out.println("Input an integer");
    n = input.nextInt();

    while (n != 0) {
      System.out.println("You entered " + n);
      System.out.println("Input an integer");
      n = input.nextInt();
    }
  }
}
