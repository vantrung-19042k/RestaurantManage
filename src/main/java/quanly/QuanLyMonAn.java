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

public class QuanLyMonAn {
	private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "123456789";

	List<MonAn> dsMonAn = new ArrayList<MonAn>();
	
//themThucUong
	public void themThucUong(Scanner scanner) throws SQLException {
		System.out.println("=>Nhập thông tin thức uống");
		System.out.print(">Tên thức uống: ");
		String tenThucUong;
		tenThucUong = scanner.nextLine();

		System.out.println(">Giá bán: ");
		double giaBan;
		giaBan = scanner.nextDouble();
		
		scanner.nextLine();
		System.out.print(">Hãng sản xuất: ");
		String hangSx;
		hangSx = scanner.nextLine();

		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			
			String selectQuery = "select max(MaMonAn) from monan";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maMonAn = 0;
			while (rs.next()) {
				maMonAn = rs.getInt("max(MaMonAn)") + 1;
			}
			
			MonAn monAn = new ThucUong(maMonAn, tenThucUong, giaBan, hangSx);
			
			String insertQuery = "insert into monAn(MaMonAn, TenMonAn, GiaBan, HangSanXuat)"
					+ "values(?, ?, ?, ?)";
			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, maMonAn);
			preStm.setString(2, tenThucUong);
			preStm.setDouble(3, giaBan);
			preStm.setString(4, hangSx);
			preStm.executeUpdate();
			System.out.println("Thêm thành công");
				
			this.dsMonAn.add(monAn);	
		} catch (SQLException ex) {
			System.out.println("Thêm thất bại");
			System.err.println(ex.getMessage());
		}

		conn.close();
	}
	
//them thuc an
	public void themThucAn(Scanner scanner) throws SQLException {
		System.out.println("=>Nhập thông tin thức ăn");
		System.out.print(">Tên thức ăn: ");
		String tenThucAn;
		tenThucAn = scanner.nextLine();

		System.out.print(">Giá bán: ");
		double giaBan;
		giaBan = scanner.nextDouble();
		
		scanner.nextLine();
		System.out.println(">Ăn chay không");
		System.out.println("1.Có");
		System.out.println("2.Không");
		String anChay = null;
		int chonAnChay = scanner.nextInt();
		switch (chonAnChay) {
		case 1:
			anChay = "Có";
			break;
		case 2:
			anChay = "Không";
			break;
		default:
			break;
		}

		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			
			String selectQuery = "select max(MaMonAn) from monan";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maMonAn = 0;
			while (rs.next()) {
				maMonAn = rs.getInt("max(MaMonAn)") + 1;
			}
			
			MonAn monAn = new ThucAn(maMonAn, tenThucAn, giaBan, anChay);
			
			String insertQuery = "insert into monAn(MaMonAn, TenMonAn, GiaBan, AnChay)"
					+ "values(?, ?, ?, ?)";
			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, maMonAn);
			preStm.setString(2, tenThucAn);
			preStm.setDouble(3, giaBan);
			preStm.setString(4, anChay);
			preStm.executeUpdate();
			System.out.println("Thêm thành công");
				
			this.dsMonAn.add(monAn);
		} catch (SQLException ex) {
			System.out.println("Thêm thất bại");
			System.err.println(ex.getMessage());
		}

		conn.close();
	}
	
//them mon an
	public void themMonAn(Scanner scanner) throws SQLException {
		System.out.println("=>Bạn muốn thêm thức ăn hay thức uống");
		System.out.println("1.Thức ăn");
		System.out.println("2.Thức uống");
		System.out.print(">Bạn chọn: ");
		int select = scanner.nextInt();
		switch (select) {
		case 1:
			scanner.nextLine();
			this.themThucAn(scanner);
			break;
		case 2:
			scanner.nextLine();
			this.themThucUong(scanner);
			break;
		default:
			break;
		}
	}
	
//hien thi danh sach mon an
	public void hienThiDanhSachMonAn(Scanner scanner) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
		
		try {			
			String selectQuery = "select * from monan";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			while (rs.next()) {
				int maMonAn = rs.getInt("MaMonAn");
				String tenMon = rs.getString("TenMonAn");
				double giaBan = rs.getDouble("GiaBan");
				String anChay = rs.getString("AnChay");
				String hangSx = rs.getString("HangSanXuat");
				if(anChay == null) {
					MonAn thucUong = new ThucUong(maMonAn, tenMon, giaBan, hangSx);
					thucUong.showInfo();
				}
				else {
					MonAn thucAn = new ThucAn(maMonAn, tenMon, giaBan, anChay);
					thucAn.showInfo();
				}
			}									
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}		
		
		conn.close();
	}
	
// tra cuu mon an theo ten
		public List<MonAn> traCuuMonAnTheoTen(String tenMonCanTim) throws SQLException {
			Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
			List<MonAn> kq = new ArrayList<MonAn>();

			try {
				String selectQuery = "select * from monan where TenMonAn = ?";
				PreparedStatement preStm = conn.prepareStatement(selectQuery);
				preStm.setString(1, tenMonCanTim);
				ResultSet rs = preStm.executeQuery();
				
				while (rs.next()) {
					int maMonAn = rs.getInt("MaMonAn");
					String tenMon = rs.getString("TenMonAn");
					double giaBan = rs.getDouble("GiaBan");
					String anChay = rs.getString("AnChay");
					String hangSx = rs.getString("HangSanXuat");
					if(anChay == null) {
						MonAn thucUong = new ThucUong(maMonAn, tenMon, giaBan, hangSx);
						kq.add(thucUong);
					}
					else {
						MonAn thucAn = new ThucAn(maMonAn, tenMon, giaBan, anChay);
						kq.add(thucAn);
					}
				}					
			} catch (SQLException ex) {
				System.err.println(ex.getMessage());
			}
			conn.close();
			return kq;
		}	

// getter and setter
	public List<MonAn> getDsMonAn() {
		return dsMonAn;
	}

	public void setDsMonAn(List<MonAn> dsMonAn) {
		this.dsMonAn = dsMonAn;
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
}
