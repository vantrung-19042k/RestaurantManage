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
                System.out.println("=>Ho??n t???t nh???p th??ng tin kh??ch h??ng");
            }
        } catch (SQLException ex) {
            System.out.println("=>Nh???p th??ng tin kh??ch h??ng th???t b???i");
            System.err.println(ex.getMessage());
        }

        System.out.printf("-T??n kh??ch h??ng: %s\n", this.khachHang.getTenKhachHang());
        System.out.printf("-S??T kh??ch h??ng: %s\n", this.khachHang.getSdtKhachHang());

        conn.close();
    }

// thue sanh
    public void thueSanh(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>Danh s??ch s???nh c?????i hi???n c??");
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

        System.out.print("=>Nh???p m?? s???nh b???n mu???n ch???n: S");
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

        System.out.println("=>S???nh ???? ch???n cho bu???i ti???c");
        sanhThue.showInfo();

        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("=>Nh???p th??ng tin ng??y thu?? s???nh");
        System.out.println("-Nh???p ng??y: ");
        int ngay = scanner.nextInt();
        System.out.println("-Nh???p th??ng:");
        int thang = scanner.nextInt();
        System.out.println("-Nh???p n??m: ");
        int nam = scanner.nextInt();

        GregorianCalendar ngayDatTiec = new GregorianCalendar(nam, (thang - 1), ngay);
        int dayOfWeek = ngayDatTiec.get(Calendar.DAY_OF_WEEK);
        System.out.printf("=>Ng??y bu???i ti???c s??? di???n ra: %s\n", f.format(ngayDatTiec.getTime()));
        setNgayThue(f.format(ngayDatTiec.getTime()));

        String thoiDiem = scanner.nextLine();
        System.out.println("=>CH???N TH???I ??I???M THU??");
        if (dayOfWeek > 1 && dayOfWeek < 7) {
            thoiDiem = "ng??y th?????ng";
        } else {
            thoiDiem = "ng??y cu???i tu???n";
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
                System.out.printf("M?? th???i ??i???m: %d --- ", maThoiDiem);
                System.out.printf("Th???i ??i???m thu??: %-16s --- ", thoiDiemChoThue);
                System.out.printf("H??? s??? cho thu??: %s\n", heSoThue);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.print("=>Nh???p m?? th???i ??i???m ????? ch???n th???i ??i???m thu??: ");
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

        System.out.println("=>Th???i ??i???m thu?? ???? ch???n");
        thoiDiemThue.showInfo();
        System.out.printf("????n gi?? thu?? s???nh: %,.2f\n", tinhDonGiaThueSanh());

        conn.close();
    }

// tinh don gia thue sanh
    public double tinhDonGiaThueSanh() {
        double donGiaThue = thoiDiemThue.getHeSoChoThue() * sanhThue.getGiaThueCoBan();
        return donGiaThue;
    }

// t???o menu
    public void taoMenu(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        scanner.nextLine();
        System.out.print("Nh???p t??n menu: ");
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
                System.out.println("=>Ho??n t???t nh???p th??ng tin Menu");
            } else {
                System.out.println("Nh???p menu l???i");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        System.out.println("=>Danh s??ch m??n ??n nh?? h??ng hi???n c??");
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
                System.out.print("=>Nh???p m?? m??n ??n c???n th??m: ");
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
                            System.out.println("=>Th??m th??nh c??ng");
                        } else {
                            System.out.println("=>Th??m th???t b???i");
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
                            System.out.println("=>Th??m th??nh c??ng");
                        } else {
                            System.out.println("=>Th??m th???t b???i");
                        }
                    }
                }

                System.out.println("=>Ti???p t???c th??m m??n ??n v??o menu (c,k): ");
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

        System.out.println("=>Danh s??ch d???ch v??? nh?? h??ng hi???n c??");
        try {
            String selectQuery = "select * from dichvu";
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(selectQuery);
            while (rs.next()) {
                int maDichVu = rs.getInt("MaDichVu");
                String tenDichVu = rs.getString("TenDichVu");
                double giaDichVu = rs.getDouble("GiaDichVu");
                System.out.printf("M?? d???ch v???: DV-%03d ---", maDichVu);
                System.out.printf("T??n d???ch v???: %s ---", tenDichVu);
                System.out.printf("Gi?? d???ch v???: %.2f\n", giaDichVu);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        try {
            boolean continued = true;
            do {
                System.out.print("=>Nh???p t??n d???ch v??? c???n th??m: ");
                String tenDichVuThem = scanner.nextLine().toLowerCase();
                int thoiGianThue = 0;
                String tenCaSi = null;
                int soBaiHat = 0;
                if (tenDichVuThem.contains("karaoke")) {
                    System.out.print("=>Nh???p s??? gi??? mu???n thu??: ");
                    thoiGianThue = scanner.nextInt();
                    scanner.nextLine();
                } else if (tenDichVuThem.contains("thu?? ca s??")) {
                    System.out.print("=>Nh???p t??n ca s?? mu???n thu??: ");
                    tenCaSi = scanner.nextLine();
                    System.out.print("=>Nh???p s??? l?????ng b??i h??t: ");
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
                    } else if (tenDichVuThem.contains("thu?? ca s??")
                            && tenDichVu.toLowerCase().contains(tenDichVuThem)) {
                        DichVu thueCaSi = new ThueCaSi(maDichVu, tenDichVu, giaDichVu, tenCaSi, soBaiHat);
                        this.dsDichVu.add(thueCaSi);
                    }
                }

                System.out.println("=>B???n mu???n th??m d???ch v??? n??o n???a kh??ng(c,k): ");
                String tiepTuc = scanner.nextLine();
                if (tiepTuc.toLowerCase().contains("c")) {
                    continued = true;
                } else {
                    System.out.println("=>K???t th??c th??m d???ch v???");
                    continued = false;
                }
            } while (continued == true);

            System.out.println("=>Danh s??ch d???ch v??? ???? ch???n");
            this.dsDichVu.forEach(dichVu -> dichVu.showInfo());
            System.out.printf("=>T???ng ti???n c??c d???ch v???: %,.2f\n", this.tinhTongTienDichVu());
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
        System.out.println("==========>H??A ????N THANH TO??N<==========");

        System.out.println("=>Th??ng tin kh??ch h??ng");
        System.out.printf("-T??n kh??ch h??ng: %s\n", this.khachHang.getTenKhachHang());
        System.out.printf("-S??T kh??ch h??ng: %s\n", this.khachHang.getSdtKhachHang());
        System.out.println("=>Th??ng tin s???nh");

        //this.sanhThue.showInfo();
        System.out.printf("-M?? s???nh: %d --- T??n s???nh: %s --- S???c ch???a: %d (b??n) --- V??? tr??: %s\n",
                this.sanhThue.getMaSanh(), this.sanhThue.getTenSanh(), this.sanhThue.getSucChua(),
                this.sanhThue.getViTri());
        //this.thoiDiemThue.showInfo();
        System.out.printf("-Th???i ??i???m thu??: %s\n", this.thoiDiemThue.getThoiDiemThue());
        System.out.printf("????n gi?? thu?? s???nh: %,.2f\n", this.tinhDonGiaThueSanh());

        System.out.println("=>Th??ng tin Menu");
        this.menu.showInfo();

        System.out.println("=>Th??ng tin c??c d???ch v??? ????nh k??m");
        this.dsDichVu.forEach(dichVu -> dichVu.showInfo());

        System.out.println();
        System.out.printf("T???ng s??? ti???n ph???i tr??? l??: %,.2f\n", this.tinhTongTienPhaiTra());
    }

//cho thue sanh
    public void choThueSanh(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        //this.nhapThongTinBuoiTiec();
        scanner.nextLine();
        System.out.println("=>NH???P TH??NG TIN KH??CH H??NG");
        this.nhapThongTinKhachHang(scanner);
        System.out.print("Nh???p t??n bu???i ti???c: ");
        tenBuoiTiec = scanner.nextLine();
        System.out.println("=>CH???N S???NH CHO BU???I TI???C");
        this.thueSanh(scanner);
        System.out.println("=>CH???N M??N ??N CHO MENU");
        this.taoMenu(scanner);
        System.out.println("=>CH???N D???CH V??? ????NH K??M CHO BU???I TI???C");
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
            System.out.println("Th??m th???t b???i");
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
                System.out.println("Th??m th???t b???i");
                System.err.println(ex.getMessage());
            }
        }

        scanner.nextLine();
        System.out.print("=>B???n c?? mu???n xu???t h??a ????n kh??ng (c,k): ");
        String xuatHoaDon = scanner.nextLine().toLowerCase();
        if (xuatHoaDon.toLowerCase().contains("c") == true) {
            this.xuatHoaDon();
        } else {
            System.out.println("=>K???T TH??C NH???P TH??NG TIN CHO BU???I TI???C");
        }

        conn.close();
    }

