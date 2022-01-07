package quanly;

import java.util.Scanner;

public class KhachHang {
	private int maKhachHang;
	private String tenKhachHang;
	private String sdtKhachHang;
	
	public void nhapThongTin(Scanner scanner) {
		System.out.print("Nhập tên khách hàng: ");
		tenKhachHang = scanner.nextLine();
		System.out.print("Nhập SĐT khách hàng: ");
		sdtKhachHang = scanner.nextLine();
	}
	
	public int getMaKhachHang() {
		return maKhachHang;
	}
	public void setMaKhachHang(int maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getSdtKhachHang() {
		return sdtKhachHang;
	}
	public void setSdtKhachHang(String sdtKhachHang) {
		this.sdtKhachHang = sdtKhachHang;
	}
	
	
}
