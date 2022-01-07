package quanly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLyDichVu {
	private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "123456789";

	private List<DichVu> dsDichVu = new ArrayList<DichVu>();

// them dich vu
	public void themDichVu(Scanner scanner) throws SQLException {
		System.out.println("=>Nhập thông tin dịch vụ cần thêm");
		System.out.println(">Nhập tên dịch vụ: ");
		String tenDichVu;
		tenDichVu = scanner.nextLine();

		System.out.println(">Nhập giá dịch vụ: ");
		double giaDichVu;
		giaDichVu = scanner.nextDouble();

		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			String selectQuery = "select max(MaDichVu) from dichvu";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maDichVu = 0;
			while (rs.next()) {
				maDichVu = rs.getInt("max(MaDichVu)") + 1;
			}

			String insertQuery = "insert into dichvu(MaDichVu, TenDichVu, GiaDichVu)" + "values(?, ?, ?)";

			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, maDichVu);
			preStm.setString(2, tenDichVu);
			preStm.setDouble(3, giaDichVu);
			preStm.executeUpdate();
			System.out.println("Thêm dịch vụ thành công");
				
		} catch (SQLException ex) {
			System.out.println("Thêm dịch vụ thất bại");
			System.err.println(ex.getMessage());
		}
		conn.close();
	}

// tạo kết nối
	public static Connection getConnection(String dbURL, String userName, String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, userName, password);
			//System.out.println("connect successfully!");
		} catch (Exception ex) {
			System.out.println("connect failure!");
			ex.printStackTrace();
		}
		return conn;
	}

//Getter and setter
	public List<DichVu> getDsDichVu() {
		return dsDichVu;
	}

	public void setDsDichVu(List<DichVu> dsDichVu) {
		this.dsDichVu = dsDichVu;
	}
}
