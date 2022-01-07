package quanly;

public class ThueCaSi extends DichVu {
	private String tenCaSi;
	private int soLuongBaiHat;

	public ThueCaSi(int ma, String ten, double gia, String tenCaSi, int soLuongBai) {
		super(ma, ten, gia);
		this.soLuongBaiHat = soLuongBai;
		this.tenCaSi = tenCaSi;
	}

	@Override
	public void showInfo() {
		super.showInfo();
		System.out.printf("Tên ca sĩ: %s\n", this.tenCaSi);
		System.out.printf("Số lượng bài hát: %d\n", this.soLuongBaiHat);
	}	

	public String getTenCaSi() {
		return tenCaSi;
	}

	public void setTenCaSi(String tenCaSi) {
		this.tenCaSi = tenCaSi;
	}

	public int getSoLuongBaiHat() {
		return soLuongBaiHat;
	}

	public void setSoLuongBaiHat(int soLuongBaiHat) {
		this.soLuongBaiHat = soLuongBaiHat;
	}
}
