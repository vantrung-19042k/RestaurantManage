package quanly;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class DatTiec {

    private static final String DB_URL = "jdbc:mysql://localhost/quanlynhahang";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "123456789";

    private int maTiec;
    private String tenBuoiTiec;
    private KhachHang khachHang;
    private SanhCuoi sanhThue;
    private String ngayThue;
    private ThoiDiem thoiDiemThue;
    private double donGiaThueSanh;
    private Menu menu;
    private double donGiaThucDon;
    private List<DichVu> dsDichVu = new ArrayList<DichVu>();
    private double donGiaCacDichVu;

    public DatTiec() {

    }

// nhap thong tin khach hang
    public void nhapThongTinKhachHang(Scanner scanner) throws SQLException {
        khachHang = new KhachHang();
        khachHang.nhapThongTin(scanner);

        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        try {
            String selectQuery = "select max(MaKhachHang) from khachhang";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(selectQuery);
            while (rs.next()) {
                int maKhachHang = rs.getInt("max(MaKhachHang)") + 1;
                khachHang.setMaKhachHang(maKhachHang);
            }
            String insertQuery = "insert into khachhang(MaKhachHang, TenKhachHang, SDT)" + "values(?, ?, ?)";
            PreparedStatement preStm = conn.prepareStatement(insertQuery);
            preStm.setInt(1, khachHang.getMaKhachHang());
            preStm.setString(2, khachHang.getTenKhachHang());
            preStm.setString(3, khachHang.getSdtKhachHang());
            int ketQua = preStm.executeUpdate();
            if (ketQua == 1) {
                System.out.println("=>Hoàn tất nhập thông tin khách hàng");
            }
        } catch (SQLException ex) {
            System.out.println("=>Nhập thông tin khách hàng thất bại");
            System.err.println(ex.getMessage());
        }

        System.out.printf("-Tên khách hàng: %s\n", this.khachHang.getTenKhachHang());
        System.out.printf("-SĐT khách hàng: %s\n", this.khachHang.getSdtKhachHang());

        conn.close();
    }

// thue sanh
    public void thueSanh(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>Danh sách sảnh cưới hiện có");
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

        System.out.print("=>Nhập mã sảnh bạn muốn chọn: S");
        int maSanhMuonThue = scanner.nextInt();
        scanner.nextLine();

        try {
            String selectQuery = "select * from sanhcuoi where MaSanhCuoi = ?";
            PreparedStatement preStm = conn.prepareStatement(selectQuery);
            preStm.setInt(1, maSanhMuonThue);
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                int maSanhCuoi = rs.getInt("MaSanhCuoi");
                String tenSanh = rs.getString("TenSanh");
                String viTri = rs.getString("ViTri");
                int sucChua = rs.getInt("SucChua");
                double giaThueCb = rs.getDouble("GiaThueCoBan");
                sanhThue = new SanhCuoi(maSanhCuoi, tenSanh, sucChua, viTri, giaThueCb);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println("=>Sảnh đã chọn cho buổi tiệc");
        sanhThue.showInfo();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("=>Nhập thông tin ngày thuê sảnh");
        System.out.println("-Nhập ngày: ");
        int ngay = scanner.nextInt();
        System.out.println("-Nhập tháng:");
        int thang = scanner.nextInt();
        System.out.println("-Nhập năm: ");
        int nam = scanner.nextInt();

        GregorianCalendar ngayDatTiec = new GregorianCalendar(nam, (thang - 1), ngay);
        int dayOfWeek = ngayDatTiec.get(Calendar.DAY_OF_WEEK);
        System.out.printf("=>Ngày buổi tiệc sẽ diễn ra: %s\n", f.format(ngayDatTiec.getTime()));
        setNgayThue(f.format(ngayDatTiec.getTime()));

        String thoiDiem = scanner.nextLine();
        System.out.println("=>CHỌN THỜI ĐIỂM THUÊ");
        if (dayOfWeek > 1 && dayOfWeek < 7) {
            thoiDiem = "ngày thường";
        } else {
            thoiDiem = "ngày cuối tuần";
        }

        try {
            String selectQuery = "select * from thoidiemthue where ThoiDiemThue like ? ";
            PreparedStatement preStm = conn.prepareStatement(selectQuery);
            preStm.setString(1, "%" + thoiDiem + "%");
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                int maThoiDiem = rs.getInt("MaThoiDiem");
                String thoiDiemChoThue = rs.getString("ThoiDiemThue");
                double heSoThue = rs.getDouble("HeSoChoThue");
                System.out.printf("Mã thời điểm: %d --- ", maThoiDiem);
                System.out.printf("Thời điểm thuê: %-16s --- ", thoiDiemChoThue);
                System.out.printf("Hệ số cho thuê: %s\n", heSoThue);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.print("=>Nhập mã thời điểm để chọn thời điểm thuê: ");
        int chonThoiDiem = scanner.nextInt();

        try {
            String selectQuery = "select * from thoidiemthue where MaThoiDiem = ? ";
            PreparedStatement preStm = conn.prepareStatement(selectQuery);
            preStm.setInt(1, chonThoiDiem);
            ResultSet rs = preStm.executeQuery();
            while (rs.next()) {
                int maThoiDiem = rs.getInt("MaThoiDiem");
                String thoiDiemChoThue = rs.getString("ThoiDiemThue");
                double heSoThue = rs.getDouble("HeSoChoThue");
                thoiDiemThue = new ThoiDiem(maThoiDiem, thoiDiemChoThue, heSoThue);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println("=>Thời điểm thuê đã chọn");
        thoiDiemThue.showInfo();
        System.out.printf("Đơn giá thuê sảnh: %,.2f\n", tinhDonGiaThueSanh());

        conn.close();
    }

// tinh don gia thue sanh
    public double tinhDonGiaThueSanh() {
        double donGiaThue = thoiDiemThue.getHeSoChoThue() * sanhThue.getGiaThueCoBan();
        return donGiaThue;
    }

// tạo menu
    public void taoMenu(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        scanner.nextLine();
        System.out.print("Nhập tên menu: ");
        String tenMenu = scanner.nextLine();
        Menu menu = new Menu(tenMenu);
        this.setMenu(menu);
        int maMenu = 0;

        try {
            String selectQuery = "select max(MaMenu) from menu";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(selectQuery);

            while (rs.next()) {
                maMenu = rs.getInt("max(MaMenu)") + 1;
            }
            String insertQuery = "insert into menu(MaMenu, TenMenu)" + "values(?, ?)";
            PreparedStatement preStm = conn.prepareStatement(insertQuery);
            preStm.setInt(1, maMenu);
            preStm.setString(2, menu.getTenMenu());

            int ketQua = preStm.executeUpdate();
            if (ketQua == 1) {
                System.out.println("=>Hoàn tất nhập thông tin Menu");
            } else {
                System.out.println("Nhập menu lỗi");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println("=>Danh sách món ăn nhà hàng hiện có");
        try {
            String selectQuery = "select * from monan";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(selectQuery);
            while (rs.next()) {
                int maMonAn = rs.getInt("MaMonAn");
                String tenMon = rs.getString("TenMonAn");
                double giaBan = rs.getDouble("GiaBan");
                String anChay = rs.getString("AnChay");
                String hangSx = rs.getString("HangSanXuat");
                if (anChay == null) {
                    MonAn thucUong = new ThucUong(maMonAn, tenMon, giaBan, hangSx);
                    thucUong.showInfo();
                } else {
                    MonAn thucAn = new ThucAn(maMonAn, tenMon, giaBan, anChay);
                    thucAn.showInfo();
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        try {
            boolean continued = true;
            do {
                System.out.print("=>Nhập mã món ăn cần thêm: ");
                int themMonAn = scanner.nextInt();
                scanner.nextLine();

                String selectQuery2 = "select * from monan";
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery(selectQuery2);
                while (rs2.next()) {
                    int maMonAn = rs2.getInt("MaMonAn");
                    String tenMon = rs2.getString("TenMonAn");
                    double giaBan = rs2.getDouble("GiaBan");
                    String anChay = rs2.getString("AnChay");
                    String hangSx = rs2.getString("HangSanXuat");
                    if (anChay == null && maMonAn == themMonAn) {
                        MonAn thucUong = new ThucUong(maMonAn, tenMon, giaBan, hangSx);
                        menu.themMonAn(thucUong);
                        String insertQuery = "insert into menu_monan(Menu_ID, MonAn_ID)" + "values(?, ?)";
                        PreparedStatement preStm = conn.prepareStatement(insertQuery);
                        preStm.setInt(1, maMenu);
                        preStm.setInt(2, maMonAn);
                        int ketQua = preStm.executeUpdate();
                        if (ketQua == 1) {
                            System.out.println("=>Thêm thành công");
                        } else {
                            System.out.println("=>Thêm thất bại");
                        }

                    } else if (anChay != null && maMonAn == themMonAn) {
                        MonAn thucAn = new ThucAn(maMonAn, tenMon, giaBan, anChay);
                        menu.themMonAn(thucAn);
                        String insertQuery = "insert into menu_monan(Menu_ID, MonAn_ID)" + "values(?, ?)";
                        PreparedStatement preStm = conn.prepareStatement(insertQuery);
                        preStm.setInt(1, maMenu);
                        preStm.setInt(2, maMonAn);
                        int ketQua = preStm.executeUpdate();
                        if (ketQua == 1) {
                            System.out.println("=>Thêm thành công");
                        } else {
                            System.out.println("=>Thêm thất bại");
                        }
                    }
                }

                System.out.println("=>Tiếp tục thêm món ăn vào menu (c,k): ");
                String tiepTuc = scanner.nextLine();
                if (tiepTuc.toLowerCase().equals("c")) {
                    continued = true;
                } else {
                    continued = false;
                }
            } while (continued == true);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        this.donGiaThucDon = menu.tinhDonGiaMenu();
        menu.showInfo();
        conn.close();
    }

// them dich vu
    public void themDichVu(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>Danh sách dịch vụ nhà hàng hiện có");
        try {
            String selectQuery = "select * from dichvu";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(selectQuery);
            while (rs.next()) {
                int maDichVu = rs.getInt("MaDichVu");
                String tenDichVu = rs.getString("TenDichVu");
                double giaDichVu = rs.getDouble("GiaDichVu");
                System.out.printf("Mã dịch vụ: DV-%03d ---", maDichVu);
                System.out.printf("Tên dịch vụ: %s ---", tenDichVu);
                System.out.printf("Giá dịch vụ: %.2f\n", giaDichVu);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        try {
            boolean continued = true;
            do {
                System.out.print("=>Nhập tên dịch vụ cần thêm: ");
                String tenDichVuThem = scanner.nextLine().toLowerCase();
                int thoiGianThue = 0;
                String tenCaSi = null;
                int soBaiHat = 0;
                if (tenDichVuThem.contains("karaoke")) {
                    System.out.print("=>Nhập số giờ muốn thuê: ");
                    thoiGianThue = scanner.nextInt();
                    scanner.nextLine();
                } else if (tenDichVuThem.contains("thuê ca sĩ")) {
                    System.out.print("=>Nhập tên ca sĩ muốn thuê: ");
                    tenCaSi = scanner.nextLine();
                    System.out.print("=>Nhập số lượng bài hát: ");
                    soBaiHat = scanner.nextInt();
                    scanner.nextLine();
                }

                String selectQuery2 = "select * from dichvu";
                Statement stm2 = conn.createStatement();
                ResultSet rs2 = stm2.executeQuery(selectQuery2);

                while (rs2.next()) {
                    int maDichVu = rs2.getInt("MaDichVu");
                    String tenDichVu = rs2.getString("TenDichVu");
                    double giaDichVu = rs2.getDouble("GiaDichVu");

                    if (tenDichVuThem.contains("karaoke") && tenDichVu.toLowerCase().contains(tenDichVuThem)) {
                        DichVu karaoke = new Karaoke(maDichVu, tenDichVu, giaDichVu, thoiGianThue);
                        this.dsDichVu.add(karaoke);
                    } else if (tenDichVuThem.contains("thuê ca sĩ")
                            && tenDichVu.toLowerCase().contains(tenDichVuThem)) {
                        DichVu thueCaSi = new ThueCaSi(maDichVu, tenDichVu, giaDichVu, tenCaSi, soBaiHat);
                        this.dsDichVu.add(thueCaSi);
                    }
                }

                System.out.println("=>Bạn muốn thêm dịch vụ nào nữa không(c,k): ");
                String tiepTuc = scanner.nextLine();
                if (tiepTuc.toLowerCase().contains("c")) {
                    continued = true;
                } else {
                    System.out.println("=>Kết thúc thêm dịch vụ");
                    continued = false;
                }
            } while (continued == true);

            System.out.println("=>Danh sách dịch vụ đã chọn");
            this.dsDichVu.forEach(dichVu -> dichVu.showInfo());
            System.out.printf("=>Tổng tiền các dịch vụ: %,.2f\n", this.tinhTongTienDichVu());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        conn.close();
    }

    public double tinhTongTienDichVu() {
        double tongTienDichVu = 0;
        for (DichVu dv : this.dsDichVu) {
            tongTienDichVu += dv.giaDichVu;
        }
        return tongTienDichVu;
    }

//xuat hoat don
    public void xuatHoaDon() {
        System.out.println("==========>HÓA ĐƠN THANH TOÁN<==========");

        System.out.println("=>Thông tin khách hàng");
        System.out.printf("-Tên khách hàng: %s\n", this.khachHang.getTenKhachHang());
        System.out.printf("-SĐT khách hàng: %s\n", this.khachHang.getSdtKhachHang());
        System.out.println("=>Thông tin sảnh");

        //this.sanhThue.showInfo();
        System.out.printf("-Mã sảnh: %d --- Tên sảnh: %s --- Sức chứa: %d (bàn) --- Vị trí: %s\n",
                this.sanhThue.getMaSanh(), this.sanhThue.getTenSanh(), this.sanhThue.getSucChua(),
                this.sanhThue.getViTri());
        //this.thoiDiemThue.showInfo();
        System.out.printf("-Thời điểm thuê: %s\n", this.thoiDiemThue.getThoiDiemThue());
        System.out.printf("Đơn giá thuê sảnh: %,.2f\n", this.tinhDonGiaThueSanh());

        System.out.println("=>Thông tin Menu");
        this.menu.showInfo();

        System.out.println("=>Thông tin các dịch vụ đính kèm");
        this.dsDichVu.forEach(dichVu -> dichVu.showInfo());

        System.out.println();
        System.out.printf("Tổng số tiền phải trả là: %,.2f\n", this.tinhTongTienPhaiTra());
    }

//cho thue sanh
    public void choThueSanh(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        //this.nhapThongTinBuoiTiec();
        scanner.nextLine();
        System.out.println("=>NHẬP THÔNG TIN KHÁCH HÀNG");
        this.nhapThongTinKhachHang(scanner);
        System.out.print("Nhập tên buổi tiệc: ");
        tenBuoiTiec = scanner.nextLine();
        System.out.println("=>CHỌN SẢNH CHO BUỔI TIỆC");
        this.thueSanh(scanner);
        System.out.println("=>CHỌN MÓN ĂN CHO MENU");
        this.taoMenu(scanner);
        System.out.println("=>CHỌN DỊCH VỤ ĐÍNH KÈM CHO BUỔI TIỆC");
        this.themDichVu(scanner);

        String selectQuery3 = "select max(MaTiec) from thongtintiec";
        Statement stm3 = conn.createStatement();
        ResultSet rs3 = stm3.executeQuery(selectQuery3);
        while (rs3.next()) {
            maTiec = rs3.getInt("max(MaTiec)") + 1;
        }

        int maMenu = 0;
        String selectQuery2 = "select max(MaMenu) from menu";
        Statement stm2 = conn.createStatement();
        ResultSet rs2 = stm2.executeQuery(selectQuery2);
        while (rs2.next()) {
            maMenu = rs2.getInt("max(MaMenu)");
        }

        try {
            String insertQuery = "insert into thongtintiec(MaTiec, KhachHang_ID, TenBuoiTiec,"
                    + " SanhCuoi_ID, NgayThue, ThoiDiemThue_ID, Menu_ID, TienPhaiTra)"
                    + "values(?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preStm = conn.prepareStatement(insertQuery);
            preStm.setInt(1, this.maTiec);
            preStm.setInt(2, this.khachHang.getMaKhachHang());
            preStm.setString(3, this.tenBuoiTiec);
            preStm.setInt(4, this.sanhThue.getMaSanh());
            preStm.setString(5, this.ngayThue);
            preStm.setInt(6, thoiDiemThue.getMaThoiDiem());
            preStm.setInt(7, maMenu);
            preStm.setDouble(8, this.tinhTongTienPhaiTra());
            preStm.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Thêm thất bại");
            System.err.println(ex.getMessage());
        }

        for (DichVu dv : this.dsDichVu) {
            try {
                String insertDichVu = "insert into tiec_dichvu(Tiec_ID, DichVu_ID)" + "values(?, ?)";
                PreparedStatement preStm = conn.prepareStatement(insertDichVu);
                preStm.setInt(1, this.maTiec);
                preStm.setInt(2, dv.getMaDichVu());
                preStm.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Thêm thất bại");
                System.err.println(ex.getMessage());
            }
        }

        scanner.nextLine();
        System.out.print("=>Bạn có muốn xuất hóa đơn không (c,k): ");
        String xuatHoaDon = scanner.nextLine().toLowerCase();
        if (xuatHoaDon.toLowerCase().contains("c") == true) {
            this.xuatHoaDon();
        } else {
            System.out.println("=>KẾT THÚC NHẬP THÔNG TIN CHO BUỔI TIỆC");
        }

        conn.close();
    }

// tinh tong tien phải tra
    public double tinhTongTienPhaiTra() {
        double tienPhaiTra = 0;

        tienPhaiTra = this.tinhDonGiaThueSanh() + this.tinhTongTienDichVu() + this.menu.tinhDonGiaMenu() * this.sanhThue.getSucChua();

        return tienPhaiTra;
    }

//bao cao doanh thu theo thang
    public void baoCaoDoanhThuTheoThang(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>NHẬP THÔNG TIN ĐỂ XUẤT BÁO CÁO DOANH THU");
        System.out.print(">Nhập tháng cần báo cáo doanh thu: ");
        Integer thang = scanner.nextInt();
        System.out.print(">Nhập năm cần báo cáo doanh thu: ");
        Integer nam = scanner.nextInt();
        System.out.printf("=>Danh sách tiệc trong tháng: %d<=\n", thang);

        String selectQuery = "select * from thongtintiec where NgayThue like ? ";
        PreparedStatement preStm = conn.prepareStatement(selectQuery);
        preStm.setString(1, "%" + thang.toString() + "/" + nam.toString() + "%");
        ResultSet rs = preStm.executeQuery();
        double tongDoanhThu = 0;
        int soTiec = 0;
        while (rs.next()) {
            int maTiec = rs.getInt("MaTiec");
            String ngayThue = rs.getString("NgayThue");
            double tongTien = rs.getDouble("TienPhaiTra");
            System.out.printf("Mã tiệc: %d - Ngày thuê: %s - Tổng tiền: %,.2f\n", maTiec, ngayThue, tongTien);
            tongDoanhThu += tongTien;
            soTiec++;
        }
        System.out.printf("-Tổng số tiệc trong tháng %s: %d\n", thang, soTiec);
        System.out.printf("-Tổng doanh thu tháng %s: %,.2f\n", thang, tongDoanhThu);
        conn.close();
    }

//bao cao doanh thu theo nam
    public void baoCaoDoanhThuTheoNam(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>NHẬP THÔNG TIN ĐỂ XUẤT BÁO CÁO DOANH THU");
        System.out.print(">Nhập năm cần báo cáo doanh thu: ");
        Integer nam = scanner.nextInt();
        System.out.printf("=>Danh sách tiệc trong năm: %d<=\n", nam);

        String selectQuery = "select * from thongtintiec where NgayThue like ? ";
        PreparedStatement preStm = conn.prepareStatement(selectQuery);
        preStm.setString(1, "%" + nam.toString() + "%");
        ResultSet rs = preStm.executeQuery();
        double tongDoanhThu = 0;
        int soTiec = 0;
        while (rs.next()) {
            int maTiec = rs.getInt("MaTiec");
            String ngayThue = rs.getString("NgayThue");
            double tongTien = rs.getDouble("TienPhaiTra");
            System.out.printf("Mã tiệc: %d --- Ngày thuê: %s --- Tổng tiền: %,.2f\n", maTiec, ngayThue, tongTien);
            tongDoanhThu += tongTien;
            soTiec++;
        }
        System.out.printf("-Tổng số tiệc trong năm %s: %d\n", nam, soTiec);
        System.out.printf("-Tổng doanh thu năm %s: %,.2f\n", nam, tongDoanhThu);
        conn.close();
    }

// getter and setter
    public int getMaTiec() {
        return maTiec;
    }

    public void setMaTiec(int maTiec) {
        this.maTiec = maTiec;
    }

    public String getTenBuoiTiec() {
        return tenBuoiTiec;
    }

    public void setTenBuoiTiec(String tenBuoiTiec) {
        this.tenBuoiTiec = tenBuoiTiec;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public SanhCuoi getSanhThue() {
        return sanhThue;
    }

    public void setSanhThue(SanhCuoi sanhThue) {
        this.sanhThue = sanhThue;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public ThoiDiem getThoiDiemThue() {
        return thoiDiemThue;
    }

    public void setThoiDiemThue(ThoiDiem thoiDiemThue) {
        this.thoiDiemThue = thoiDiemThue;
    }

    public double getDonGiaThueSanh() {
        return donGiaThueSanh;
    }

    public void setDonGiaThueSanh(double donGiaThueSanh) {
        this.donGiaThueSanh = donGiaThueSanh;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public double getDonGiaThucDon() {
        return donGiaThucDon;
    }

    public void setDonGiaThucDon(double donGiaThucDon) {
        this.donGiaThucDon = donGiaThucDon;
    }

    public List<DichVu> getDsDichVu() {
        return dsDichVu;
    }

    public void setDsDichVu(List<DichVu> dsDichVu) {
        this.dsDichVu = dsDichVu;
    }

    public double getDonGiaCacDichVu() {
        return donGiaCacDichVu;
    }

    public void setDonGiaCacDichVu(double donGiaCacDichVu) {
        this.donGiaCacDichVu = donGiaCacDichVu;
    }

// tao ket noi den workbench
    public static Connection getConnection(String dbURL, String userName, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            // System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
