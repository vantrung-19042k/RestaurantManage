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

public class QuanLyThoiDiem {
	private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "123456789";
	private List<ThoiDiem> danhSach = new ArrayList<ThoiDiem>();

//them thoi diem cho thue
	public void themThoiDiem(Scanner scanner) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		System.out.println("=>NHẬP THÔNG TIN THỜI ĐIỂM CẦN THÊM");
		scanner.nextLine();
		System.out.print(">Nhập thời điểm: ");
		String thoiDiem;
		thoiDiem = scanner.nextLine();

		System.out.println(">Nhập hệ số cho thuê");
		double heSoChoThue;
		heSoChoThue = scanner.nextDouble();

		try {
			String selectQuery = "select max(MaThoiDiem) from thoidiemthue";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maThoiDiem = 0;
			while (rs.next()) {
				maThoiDiem = rs.getInt("max(MaThoiDiem)") + 1;
			}

			ThoiDiem thoiDiemThue = new ThoiDiem(maThoiDiem, thoiDiem, heSoChoThue);

			String insertQuery = "insert into thoidiemthue(MaThoiDiem, ThoiDiemThue, HeSoChoThue)" + "values(?, ?, ?)";
			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, thoiDiemThue.getMaThoiDiem());
			preStm.setString(2, thoiDiemThue.getThoiDiemThue());
			preStm.setDouble(3, thoiDiemThue.getHeSoChoThue());
			preStm.executeUpdate();
			System.out.println("Thêm thành công");
			

		} catch (SQLException ex) {
			System.out.println("Thêm thất bại");
			System.err.println(ex.getMessage());
		}
		conn.close();
	}

//hien thi thoi diem cho thue
	public void hienThiThoiDiem(Scanner scanner) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			String selectQuery = "select * from thoidiemthue";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			while (rs.next()) {
				int maThoiDiem = rs.getInt("MaThoiDiem");
				String thoiDiemThue = rs.getString("ThoiDiemThue");
				double heSoChoThue = rs.getDouble("HeSoChoThue");
				ThoiDiem thoiDiem = new ThoiDiem(maThoiDiem, thoiDiemThue, heSoChoThue);
				thoiDiem.showInfo();
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		conn.close();
	}

//getter and setter
	public List<ThoiDiem> getDanhSach() {
		return danhSach;
	}

	public void setDanhSach(List<ThoiDiem> danhSach) {
		this.danhSach = danhSach;
	}

// tạo kết nối mysql
	public static Connection getConnection(String dbURL, String userName, String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, userName, password);
			// System.out.println("connect successfully!");
		} catch (Exception ex) {
			System.out.println("connect failure!");
			ex.printStackTrace();
		}
		return conn;
	}
}
