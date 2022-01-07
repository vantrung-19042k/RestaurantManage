package quanly;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		QuanLyMonAn ql = new QuanLyMonAn();
		boolean continued = true;
		do {
		ql.themMonAn(scanner);
		scanner.nextLine();
		System.out.print("Tiếp tục: ");
		String tiepTuc = scanner.nextLine();
		if (tiepTuc.toLowerCase().contains("c"))
			continued = true;
		else 
			continued = false;
		}while(continued == true);
	}
}
