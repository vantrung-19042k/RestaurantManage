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

public class QuanLySanhCuoi {
	private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "123456789";

	private List<SanhCuoi> danhSachSC = new ArrayList<SanhCuoi>();

// thêm sảnh cưới
	public void themSanhCuoi(Scanner scanner) throws SQLException {
		System.out.println("=>NHẬP THÔNG TIN SẢNH CƯỚI CẦN THÊM");
		System.out.print(">Nhập tên sảnh: ");
		String tenSanh;
		tenSanh = scanner.nextLine();

		System.out.println("Chọn vị trí: ");
		System.out.println("1.Tầng 1");
		System.out.println("2.Tầng 2");
		System.out.println("3.Tầng 3");
		System.out.println("4.Tầng 4");
		System.out.println("Bạn chọn: ");
		
		String viTri = "null";
		int chonViTri = scanner.nextInt();
		switch (chonViTri) {
		case 1:
			viTri = "Tầng 1";
			break;
		case 2:
			viTri = "Tầng 2";
			break;
		case 3:
			viTri = "Tầng 3";
			break;
		case 4:
			viTri = "Tầng 4";
			break;
		default:
			break;
		}

		System.out.print(">Nhập sức chứa: ");
		int sucChua;
		sucChua = scanner.nextInt();

		System.out.print(">Nhập giá thuê cơ bản: ");
		double giaThueCb;
		giaThueCb = scanner.nextDouble();

		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		try {
			String selectQuery = "select max(MaSanhCuoi) from sanhcuoi";
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(selectQuery);
			int maSanhCuoi = 0;
			while (rs.next()) {
				maSanhCuoi = rs.getInt("max(MaSanhCuoi)") + 1;
			}

			quanly.SanhCuoi sanhCuoi = new quanly.SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);

			String insertQuery = "insert into sanhcuoi(MaSanhCuoi, TenSanh, ViTri, SucChua, GiaThueCoBan)"
					+ "values(?, ?, ?, ?, ?)";

			PreparedStatement preStm = conn.prepareStatement(insertQuery);
			preStm.setInt(1, maSanhCuoi);
			preStm.setString(2, sanhCuoi.getTenSanh());
			preStm.setString(3, sanhCuoi.getViTri());
			preStm.setInt(4, sanhCuoi.getSucChua());
			preStm.setDouble(5, sanhCuoi.getGiaThueCoBan());
			preStm.executeUpdate();
			System.out.println("Thêm thành công");
				
		} catch (SQLException ex) {
			System.out.println("Thêm thất bại");
			System.err.println(ex.getMessage());
		}
		conn.close();
	}

	// hiển thị danh sách sảnh cưới
	public void hienThiDanhSachSanhCuoi() throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
		//lay danh sach sanh cuoi tu csdl
		System.out.println("=>DANH SÁCH SẢNH CƯỚI HIỆN CÓ");
		try {
			String selectQuery = "select * from sanhcuoi";
			PreparedStatement preStm = conn.prepareStatement(selectQuery);
			ResultSet rs = preStm.executeQuery();
			while (rs.next()) {
				int maSanhCuoi = rs.getInt("MaSanhCuoi");
				String tenSanh = rs.getString("TenSanh");
				String viTri = rs.getString("ViTri");
				int sucChua = rs.getInt("SucChua");
				double giaThueCb = rs.getDouble("GiaThueCoBan");
				SanhCuoi sanhCuoi = new SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);
				sanhCuoi.showInfo();
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}

		conn.close();
	}

// Cập nhật sảnh
	public void capNhatSanhCuoi(Scanner scanner) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		System.out.print(">Nhập mã sảnh muốn cập nhật: S");
		int maSanhUpdate;
		maSanhUpdate = scanner.nextInt();

		scanner.nextLine();
		System.out.print(">Nhập tên sảnh mới: ");
		String tenSanh;
		tenSanh = scanner.nextLine();

		System.out.println("=>Chọn vị trí mới");
		System.out.println("1.Tầng 1");
		System.out.println("2.Tầng 2");
		System.out.println("3.Tầng 3");
		System.out.println("4.Tầng 4");
		System.out.print(">Bạn chọn: ");

		String viTri = "null";
		int chonViTri = scanner.nextInt();
		switch (chonViTri) {
		case 1:
			viTri = "Tầng 1";
			break;
		case 2:
			viTri = "Tầng 2";
			break;
		case 3:
			viTri = "Tầng 3";
			break;
		case 4:
			viTri = "Tầng 4";
			break;
		default:
			break;
		}

		System.out.print(">Nhập sức chứa mới: ");
		int sucChua;
		sucChua = scanner.nextInt();

		System.out.print(">Nhập giá thuê cơ bản mới: ");
		double giaThueCb;
		giaThueCb = scanner.nextDouble();

		try {
			String updateQuery = "update sanhcuoi\r\n" + "set TenSanh = ?,\r\n" + "ViTri = ?,\r\n" + "SucChua = ?,\r\n"
					+ "GiaThueCoBan = ?\r\n" + "where MaSanhCuoi = ?;";

			PreparedStatement preStm = conn.prepareStatement(updateQuery);
			preStm.setString(1, tenSanh);
			preStm.setString(2, viTri);
			preStm.setInt(3, sucChua);
			preStm.setDouble(4, giaThueCb);
			preStm.setInt(5, maSanhUpdate);
			preStm.executeUpdate();
			System.out.println("Cập nhật sảnh thành công");				

		} catch (SQLException ex) {
			System.out.println("Cập nhật sảnh thất bại");
			System.err.println(ex.getMessage());
		}

		conn.close();
	}

