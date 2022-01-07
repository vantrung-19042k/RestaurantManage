package quanly;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private String tenMenu;
	private List<MonAn> dsMonAn = new ArrayList<MonAn>();		
	
	public Menu(String ten) {
		this.tenMenu = ten;
	}
	
	public double tinhDonGiaMenu() {
		double donGia = 0;
		for(MonAn monAn: this.dsMonAn)
			donGia += monAn.getGiaBan();
		return donGia;
	}
	
	public void themMonAn(MonAn monAn) {
		this.dsMonAn.add(monAn);
	}
	
	public void showInfo() {
		System.out.printf("Tên menu: %s\n", this.tenMenu);
		System.out.println("=====Danh sách món ăn của menu=====");
		this.dsMonAn.forEach(monAn -> monAn.showInfo());
		System.out.printf("-Đơn giá menu: %,.2f\n", this.tinhDonGiaMenu());
	}
	
//getter and setter
	public String getTenMenu() {
		return tenMenu;
	}
	public void setTenMenu(String tenMenu) {
		this.tenMenu = tenMenu;
	}
	public List<MonAn> getDsMonAn() {
		return dsMonAn;
	}
	public void setDsMonAn(List<MonAn> dsMonAn) {
		this.dsMonAn = dsMonAn;
	}
}
