package quanly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Test {
	private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "123456789";

	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			String selectQuery = "select max(MaKhachHang) from sanhcuoi";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maKhachHang = 0;
			while (rs.next()) {
				maKhachHang = rs.getInt("max(MaKhachHang)") + 1;
			}
			String insertQuery = "insert into khachhang(MaKhachHang, TenKhachHang, SDT)"
					+ "values(?, ?, ?)";
			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, maKhachHang);
//			preStm.setString(2, this.tenKhachHang);
//			preStm.setString(3, this.sdtKhachHang);			
			int ketQua = preStm.executeUpdate();
			if (ketQua == 1)
				System.out.println("Hoàn tất nhập thông tin khách hàng");
			else
				System.out.println("Nhập thông tin khách hàng thất bại");
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		
		conn.close();
		scanner.close();
	}

	// tạo kết nối
	public static Connection getConnection(String dbURL, String userName, String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, userName, password);
			System.out.println("connect successfully!");
		} catch (Exception ex) {
			System.out.println("connect failure!");
			ex.printStackTrace();
		}
		return conn;
	}
}