// tinh tong tien ph???i tra
    public double tinhTongTienPhaiTra() {
        double tienPhaiTra = 0;

        tienPhaiTra = this.tinhDonGiaThueSanh() + this.tinhTongTienDichVu() + this.menu.tinhDonGiaMenu() * this.sanhThue.getSucChua();

        return tienPhaiTra;
    }

//bao cao doanh thu theo thang
    public void baoCaoDoanhThuTheoThang(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>NH???P TH??NG TIN ????? XU???T B??O C??O DOANH THU");
        System.out.print(">Nh???p th??ng c???n b??o c??o doanh thu: ");
        Integer thang = scanner.nextInt();
        System.out.print(">Nh???p n??m c???n b??o c??o doanh thu: ");
        Integer nam = scanner.nextInt();
        System.out.printf("=>Danh s??ch ti???c trong th??ng: %d<=\n", thang);

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
            System.out.printf("M?? ti???c: %d - Ng??y thu??: %s - T???ng ti???n: %,.2f\n", maTiec, ngayThue, tongTien);
            tongDoanhThu += tongTien;
            soTiec++;
        }
        System.out.printf("-T???ng s??? ti???c trong th??ng %s: %d\n", thang, soTiec);
        System.out.printf("-T???ng doanh thu th??ng %s: %,.2f\n", thang, tongDoanhThu);
        conn.close();
    }

//bao cao doanh thu theo nam
    public void baoCaoDoanhThuTheoNam(Scanner scanner) throws SQLException {
        Connection conn = getConnection(DB_URL, USER_NAME, PASSWORD);

        System.out.println("=>NH???P TH??NG TIN ????? XU???T B??O C??O DOANH THU");
        System.out.print(">Nh???p n??m c???n b??o c??o doanh thu: ");
        Integer nam = scanner.nextInt();
        System.out.printf("=>Danh s??ch ti???c trong n??m: %d<=\n", nam);

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
            System.out.printf("M?? ti???c: %d --- Ng??y thu??: %s --- T???ng ti???n: %,.2f\n", maTiec, ngayThue, tongTien);
            tongDoanhThu += tongTien;
            soTiec++;
        }
        System.out.printf("-T???ng s??? ti???c trong n??m %s: %d\n", nam, soTiec);
        System.out.printf("-T???ng doanh thu n??m %s: %,.2f\n", nam, tongDoanhThu);
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
