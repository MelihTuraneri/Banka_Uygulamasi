/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.Date;

/**
 *
 * @author melih
 */
public class musteri {

    String url = "jdbc:mysql://localhost:3306/banka";
    String username = "root";
    String password = "Mysql_sifresi123";
    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    int veri_girisi = 0;
    int kullanıcı_id;

    musteri(int kullanıcı_id) {
        this.kullanıcı_id = kullanıcı_id;
    }

    void musteri_bilgi_guncelleme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.print("\n\nAd Soyad güncellemek için 1'e basın yoksa 0'a basın : ");
            int secim = sc.nextInt();
            if (secim == 1) {
                String sql = "UPDATE bilgi SET ad_soyad = ? where kullanıcı_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql);
                System.out.print("\n\nYeni Ad Soyad giriniz :");
                String isim = br.readLine();
                statement1.setString(1, isim);
                statement1.setInt(2, kullanıcı_id);
                statement1.executeUpdate();
                statement1.close();
            }
            System.out.print("\n\nTel NO güncellemek için 1'e basın yoksa 0'a basın : ");
            secim = sc.nextInt();
            if (secim == 1) {
                String sql = "UPDATE bilgi SET tel_no = ? where kullanıcı_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql);
                System.out.print("\n\nYeni Tel NO giriniz :");
                int tel = sc1.nextInt();
                statement1.setInt(1, tel);
                statement1.setInt(2, kullanıcı_id);
                statement1.executeUpdate();
                statement1.close();
            }
            System.out.print("\n\nTc NO güncellemek için 1'e basın yoksa 0'a basın : ");
            secim = sc.nextInt();
            if (secim == 1) {
                String sql = "UPDATE bilgi SET tc_no = ? where kullanıcı_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql);
                System.out.print("\n\nYeni Tc NO giriniz :");
                int tc = sc1.nextInt();
                statement1.setInt(1, tc);
                statement1.setInt(2, kullanıcı_id);
                statement1.executeUpdate();
                statement1.close();
            }
            System.out.print("\n\nAdres güncellemek için 1'e basın yoksa 0'a basın : ");
            secim = sc.nextInt();
            if (secim == 1) {
                String sql = "UPDATE bilgi SET adres = ? where kullanıcı_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql);
                System.out.print("\n\nYeni Adres giriniz :");
                String adres = br.readLine();
                statement1.setString(1, adres);
                statement1.setInt(2, kullanıcı_id);
                statement1.executeUpdate();
                statement1.close();
            }
            System.out.print("\n\nE-Posta güncellemek için 1'e basın yoksa 0'a basın : ");
            secim = sc.nextInt();
            if (secim == 1) {
                String sql = "UPDATE bilgi SET e_posta = ? where kullanıcı_id = ?";
                PreparedStatement statement1 = connection.prepareStatement(sql);
                System.out.print("\n\nYeni E-Posta giriniz :");
                String mail = br.readLine();
                statement1.setString(1, mail);
                statement1.setInt(2, kullanıcı_id);
                statement1.executeUpdate();
                statement1.close();
            }
            connection.close();

        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void para_cekme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT * FROM hesaplar WHERE kid = ";
                PreparedStatement statement = connection.prepareStatement(sql);
                String ekleme = Integer.toString(kullanıcı_id);
                sql = sql.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    int hesap_no = result.getInt("hesap_no");
                    int temsilci_no = result.getInt("temsilci_no");
                    int para = result.getInt("bakiye");
                    String kur = result.getString("kur");
                    if (kur.startsWith("TL")) {
                        System.out.print("\n" + hesap_no + " HESAP NO lu  " + kur + " hesabınızdan para çekmek istiyorsanız 1'e basın yoksa 0'a basın : ");
                        veri_girisi = sc.nextInt();
                        if (veri_girisi == 1) {
                            System.out.print("\n" + kur + " hesabınızdan çekmek istediğiniz mikatarı giriniz : ");
                            veri_girisi = sc.nextInt();
                            if (para >= veri_girisi) {
                                String str = "2015-03-31";
                                Date date = Date.valueOf(str);
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, para - veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("\nHesabınızdan" + veri_girisi + " TL başarılı bir şekilde çekilmiştir!!");
                                sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, hesap_no);
                                statement.setInt(2, kullanıcı_id);
                                statement.setString(3, "Para Çekme");
                                statement.setInt(4, veri_girisi);
                                statement.setInt(5, para);
                                statement.setDate(6, date);
                                statement.setInt(7, temsilci_no);
                                statement.executeUpdate();
                            } else {
                                System.out.println("\nHesabınızda yeterli miktarda para yok!! FUKARAAAAAHAHAHAHAA");
                            }
                        }
                    } else {
                        System.out.print("\n" + hesap_no + " HESAP NO lu  " + kur + " hesabınızdan para çekmek istiyorsanız 1'e basın yoksa 0'a basın : ");
                        veri_girisi = sc.nextInt();
                        if (veri_girisi == 1) {
                            System.out.println("\nDİKKAT!! Hesaplardan yalnızca TL şeklinde para çekilebilir.Bu sebeple istediğniz miktar,anlık kur ile çarpılarak çekilecektir!!");
                            System.out.print("\n" + kur + " hesabınızdan çekmek istediğiniz mikatarı giriniz : ");
                            veri_girisi = sc.nextInt();
                            if (para >= veri_girisi) {
                                String sql1 = "SELECT * FROM kurlar WHERE kur = '";
                                sql1 = sql1.concat(kur + "'");
                                PreparedStatement statement1 = connection.prepareStatement(sql1);
                                ResultSet result1 = statement1.executeQuery(sql1);
                                result1.next();
                                float karsılıgı = result1.getFloat("karsılıgı");
                                result1.close();
                                String str = "2015-03-31";
                                Date date = Date.valueOf(str);
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, para - veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("\nHesabınızdan " + veri_girisi + "(" + (karsılıgı * veri_girisi) + " TL)$" + "başarılı şekilde çekilmiştir!!");
                                sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, hesap_no);
                                statement.setInt(2, kullanıcı_id);
                                statement.setString(3, "Para Çekme");
                                statement.setInt(4, veri_girisi);
                                statement.setInt(5, para);
                                statement.setDate(6, date);
                                statement.setInt(7, temsilci_no);
                                statement.executeUpdate();
                            } else {
                                System.out.println("\nHesabınızda yeterli miktarda para yok!! FUKARAAAAAHAHAHAHAA");
                            }
                        }
                    }
                }
                System.out.println("");
                statement.close();
                connection.close();
                break;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void para_yatirma() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT * FROM hesaplar WHERE kid = ";
                PreparedStatement statement = connection.prepareStatement(sql);
                String ekleme = Integer.toString(kullanıcı_id);
                sql = sql.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    int hesap_no = result.getInt("hesap_no");
                    int temsilci_no = result.getInt("temsilci_no");
                    String kur = result.getString("kur");
                    if (kur != "TL") {
                        System.out.print("\n" + hesap_no + " HESAP NO lu  " + kur + " hesabınıza para yatirmak istiyorsanız 1'e basın yoksa 0'a basın : ");
                        veri_girisi = sc.nextInt();
                        if (veri_girisi == 1) {
                            System.out.print("\n" + kur + " hesabınıza yatirmak istediğiniz mikatarı giriniz : ");
                            veri_girisi = sc.nextInt();
                            int para = result.getInt("bakiye");
                            String str = "2015-03-31";
                            Date date = Date.valueOf(str);
                            sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                            ekleme = Integer.toString(hesap_no);
                            sql = sql.concat(ekleme);
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, para + veri_girisi);
                            statement.executeUpdate();
                            statement.close();
                            System.out.println("\nBAŞARILI");
                            sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,hedef_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, kullanıcı_id);
                            statement.setInt(2, hesap_no);
                            statement.setString(3, "Para Yatirma");
                            statement.setInt(4, veri_girisi);
                            statement.setInt(5, para);
                            statement.setDate(6, date);
                            statement.setInt(7, temsilci_no);
                            statement.executeUpdate();
                        }
                    } else {
                        System.out.print("\n" + hesap_no + " HESAP NO lu  " + kur + " hesabınızdan para yatirmak istiyorsanız 1'e basın yoksa 0'a basın : ");
                        veri_girisi = sc.nextInt();
                        if (veri_girisi == 1) {
                            System.out.print("\n" + kur + " hesabınıza yatirmak istediğiniz mikatarı giriniz : ");
                            veri_girisi = sc.nextInt();
                            int para = result.getInt("bakiye");
                            String str = "2015-03-31";
                            Date date = Date.valueOf(str);
                            sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                            ekleme = Integer.toString(hesap_no);
                            sql = sql.concat(ekleme);
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, para + veri_girisi);
                            statement.executeUpdate();
                            statement.close();
                            System.out.println("\nBAŞARILI");
                            sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,hedef_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                            statement = connection.prepareStatement(sql);
                            statement.setInt(1, kullanıcı_id);
                            statement.setInt(2, hesap_no);
                            statement.setString(3, "Para Yatirma");
                            statement.setInt(4, veri_girisi);
                            statement.setInt(5, para);
                            statement.setDate(6, date);
                            statement.setInt(7, temsilci_no);
                            statement.executeUpdate();
                        }
                    }
                }
                statement.close();
                connection.close();
                break;
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void para_transferi() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT COUNT(hesap_no) FROM hesaplar WHERE hesap_no = ";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.print("\nPara transfer etmek istediğiniz hesap numarasını giriniz : ");
                int hedef = sc.nextInt();
                String ekleme = Integer.toString(hedef);
                sql = sql.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                result.next();
                int varmi = result.getInt("COUNT(hesap_no)");
                String sql1 = "SELECT * FROM hesaplar WHERE hesap_no = ";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                String ekleme1 = Integer.toString(hedef);
                sql1 = sql1.concat(ekleme1);
                ResultSet result1 = statement1.executeQuery(sql1);
                result1.next();
                String kur1 = result1.getString("kur");
                if (varmi == 1) {
                    sql = "SELECT * FROM hesaplar WHERE hesap_no = ";
                    statement = connection.prepareStatement(sql);
                    sql = sql.concat(ekleme);
                    result = statement.executeQuery(sql);
                    result.next();
                    int bakiye = result.getInt("bakiye");
                    sql = "SELECT * FROM hesaplar WHERE kid = ";
                    statement = connection.prepareStatement(sql);
                    ekleme = Integer.toString(kullanıcı_id);
                    sql = sql.concat(ekleme);
                    result = statement.executeQuery(sql);
                    while (result.next()) {
                        int hesap_no = result.getInt("hesap_no");
                        int temsilci_no = result.getInt("temsilci_no");
                        String kur = result.getString("kur");
                        System.out.print("\n" + hesap_no + " HESAP NO lu  " + kur + " hesabınızdan " + hedef + " HESAP NO lu hesaba para transfer etmek istiyorsanız 1'e basın yoksa 0'a basın : ");
                        veri_girisi = sc.nextInt();
                        if (veri_girisi == 1) {
                            System.out.print("\n" + kur + " hesabınızdan transfer etmek istediğiniz para miktarini giriniz : ");
                            veri_girisi = sc.nextInt();
                            if (kur == kur1) {
                                int para = result.getInt("bakiye");
                                String str = "2015-03-31";
                                Date date = Date.valueOf(str);
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, para - veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hedef);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, bakiye + veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("\n" + hesap_no + "hesap no lu hesabınızdan " + hedef + "hesap no lu hesaba " + veri_girisi + " " + kur + "başarıyla transfer edilmiştir!!");
                                sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, hesap_no);
                                statement.setInt(2, hedef);
                                statement.setString(3, "Para Gönderme");
                                statement.setInt(4, veri_girisi);
                                statement.setInt(5, para);
                                statement.setDate(6, date);
                                statement.setInt(7, temsilci_no);
                                statement.executeUpdate();
                            } else if (kur.startsWith("TL") || kur1.startsWith("TL")) {
                                System.out.println("\nDİKKAT!! Farklı kura sahip hesaplara para transfer edilirken gönderilmek istenen miktar kur ile çarpılarak transfer edilir!!");
                                String sql2 = "SELECT * FROM kurlar WHERE kur = '";
                                sql2 = sql2.concat(kur + "'");
                                PreparedStatement statement2 = connection.prepareStatement(sql2);
                                ResultSet result2 = statement2.executeQuery(sql2);
                                result2.next();
                                float karsılıgı = result2.getFloat("karsılıgı");
                                result2.close();
                                int para = result.getInt("bakiye");
                                String str = "2015-03-31";
                                Date date = Date.valueOf(str);
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, para - veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hedef);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, bakiye + (int) (karsılıgı * veri_girisi));
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("\n" + hesap_no + " hesap no lu hesabınızdan " + hedef + " hesap no lu hesaba " + veri_girisi + " " + kur + " (" + (int) (karsılıgı * veri_girisi) + ")" + "TL başarıyla transfer edilmiştir!!");
                                sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, hesap_no);
                                statement.setInt(2, hedef);
                                statement.setString(3, "Para Gönderme");
                                statement.setInt(4, (int) (karsılıgı * veri_girisi));
                                statement.setInt(5, para);
                                statement.setDate(6, date);
                                statement.setInt(7, temsilci_no);
                                statement.executeUpdate();
                            } else if (kur1.startsWith("TL")) {
                                System.out.println("\nDİKKAT!! Farklı kura sahip hesaplara para transfer edilirken gönderilmek istenen miktar kur ile çarpılarak transfer edilir!!");
                                String sql2 = "SELECT * FROM kurlar WHERE kur = '";
                                sql2 = sql2.concat(kur + "'");
                                PreparedStatement statement2 = connection.prepareStatement(sql2);
                                ResultSet result2 = statement2.executeQuery(sql2);
                                result2.next();
                                float karsılıgı = result2.getFloat("karsılıgı");
                                result2.close();
                                int para = result.getInt("bakiye");
                                String str = "2015-03-31";
                                Date date = Date.valueOf(str);
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, para - veri_girisi);
                                statement.executeUpdate();
                                statement.close();
                                sql = "UPDATE hesaplar SET bakiye = ? where hesap_no = ";
                                ekleme = Integer.toString(hedef);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, bakiye + (int) ((1 / karsılıgı) * veri_girisi));
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("\n" + hesap_no + " hesap no lu hesabınızdan " + hedef + " hesap no lu hesaba " + veri_girisi + " " + kur + " (" + (int) ((1 / karsılıgı) * veri_girisi) + ")" + "TL başarıyla transfer edilmiştir!!");
                                sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                                statement = connection.prepareStatement(sql);
                                statement.setInt(1, hesap_no);
                                statement.setInt(2, hedef);
                                statement.setString(3, "Para Gönderme");
                                statement.setInt(4, (int) (karsılıgı * veri_girisi));
                                statement.setInt(5, para);
                                statement.setDate(6, date);
                                statement.setInt(7, temsilci_no);
                                statement.executeUpdate();
                            } else {
                                System.out.println("Para transferlerinde iki hesaptan en az biri TL hesabı olmalıdır.Çıkışa yönlendiriliyorsunuz");
                                break;
                            }
                        }
                    }
                    break;
                } else {
                    System.out.println("\nGirilen hesap no yanliş.Lütfen tekrar giriniz...");
                }
                statement.close();
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void aylik_kredi_borcu() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT COUNT(*) FROM hesaplar WHERE kid = ";
            PreparedStatement statement = connection.prepareStatement(sql);
            String ekleme = Integer.toString(kullanıcı_id);
            sql = sql.concat(ekleme);
            String sql1 = " and kur = 'TL'";
            sql = sql.concat(sql1);
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int varmi = result.getInt("COUNT(*)");
            if (varmi == 1) {
                sql = "SELECT * FROM hesaplar WHERE kid = ";
                statement = connection.prepareStatement(sql);
                ekleme = Integer.toString(kullanıcı_id);
                sql = sql.concat(ekleme);
                sql1 = " and kur = 'TL'";
                sql = sql.concat(sql1);
                result = statement.executeQuery(sql);
                result.next();
                int hesap_no = result.getInt("hesap_no");
                int temsilci_no = result.getInt("temsilci_no");
                int aylık_borc = result.getInt("aylık_borc");
                int toplam_borc = result.getInt("toplam_borc");
                String kur = result.getString("kur");
                int para = result.getInt("bakiye");
                float gecikme_faizi = result.getFloat("gecikme_faizi");
                int gecikme_borcu = result.getInt("gecikme_borcu");
                float faiz = result.getFloat("faiz");
                int fazlalık = (int) ((toplam_borc - aylık_borc) * faiz);
                if (toplam_borc == 0) {
                    System.out.println("Borcunuz bulunmamaktadır :D");
                } else if (para >= aylık_borc + gecikme_borcu) {
                    String str = "2015-03-31";
                    Date date = Date.valueOf(str);
                    sql = "UPDATE hesaplar SET bakiye = ?,toplam_borc = ?,aylık_borc = ? where hesap_no = ";
                    ekleme = Integer.toString(hesap_no);
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, para - aylık_borc);
                    statement.setInt(2, toplam_borc - aylık_borc);
                    if (toplam_borc - aylık_borc == 0) {
                        statement.setInt(3, 0);
                        System.out.println("Borcuzun bitmistir :D");
                    } else {
                        statement.setInt(3, aylık_borc);
                    }
                    System.out.println("Ödenen Aylık Borç miktarı : " + aylık_borc + gecikme_borcu);
                    System.out.println("Ödenen Faiz miktarı : " + fazlalık + gecikme_borcu);
                    statement.executeUpdate();
                    statement.close();
                    System.out.println("\nBAŞARILI");
                    sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, hesap_no);
                    statement.setInt(2, 99999);
                    statement.setString(3, "Borç Ödeme");
                    statement.setInt(4, aylık_borc);
                    statement.setInt(5, para);
                    statement.setDate(6, date);
                    statement.setInt(7, temsilci_no);
                    statement.executeUpdate();
                } else {
                    String str = "2015-03-31";
                    Date date = Date.valueOf(str);
                    sql = "UPDATE hesaplar SET bakiye = ?,toplam_borc = ?,aylık_borc = ?,gecikme_borcu = ? where hesap_no = ";
                    ekleme = Integer.toString(hesap_no);
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    if (para == 0) {
                        statement.setInt(1, 0);
                        statement.setInt(2, toplam_borc - aylık_borc + aylık_borc + (int) ((aylık_borc) * gecikme_faizi));
                        statement.setInt(3, aylık_borc);
                        statement.setInt(4, gecikme_borcu + (int) ((aylık_borc) * gecikme_faizi));
                        statement.executeUpdate();
                        statement.close();
                    } else {
                        statement.setInt(1, 0);
                        statement.setInt(2, toplam_borc - aylık_borc + aylık_borc + (int) ((aylık_borc - para) * gecikme_faizi) - para);
                        if (toplam_borc - aylık_borc == 0) {
                            statement.setInt(3, aylık_borc - para);
                        } else {
                            statement.setInt(3, aylık_borc);
                        }
                        statement.setInt(4, gecikme_borcu + (int) ((aylık_borc - para) * gecikme_faizi));
                        statement.executeUpdate();
                        statement.close();
                        sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, hesap_no);
                        statement.setInt(2, 99999);
                        statement.setString(3, "Borç Ödeme");
                        statement.setInt(4, para);
                        statement.setInt(5, para);
                        statement.setDate(6, date);
                        statement.setInt(7, temsilci_no);
                        System.out.println("Hesabınızda yeterli miktarda para yoktur!!");
                        System.out.println("\nÖdenen miktar : " + para);
                        statement.executeUpdate();
                    }
                }
            }
            System.out.println("");
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void toplam_kredi_borcu() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT COUNT(*) FROM hesaplar WHERE kid = ";
            PreparedStatement statement = connection.prepareStatement(sql);
            String ekleme = Integer.toString(kullanıcı_id);
            sql = sql.concat(ekleme);
            String sql1 = " and kur = 'TL'";
            sql = sql.concat(sql1);
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int varmi = result.getInt("COUNT(*)");
            if (varmi == 1) {
                sql = "SELECT * FROM hesaplar WHERE kid = ";
                statement = connection.prepareStatement(sql);
                ekleme = Integer.toString(kullanıcı_id);
                sql = sql.concat(ekleme);
                sql1 = " and kur = 'TL'";
                sql = sql.concat(sql1);
                result = statement.executeQuery(sql);
                result.next();
                int hesap_no = result.getInt("hesap_no");
                int temsilci_no = result.getInt("temsilci_no");
                int aylık_borc = result.getInt("aylık_borc");
                int toplam_borc = result.getInt("toplam_borc");
                String kur = result.getString("kur");
                int para = result.getInt("bakiye");
                float faiz = result.getFloat("faiz");
                int gecikme_borcu = result.getInt("gecikme_borcu");
                if (toplam_borc == 0) {
                    System.out.println("Borcunuz bulunmamaktadır :D");
                } else if (para >= toplam_borc + gecikme_borcu) {
                    String str = "2016-03-31";
                    Date date = Date.valueOf(str);
                    sql = "UPDATE hesaplar SET bakiye = ?,toplam_borc = ?,aylık_borc = ?,gecikme_borcu = ? where hesap_no = ";
                    ekleme = Integer.toString(hesap_no);
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    int fazlalık = (int) ((toplam_borc - aylık_borc) * faiz);
                    statement.setInt(1, (para - toplam_borc + fazlalık - gecikme_borcu));
                    statement.setInt(2, 0);
                    statement.setInt(3, 0);
                    statement.setInt(4, 0);
                    System.out.println("Ödenen Toplam Borç : " + toplam_borc + gecikme_borcu);
                    System.out.println("Ödenen Toplam Faiz : " + fazlalık + gecikme_borcu);
                    statement.executeUpdate();
                    statement.close();
                    System.out.println("\nBAŞARILI");
                    sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, hesap_no);
                    statement.setInt(2, 99999);
                    statement.setString(3, "Borç Ödeme");
                    statement.setInt(4, toplam_borc - fazlalık);
                    statement.setInt(5, para);
                    statement.setDate(6, date);
                    statement.setInt(7, temsilci_no);
                    statement.executeUpdate();
                } else {
                    System.out.println("\nHesabınızda yeterli miktarda para yok!! FUKARAAAAAHAHAHAHAA");
                }
            }
            System.out.println("");
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void özet() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT COUNT(islem_no) FROM özetler,hesaplar WHERE kid = ";
                String ekleme = Integer.toString(kullanıcı_id);
                sql = sql.concat(ekleme);
                String sql1 = " and (kaynak = hesap_no or hedef = hesap_no)";
                sql = sql.concat(sql1);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery(sql);
                result.next();
                int varmi = result.getInt("COUNT(islem_no)");
                result.close();
                if (varmi > 0) {
                    sql = "SELECT * FROM özetler,hesaplar WHERE kid = ";
                    sql = sql.concat(ekleme);
                    sql1 = " and (kaynak = hesap_no or hedef = hesap_no)";
                    sql = sql.concat(sql1);
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery(sql);
                    while (result.next()) {
                        int islem_no = result.getInt("islem_no");
                        String islem = result.getString("islem");
                        int hedef = result.getInt("hedef");
                        int tutar = result.getInt("tutar");
                        Date tarih = result.getDate("tarih");
                        System.out.println("\nİŞLEM NO : " + islem_no);
                        System.out.println("İŞLEM : " + islem);
                        System.out.println("HEDEF : " + hedef);
                        System.out.println("TUTAR : " + tutar);
                        System.out.println("TARİH : " + tarih);
                        System.out.println("");
                    }
                    statement.close();
                    break;
                } else {
                    System.out.println("\nBu kullanıcıya erişemezsiniz?!Lütfen tekrar giriniz...");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void müşteri_talepleri() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            int id = 0;
            while (true) {
                String sql = "INSERT INTO talepler (talep_türü, kullanıcı_id, temsilci_no, hesap_no, onay_durumu) VALUES(?,?,?,?,?)";
                String sql1 = "INSERT INTO talepler (talep_türü, kullanıcı_id, temsilci_no, onay_durumu) VALUES(?,?,?,?)";
                String sql2 = "SELECT temsilci_no FROM hesaplar WHERE kid = ";
                String sql3 = "INSERT INTO talepler (talep_türü, kullanıcı_id, temsilci_no, hesap_no, miktar, vade, onay_durumu) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                PreparedStatement statement3 = connection.prepareStatement(sql3);
                String ekleme = Integer.toString(kullanıcı_id);
                sql2 = sql2.concat(ekleme);
                System.out.print("\nHesap açmak istiyorsanız 1'e basın yoksa 0'a basın : ");
                veri_girisi = sc.nextInt();
                if (veri_girisi != 0) {
                    ResultSet result = statement1.executeQuery(sql2);
                    int temsilci_no = 0;
                    while (result.next()) {
                        temsilci_no = result.getInt("temsilci_no");
                        break;
                    }
                    statement1.setString(1, "Hesap Açma");
                    statement1.setInt(2, this.kullanıcı_id);
                    statement1.setInt(3, temsilci_no);
                    statement1.setString(4, "ONAY BEKLİYOR");
                    statement1.executeUpdate();
                }
                System.out.print("\nHesap silmek istiyorsanız 1'e basın yoksa 0'a basın : ");
                veri_girisi = sc.nextInt();
                if (veri_girisi != 0) {
                    ResultSet result = statement.executeQuery(sql2);
                    int temsilci_no = 0;
                    while (result.next()) {
                        temsilci_no = result.getInt("temsilci_no");
                        break;
                    }
                    System.out.print("\nSilmek istediğiniz hesap no : ");
                    int hesap_no = sc1.nextInt();
                    statement.setString(1, "Hesap Silme");
                    statement.setInt(2, this.kullanıcı_id);
                    statement.setInt(3, temsilci_no);
                    statement.setInt(4, hesap_no);
                    statement1.setString(5, "ONAY BEKLİYOR");
                    statement.executeUpdate();
                }
                System.out.print("\nKredi talep etmek istiyorsanız 1'e basın yoksa 0'a basın : ");
                veri_girisi = sc.nextInt();
                if (veri_girisi != 0) {
                    ResultSet result = statement3.executeQuery(sql2);
                    int temsilci_no = 0;
                    while (result.next()) {
                        temsilci_no = result.getInt("temsilci_no");
                        break;
                    }
                    int hesap_no = 0;
                    while (true) {
                        System.out.print("\nKredi çekmek istediğiniz hesap no : ");
                        hesap_no = sc1.nextInt();
                        sql = "SELECT COUNT(hesap_no) FROM hesaplar where hesap_no = ";
                        ekleme = Integer.toString(hesap_no);
                        sql = sql.concat(ekleme);
                        statement = connection.prepareStatement(sql);
                        result = statement.executeQuery(sql);
                        result.next();
                        int varmi = result.getInt("COUNT(hesap_no)");
                        result.close();
                        if (varmi != 1) {
                            System.out.println("\nGirilen hesap no yanlış.Lütfen tekrar giriniz...");
                        } else {
                            break;
                        }
                    }
                    System.out.print("\nÇekmek istediğiniz kredi miktarı : ");
                    int miktar = sc1.nextInt();
                    System.out.print("\nKaç taksit olsun istersiniz? : ");
                    int vade = sc1.nextInt();
                    statement3.setString(1, "Kredi Talebi");
                    statement3.setInt(2, this.kullanıcı_id);
                    statement3.setInt(3, temsilci_no);
                    statement3.setInt(4, hesap_no);
                    statement3.setInt(5, miktar);
                    statement3.setInt(6, vade);
                    statement3.setString(7, "ONAY BEKLİYOR");
                    statement3.executeUpdate();
                }
                statement.close();
                statement1.close();
                statement3.close();
                connection.close();
                break;
            }
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }
}
