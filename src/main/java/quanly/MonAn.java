package quanly;

public abstract class MonAn {
	protected int maMon;
	protected String tenMon;
	protected double giaBan;

	public MonAn(int maMon, String tenMon, double giaBan) {
		this.maMon = maMon;
		this.tenMon = tenMon;
		this.giaBan = giaBan;
	}

	public void showInfo() {
		System.out.printf("Tên món: %-21s --- ", this.tenMon);
		System.out.printf("Giá bán: %,.2f --- ", this.giaBan);
	}

	public int getMaMon() {
		return maMon;
	}

	public void setMaMon(int maMon) {
		this.maMon = maMon;
	}

	public String getTenMon() {
		return tenMon;
	}

	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}
}
