package quanly;

public class ThucUong extends MonAn{
	private String hangSanXuat;
	
	public ThucUong(int maMon, String tenMon, double giaBan, String hangSx) {
		super(maMon, tenMon, giaBan);
		this.hangSanXuat = hangSx;
	}
	
	@Override
	public void showInfo() {	
		System.out.printf("Mã món: TU-%03d  ---  ", this.maMon);
		super.showInfo();		
		System.out.printf("Hãng sản xuất: %s\n", this.hangSanXuat);
	}

	public String getHangSanXuat() {
		return hangSanXuat;
	}

	public void setHangSanXuat(String hangSanXuat) {
		this.hangSanXuat = hangSanXuat;
	}	
}
