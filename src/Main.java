import network.NetworkService;

import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NetworkService.getInstance().start();
        Scanner scanner = new Scanner(System.in);

        String line = "";

        while(!line.equals("exit")) {
            line = scanner.nextLine();
        }

        NetworkService.getInstance().stop();

    }
}