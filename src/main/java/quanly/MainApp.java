package quanly;

import java.sql.SQLException;
import java.util.Scanner;

public class MainApp {
	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);
		QuanLySanhCuoi quanLySC = new QuanLySanhCuoi();
		QuanLyMonAn quanLyMA = new QuanLyMonAn();
		QuanLyDichVu quanLyDV = new QuanLyDichVu();
		QuanLyThoiDiem quanLyTD = new QuanLyThoiDiem();
		DatTiec datTiec = new DatTiec();

		boolean continued = true;
		do {
			System.out.println("==========NHÀ HÀNG TIỆC CƯỚI WEDDING RESTAURANT==========");
			System.out.println("1.QUẢN LÝ THÔNG TIN SẢNH CƯỚI");
			System.out.println("2.QUẢN LÝ THÔNG TIN DỊCH VỤ");
			System.out.println("3.QUẢN LÝ THÔNG TIN MÓN ĂN");
			System.out.println("4.QUẢN LÝ THÔNG TIN THỜI ĐIỂM THUÊ");
			System.out.println("5.ĐẶT TIỆC");
			System.out.println("6.BÁO CÁO DOANH THU");

			int select = 0;
			do {
				System.out.printf("Chọn chức năng cần thực hiện: ");
				select = scanner.nextInt(); // Integer.parseInt(scanner.nextLine());
				if (select < 1 || select > 6)
					System.out.println("Chọn chức năng sai. Vui lòng chọn lại");
			} while (select < 1 || select > 6);

			switch (select) {
			case 1:
				System.out.println("==========QUẢN LÝ THÔNG TIN SẢNH CƯỚI==========");
				System.out.println("1.THÊM SẢNH");
				System.out.println("2.CẬP NHẬT SẢNH");
				System.out.println("3.XÓA SẢNH");
				System.out.println("4.HIỂN THỊ DANH SÁCH SẢNH CƯỚI HIỆN CÓ");
				System.out.println("5.TRA CỨU SẢNH THEO TÊN");
				System.out.println("6.TRA CỨU SẢNH THEO SỨC CHỨA");
				System.out.println("7.TRA CỨU SẢNH THEO VỊ TRÍ");

				int function1 = scanner.nextInt();
				switch (function1) {
				case 1:
					quanLySC.hienThiDanhSachSanhCuoi();
					scanner.nextLine();
					quanLySC.themSanhCuoi(scanner);
					break;
				case 2:
					quanLySC.hienThiDanhSachSanhCuoi();
					quanLySC.capNhatSanhCuoi(scanner);
					break;
				case 3:
					quanLySC.hienThiDanhSachSanhCuoi();
					quanLySC.xoaSanhCuoi(scanner);
					break;
				case 4:
					quanLySC.hienThiDanhSachSanhCuoi();
					break;
				case 5:
					scanner.nextLine();
					quanLySC.hienThiDanhSachSanhCuoi();
					System.out.print(">Nhập tên sảnh cần tìm kiếm: ");
					String tenSanhCanTim = scanner.nextLine();
					if (quanLySC.traCuuSanhCuoiTheoTen(tenSanhCanTim).isEmpty() == true)
						System.out.println("=>KHÔNG TÌM THẤY SẢNH");
					else {
						System.out.println("=>THÔNG TIN SẢNH TÌM THẤY");
						quanLySC.traCuuSanhCuoiTheoTen(tenSanhCanTim).forEach(sanhCuoi -> sanhCuoi.showInfo());
					}
					break;
				case 6:
					scanner.nextLine();
					quanLySC.hienThiDanhSachSanhCuoi();
					System.out.print(">Nhập sức chứa để tim kiếm sảnh: ");
					int sucChuaCanTim = scanner.nextInt();
					if (quanLySC.traCuuSanhCuoiTheoSucChua(sucChuaCanTim).isEmpty() == true)
						System.out.println("=>KHÔNG TÌM THẤY SẢNH");
					else {
						System.out.println("=>THÔNG TIN SẢNH TÌM THẤY");
						quanLySC.traCuuSanhCuoiTheoSucChua(sucChuaCanTim).forEach(sanhCuoi -> sanhCuoi.showInfo());
					}
					break;
				case 7:
					scanner.nextLine();	
					quanLySC.hienThiDanhSachSanhCuoi();
					System.out.println("=>DANH SÁCH VỊ TRÍ SẢNH HIỆN TẠI CÓ");
					System.out.println("1.Tầng 1");
					System.out.println("2.Tầng 2");
					System.out.println("3.Tầng 3");
					System.out.println("4.Tầng 4");
					System.out.print("=>CHỌN VỊ TRÍ SẢNH CƯỚI CẦN TÌM KIẾM: ");
					String viTriSanh = null;
					int chonViTri = scanner.nextInt();
					switch (chonViTri) {
					case 1:
						viTriSanh = "Tầng 1";
						break;
					case 2:
						viTriSanh = "Tầng 2";
						break;
					case 3:
						viTriSanh = "Tầng 3";
						break;
					case 4:
						viTriSanh = "Tầng 4";
						break;
					default:
						break;
					}
					if (quanLySC.traCuuSanhCuoiTheoViTri(viTriSanh).isEmpty() == true)
						System.out.println("=>KHÔNG TÌM THẤY SẢNH");
					else {
						System.out.println("=>THÔNG TIN SẢNH TÌM THẤY");
						quanLySC.traCuuSanhCuoiTheoViTri(viTriSanh).forEach(sanhCuoi -> sanhCuoi.showInfo());
					}
				default:
					break;

				}

				break;

			case 2:
				System.out.println("==========QUẢN LÝ THÔNG TIN DỊCH VỤ==========");
				System.out.println("1.THÊM DỊCH VỤ");
				System.out.println("2.CẬP NHẬT DỊCH VỤ");
				System.out.println("3.XÓA DỊCH VỤ");
				System.out.println("4.HIỂN THỊ DANH SÁCH DỊCH VỤ HIỆN CÓ");
				System.out.println("5.TRA CỨU DỊCH VỤ THEO TÊN");

				int function2 = 0;
				function2 = scanner.nextInt();
				switch (function2) {
				case 1:
					scanner.nextLine();
					quanLyDV.themDichVu(scanner);
					break;
				case 2:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 3:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 4:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 5:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				}
				break;

			case 3:
				System.out.println("==========QUẢN LÝ THÔNG TIN MÓN ĂN==========");
				System.out.println("1.THÊM MÓN ĂN");
				System.out.println("2.CẬP NHẬT THÔNG TIN MÓN ĂN");
				System.out.println("3.XÓA MÓN ĂN");
				System.out.println("4.HIỂN THỊ DANH SÁCH MÓN ĂN HIỆN CÓ");
				System.out.println("5.TRA CỨU MÓN ĂN THEO TÊN");

				int function3 = 0;
				function3 = scanner.nextInt();
				switch (function3) {
				case 1:
					quanLyMA.themMonAn(scanner);
					break;
				case 2:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 3:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 4:
					System.out.println("=>DANH SÁCH MÓN ĂN NHÀ HÀNG HIỆN CÓ");
					quanLyMA.hienThiDanhSachMonAn(scanner);
					break;
				case 5:
					scanner.nextLine();
					System.out.print("=>Nhập tên món ăn cần tìm: ");
					String tenMonCanTim = scanner.nextLine();
					if(quanLyMA.traCuuMonAnTheoTen(tenMonCanTim).isEmpty() == true)
						System.out.println("=>Không tìm thấy món ăn trong danh sách");
					else
					{
						System.out.println("=>Thông tin món ăn tìm thấy");
						quanLyMA.traCuuMonAnTheoTen(tenMonCanTim).forEach(monAn -> monAn.showInfo());
					}
					break;
				}
				break;

			case 4:
				System.out.println("==========QUẢN LÝ THÔNG TIN THỜI ĐIỂM CHO THUÊ==========");
				System.out.println("1.THÊM THỜI ĐIỂM");
				System.out.println("2.CẬP NHẬT THÔNG TIN THỜI ĐIỂM");
				System.out.println("3.XÓA THỜI ĐIỂM");
				System.out.println("4.HIỂN THỊ DANH SÁCH THỜI ĐIỂM HIỆN CÓ");
				System.out.println("5.TRA CỨU THỜI ĐIỂM THEO TÊN");

				int function4 = 0;
				function4 = scanner.nextInt();
				switch (function4) {
				case 1:
					quanLyTD.themThoiDiem(scanner);
					break;
				case 2:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 3:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				case 4:
					System.out.println("=>DANH SÁCH THỜI ĐIỂM HIỆN CÓ");
					quanLyTD.hienThiThoiDiem(scanner);
					break;
				case 5:
					System.out.println("Chức năng đang được cập nhật!");
					break;
				}
				break;

			case 5:
				System.out.println("==========ĐẶT TIỆC==========");
				datTiec.choThueSanh(scanner);
				break;

			case 6:
				System.out.println("==========BÁO CÁO DOANH THU==========");
				System.out.println("1.BÁO CÁO DOANH THU THEO THÁNG");
				System.out.println("2.BAO CÁO DOANH THU THEO NĂM");
				int function6 = 0;
				function6 = scanner.nextInt();
				switch (function6) {
				case 1:
					datTiec.baoCaoDoanhThuTheoThang(scanner);
					break;
				case 2:
					datTiec.baoCaoDoanhThuTheoNam(scanner);
					break;
				}
				break;
			default:
				break;
			}

			scanner.nextLine();
			System.out.print("=>Trở về menu chính (c,k): ");
			String tiepTuc = scanner.nextLine();
			if (tiepTuc.toLowerCase().contains("c"))
				continued = true;
			else {
				continued = false;
				System.out.println("==========CHÀO TẠM BIỆT. HẸN GẶP LẠI==========");
			}
		} while (continued == true);
	}
}
