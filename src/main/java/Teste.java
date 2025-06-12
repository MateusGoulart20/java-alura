import java.util.Scanner;

public class Teste {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite algo: ");
        String entrada = scanner.nextLine();
        System.out.println("Você digitou: " + entrada);
    }
}