package quanly;

public class SanhCuoi {
	private int maSanh;
	private String tenSanh;
	private int sucChua;
	private String viTri;
	private double giaThueCoBan;

	public SanhCuoi(int ma, String ten, int sucChua, String viTri, double giaThueCb) {
		this.maSanh = ma;
		this.tenSanh = ten;
		this.sucChua = sucChua;
		this.viTri = viTri;
		this.giaThueCoBan = giaThueCb;
	}

	public void showInfo() {
		System.out.printf("Mã sảnh: S%03d --- Tên sảnh: %-10s --- Sức chứa: %-3d --- Vị trí: %s --- Giá thuê: %,.2f\n",
				this.maSanh, this.tenSanh, this.sucChua, this.viTri, this.giaThueCoBan);
	}

//	getter and setter
	public int getMaSanh() {
		return maSanh;
	}

	public void setMaSanh(int maSanh) {
		this.maSanh = maSanh;
	}

	public String getTenSanh() {
		return tenSanh;
	}

	public void setTenSanh(String tenSanh) {
		this.tenSanh = tenSanh;
	}

	public int getSucChua() {
		return sucChua;
	}

	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	public String getViTri() {
		return viTri;
	}

	public void setViTri(String viTri) {
		this.viTri = viTri;
	}

	public double getGiaThueCoBan() {
		return giaThueCoBan;
	}

	public void setGiaThueCoBan(double giaThueCoBan) {
		this.giaThueCoBan = giaThueCoBan;
	}
}
