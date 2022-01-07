package quanly;

public abstract class DichVu {
	protected int maDichVu;
	protected String tenDichVu;
	protected double giaDichVu;
	
	public DichVu(int ma, String ten, double gia) {
		this.maDichVu = ma;
		this.tenDichVu = ten;
		this.giaDichVu = gia;		
	}
	
	public void showInfo() {
		System.out.printf("Mã dịch vụ: DV-%d\n", this.maDichVu);
		System.out.printf("Tên dịch vụ: %s\n", this.tenDichVu);
		System.out.printf("Giá dịch vụ: %,.2f\n", this.giaDichVu);
	}

//getter and setter
	public int getMaDichVu() {
		return maDichVu;
	}
	public void setMaDichVu(int maDichVu) {
		this.maDichVu = maDichVu;
	}
	public String getTenDichVu() {
		return tenDichVu;
	}
	public void setTenDichVu(String tenDichVu) {
		this.tenDichVu = tenDichVu;
	}
	public double getGiaDichVu() {
		return giaDichVu;
	}
	public void setGiaDichVu(double giaDichVu) {
		this.giaDichVu = giaDichVu;
	}			
}
