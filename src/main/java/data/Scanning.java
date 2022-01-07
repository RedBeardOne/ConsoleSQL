package data;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Scanner;

public class Scanning {
    Statement statement;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        DB_commands commands = new DB_commands();
        boolean flag = true;
        while (flag) {
            System.out.println("Make chose number:");
            System.out.println("1 - create table ");
            System.out.println("2 - add to table ");
            System.out.println("3 - sort table by asc ");
            System.out.println("4 - search by part of text ");
            System.out.println("5  - delete table ");
            System.out.println("6  - close application");
            System.out.println("Other command unsupported ");
            String command = scanner.next();

            flag = command(scanner, commands, flag, command);
        }
    }

    private boolean command(Scanner scanner, DB_commands commands, boolean flag, String command) {
        switch (command) {
            case "1":
                creating(scanner, commands);
                break;
            case "2":
                updating(scanner, commands);
                break;

            case "3":
                sorting(scanner, commands);
                break;

            case "4":
                searching(scanner, commands);
                break;

            case "5":
                deleting(scanner, commands);
                break;

            case "6":
                return false;

            default:
                System.out.println("This command unacceptable ");
                break;
        }
        return flag;
    }

    private void deleting(Scanner scanner, DB_commands commands) {
        System.out.println("Input name of table to delete");
        String data4 = scanner.next().trim().toLowerCase(Locale.ROOT);
        System.out.println("The delete operation is irreversible, enter \"Y\" if you want to delete the table");
        String confirmation = scanner.next();
        if (confirmation.equals("Y")) {
            try {
                commands.deleteDB(data4);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void searching(Scanner scanner, DB_commands commands) {
        System.out.println("Input name of table and keyword to search");
        System.out.println("Name of table, keyword");
        String data3 = scanner.next().trim().toLowerCase(Locale.ROOT);
        String[] dataParsedSearch = data3.split(",");
        try {
            commands.selectFromDB(dataParsedSearch[0], dataParsedSearch[1]);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void sorting(Scanner scanner, DB_commands commands) {
        System.out.println("Input name of table to sort");
        String data2 = scanner.next().trim().toLowerCase(Locale.ROOT);
        try {
            commands.sortDB(data2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updating(Scanner scanner, DB_commands commands) {
        System.out.println("Input name of table to update & data in order:");
        System.out.println("Name of table, ID, name, mail, age");
        String data1 = scanner.next().trim().toLowerCase(Locale.ROOT);
        String[] dataParsed = data1.split(",");
        try {
            commands.updateDB(dataParsed[0], Integer.getInteger(dataParsed[1]), dataParsed[2], dataParsed[3], Integer.getInteger(dataParsed[4]));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void creating(Scanner scanner, DB_commands commands) {
        System.out.println("Input name of table:");
        String table = scanner.next().trim().toLowerCase(Locale.ROOT);
        try {
            commands.createDB(table);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