// xóa sảnh cưới
	public void xoaSanhCuoi(Scanner scanner) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
		
		System.out.print(">Nhập mã sảnh cần xóa: S");
		int maSanhDelete = scanner.nextInt();
		
		try {			
			String selectQuery = "select * from thongtintiec where SanhCuoi_ID = ?";
			PreparedStatement preStm = conn.prepareStatement(selectQuery);
			preStm.setInt(1, maSanhDelete);
			ResultSet rs = preStm.executeQuery();
			int maMenu = 0;
			int maTiec = 0;
			int maKhachHang = 0;			
			while(rs.next()) {
				maTiec = rs.getInt("MaTiec");
				maMenu = rs.getInt("Menu_ID");
				maKhachHang = rs.getInt("KhachHang_Id");
			}
			
			String deleteQuery2 = "delete from menu_monan where Menu_ID = ? ";
			PreparedStatement preStm2 = conn.prepareStatement(deleteQuery2);
			preStm2.setInt(1, maMenu);
			preStm2.executeUpdate();
			
			String deleteQuery3 = "delete from tiec_dichvu where Tiec_ID = ? ";
			PreparedStatement preStm3 = conn.prepareStatement(deleteQuery3);
			preStm3.setInt(1, maTiec);
			preStm3.executeUpdate();
			
			String deleteQuery4 = "delete from thongtintiec where SanhCuoi_ID = ? ";
			PreparedStatement preStm4 = conn.prepareStatement(deleteQuery4);
			preStm4.setInt(1, maSanhDelete);
			preStm4.executeUpdate();
			
			String deleteQuery5 = "delete from menu where MaMenu = ? ";
			PreparedStatement preStm5 = conn.prepareStatement(deleteQuery5);
			preStm5.setInt(1, maMenu);
			preStm5.executeUpdate();					
		
			String deleteQuery6 = "delete from khachhang where MaKhachHang = ? ";
			PreparedStatement preStm6 = conn.prepareStatement(deleteQuery6);
			preStm6.setInt(1, maKhachHang);
			preStm6.executeUpdate();				
		
			String deleteQuery7 = "delete from sanhcuoi where MaSanhCuoi = ? ";
			PreparedStatement preStm7 = conn.prepareStatement(deleteQuery7);			
			preStm7.setInt(1, maSanhDelete);
			preStm7.executeUpdate();									
			
			System.out.println("Xóa sảnh cưới thành công");
		} catch (SQLException ex) {
			System.out.println("Xóa sảnh cưới thất bại");
			System.err.println(ex.getMessage());
		}
		conn.close();
	}

// tra cứu sảnh cưới theo tên
	public List<SanhCuoi> traCuuSanhCuoiTheoTen(String tenSanhTim) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
		List<SanhCuoi> kq = new ArrayList<SanhCuoi>();

		try {
			String selectQuery = "select * from sanhcuoi where TenSanh = ?";
			PreparedStatement preStm = conn.prepareStatement(selectQuery);
			preStm.setString(1, tenSanhTim);
			ResultSet rs = preStm.executeQuery();
			
			while (rs.next()) {
				int maSanhCuoi = rs.getInt("MaSanhCuoi");
				String tenSanh = rs.getString("TenSanh");
				String viTri = rs.getString("ViTri");
				int sucChua = rs.getInt("SucChua");
				double giaThueCb = rs.getDouble("GiaThueCoBan");
				SanhCuoi sanhCuoi = new SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);
				kq.add(sanhCuoi);				
			}		
			
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		conn.close();
		return kq;
	}

// tra cứu sảnh cưới theo sức chứa
	public List<SanhCuoi> traCuuSanhCuoiTheoSucChua(int sucChuaTim) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

		List<SanhCuoi> kq = new ArrayList<SanhCuoi>();

		try {
			String selectQuery = "select * from sanhcuoi where SucChua = ?";
			PreparedStatement preStm = conn.prepareStatement(selectQuery);
			preStm.setInt(1, sucChuaTim);
			ResultSet rs = preStm.executeQuery();
			while (rs.next()) {
				int maSanhCuoi = rs.getInt("MaSanhCuoi");
				String tenSanh = rs.getString("TenSanh");
				String viTri = rs.getString("ViTri");
				int sucChua = rs.getInt("SucChua");
				double giaThueCb = rs.getDouble("GiaThueCoBan");
				SanhCuoi sanhCuoi = new SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);
				kq.add(sanhCuoi);
			}
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		conn.close();
		return kq;
	}

// tra cứu sảnh cưới theo vị trí
	public List<SanhCuoi> traCuuSanhCuoiTheoViTri(String viTriSanh) throws SQLException {
		Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);
		List<SanhCuoi> kq = new ArrayList<SanhCuoi>();

		try {
			String selectQuery = "select * from sanhcuoi where ViTri = ?";
			PreparedStatement preStm = conn.prepareStatement(selectQuery);
			preStm.setString(1, viTriSanh);
			ResultSet rs = preStm.executeQuery();
			
			while (rs.next()) {
				int maSanhCuoi = rs.getInt("MaSanhCuoi");
				String tenSanh = rs.getString("TenSanh");
				String viTri = rs.getString("ViTri");
				int sucChua = rs.getInt("SucChua");
				double giaThueCb = rs.getDouble("GiaThueCoBan");
				SanhCuoi sanhCuoi = new SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);
				kq.add(sanhCuoi);
			}

		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		conn.close();
		return kq;
	}

// getter and setter
	public List<SanhCuoi> getDanhSachSC() {
		return danhSachSC;
	}

	public void setDanhSachSC(List<SanhCuoi> danhSachSC) {
		this.danhSachSC = danhSachSC;
	}

// tạo kết nối đến csdl
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
