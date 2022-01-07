package quanly;

public class ThoiDiem {
	private int maThoiDiem;
	private String thoiDiemThue;
	private double heSoChoThue;
	
	
	public ThoiDiem(int ma, String ten, double heSo) {
		this.maThoiDiem = ma;
		this.thoiDiemThue = ten;
		this.heSoChoThue = heSo;
	}
	
	public void showInfo() {
		System.out.printf("Mã thời điểm: %d --\t Thời điểm thuê: %s --\t Hệ số cho thuê: %s\n",
				this.maThoiDiem, this.thoiDiemThue, this.heSoChoThue);
	}		
	
//getter and setter
	public int getMaThoiDiem() {
		return maThoiDiem;
	}
	public void setMaThoiDiem(int maThoiDiem) {
		this.maThoiDiem = maThoiDiem;
	}
	public String getThoiDiemThue() {
		return thoiDiemThue;
	}

	public void setThoiDiemThue(String thoiDiemThue) {
		this.thoiDiemThue = thoiDiemThue;
	}

	public double getHeSoChoThue() {
		return heSoChoThue;
	}
	public void setHeSoChoThue(int heSoChoThue) {
		this.heSoChoThue = heSoChoThue;
	}		
}
