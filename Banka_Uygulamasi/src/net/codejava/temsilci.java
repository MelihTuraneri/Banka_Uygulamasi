/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 *
 * @author melih
 */
public class temsilci {

    String url = "jdbc:mysql://localhost:3306/banka";
    String username = "root";
    String password = "Mysql_sifresi123";
    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    int veri_girisi = 0;
    int temsilci_no;

    temsilci(int kullanıcı_id, int temsilci_no) {
        this.temsilci_no = temsilci_no;
    }

    void musteri_ekleme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO bilgi (ad_soyad,tel_no,tc_no,adres,e_posta,pozisyon,kullanıcı_id) VALUES(?,?,?,?,?,?,?)";
            String sql1 = "INSERT INTO hesaplar (kid,hesap_no,kur,bakiye,toplam_borc,aylık_borc,faiz,temsilci_no) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql1);
            System.out.print("\nİsim Soyisim : ");
            String isim = br.readLine();
            System.out.print("\nTel NO : ");
            int tel = sc1.nextInt();
            System.out.print("\nTC NO : ");
            int tc = sc1.nextInt();
            System.out.print("\nAdres: ");
            String adres = br.readLine();
            System.out.print("\nE-Posta : ");
            String mail = br.readLine();
            System.out.print("\nPozisyon : ");
            String poz = br.readLine();
            System.out.print("\nKullanıcı ID : ");
            int id = sc1.nextInt();
            System.out.println("Lütfen hesap bilgilerini giriniz!");
            System.out.print("\nHesap NO : ");
            int hesap = sc1.nextInt();
            statement.setString(1, isim);
            statement.setInt(2, tel);
            statement.setInt(3, tc);
            statement.setString(4, adres);
            statement.setString(5, mail);
            statement.setString(6, poz);
            statement.setInt(7, id);
            statement2.setInt(1, id);
            statement2.setInt(2, hesap);
            statement2.setString(3, "TL");
            statement2.setInt(4, 0);
            statement2.setInt(5, 0);
            statement2.setInt(6, 0);
            statement2.setFloat(7, 0);
            statement2.setInt(8, temsilci_no);
            int rows = statement.executeUpdate();
            int rows2 = statement2.executeUpdate();
            veri_girisi = 0;
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Oops, error");
            e.printStackTrace();
        }
    }

    void musteri_bilgi_guncelleme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            int id = 0;
            while (true) {
                String sql = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.print("\nBilgilerini güncellemek istediğiniz kullanıcın id'sini giriniz : ");
                id = sc.nextInt();
                statement.setInt(1, id);
                String ekleme = Integer.toString(id);
                String sql1 = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                sql = sql1.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                result.next();
                int varmi = result.getInt("COUNT(kullanıcı_id)");
                result.close();
                if (varmi == 1) {
                    sql = "SELECT COUNT(kid) FROM hesaplar WHERE kid = ";
                    sql = sql.concat(ekleme);
                    sql1 = " and temsilci_no = ";
                    ekleme = Integer.toString(temsilci_no);
                    sql1 = sql1.concat(ekleme);
                    sql = sql.concat(sql1);
                    result = statement.executeQuery(sql);
                    result.next();
                    varmi = result.getInt("COUNT(kid)");
                    result.close();
                    if (varmi > 0) {
                        System.out.print("\n\nAd Soyad güncellemek için 1'e basın yoksa 0'a basın : ");
                        int secim = sc.nextInt();
                        if (secim == 1) {
                            sql = "UPDATE bilgi SET ad_soyad = ? where kullanıcı_id = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql);
                            System.out.print("\n\nYeni Ad Soyad giriniz :");
                            String isim = br.readLine();
                            statement1.setString(1, isim);
                            statement1.setInt(2, id);
                            statement1.executeUpdate();
                            statement1.close();
                        }
                        System.out.print("\n\nTel NO güncellemek için 1'e basın yoksa 0'a basın : ");
                        secim = sc.nextInt();
                        if (secim == 1) {
                            sql = "UPDATE bilgi SET tel_no = ? where kullanıcı_id = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql);
                            System.out.print("\n\nYeni Tel NO giriniz :");
                            int tel = sc1.nextInt();
                            statement1.setInt(1, tel);
                            statement1.setInt(2, id);
                            statement1.executeUpdate();
                            statement1.close();
                        }
                        System.out.print("\n\nTc NO güncellemek için 1'e basın yoksa 0'a basın : ");
                        secim = sc.nextInt();
                        if (secim == 1) {
                            sql = "UPDATE bilgi SET tc_no = ? where kullanıcı_id = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql);
                            System.out.print("\n\nYeni Tc NO giriniz :");
                            int tc = sc1.nextInt();
                            statement1.setInt(1, tc);
                            statement1.setInt(2, id);
                            statement1.executeUpdate();
                            statement1.close();
                        }
                        System.out.print("\n\nAdres güncellemek için 1'e basın yoksa 0'a basın : ");
                        secim = sc.nextInt();
                        if (secim == 1) {
                            sql = "UPDATE bilgi SET adres = ? where kullanıcı_id = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql);
                            System.out.print("\n\nYeni Adres giriniz :");
                            String adres = br.readLine();
                            statement1.setString(1, adres);
                            statement1.setInt(2, id);
                            statement1.executeUpdate();
                            statement1.close();
                        }
                        System.out.print("\n\nE-Posta güncellemek için 1'e basın yoksa 0'a basın : ");
                        secim = sc.nextInt();
                        if (secim == 1) {
                            sql = "UPDATE bilgi SET e_posta = ? where kullanıcı_id = ?";
                            PreparedStatement statement1 = connection.prepareStatement(sql);
                            System.out.print("\n\nYeni E-Posta giriniz :");
                            String mail = br.readLine();
                            statement1.setString(1, mail);
                            statement1.setInt(2, id);
                            statement1.executeUpdate();
                            statement1.close();
                        }
                        break;
                    } else {
                        System.out.println("\nBu kullanıcıya erişemezsiniz?!Lütfen tekrar giriniz...");
                    }
                } else {
                    System.out.println("\nGirilen Kullanıcı id yanlış.Lütfen tekrar giriniz...");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }

    }

    void hesap_goruntule() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT COUNT(kid) FROM hesaplar WHERE kid = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.print("\nBilgilerini görüntülemek istediğiniz kullanıcın id'sini giriniz : ");
                int id = sc.nextInt();
                statement.setInt(1, id);
                String ekleme = Integer.toString(id);
                String sql1 = "SELECT COUNT(kid) FROM hesaplar WHERE kid = ";
                sql = sql1.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                result.next();
                int varmi = result.getInt("COUNT(kid)");
                result.close();
                if (varmi > 0) {
                    sql1 = " and temsilci_no = ";
                    ekleme = Integer.toString(temsilci_no);
                    sql1 = sql1.concat(ekleme);
                    sql = sql.concat(sql1);
                    result = statement.executeQuery(sql);
                    result.next();
                    varmi = result.getInt("COUNT(kid)");
                    result.close();
                    if (varmi > 0) {
                        sql1 = "SELECT * FROM hesaplar WHERE kid = ";
                        ekleme = Integer.toString(id);
                        sql1 = sql1.concat(ekleme);
                        result = statement.executeQuery(sql1);
                        int count = 1;
                        while (result.next()) {
                            int hesap_no = result.getInt("hesap_no");
                            int bakiye = result.getInt("bakiye");
                            int toplam_borc = result.getInt("toplam_borc");
                            int aylık_borc = result.getInt("aylık_borc");
                            String kur = result.getString("kur");
                            System.out.println("\n   HESAP " + count);
                            System.out.println("\nHESAP NO : " + hesap_no);
                            System.out.println("KUR : " + kur);
                            System.out.println("BAKİYE : " + bakiye);
                            System.out.println("TOPLAM BORÇ  : " + toplam_borc);
                            System.out.println("AYLIK BORÇ : " + aylık_borc);
                            System.out.println("");
                            count++;
                        }
                        statement.close();
                        break;
                    } else {
                        System.out.println("\nBu kullanıcıya erişemezsiniz?!Lütfen tekrar giriniz...");
                    }

                } else {
                    System.out.println("\nGirilen Kullanıcı id yanlış.Lütfen tekrar giriniz...");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void islem_goruntuleme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT COUNT(kaynak) FROM özetler WHERE kaynak = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.print("Bilgilerini görüntülemek istediğiniz kullanıcın hesap no'sunu giriniz : ");
                int id = sc.nextInt();
                statement.setInt(1, id);
                String ekleme = Integer.toString(id);
                String sql1 = "SELECT COUNT(kaynak) FROM özetler WHERE kaynak = ";
                sql = sql1.concat(ekleme);
                ResultSet result = statement.executeQuery(sql);
                result.next();
                int varmi = result.getInt("COUNT(kaynak)");
                result.close();
                if (varmi > 0) {
                    sql1 = " and temsilci_no = ";
                    ekleme = Integer.toString(temsilci_no);
                    sql1 = sql1.concat(ekleme);
                    sql = sql.concat(sql1);
                    result = statement.executeQuery(sql);
                    result.next();
                    varmi = result.getInt("COUNT(kaynak)");
                    result.close();
                    if (varmi > 0) {
                        sql1 = "SELECT * FROM özetler WHERE kaynak = ";
                        ekleme = Integer.toString(id);
                        sql1 = sql1.concat(ekleme);
                        result = statement.executeQuery(sql1);
                        while (result.next()) {
                            int islem_no = result.getInt("islem_no");
                            String islem = result.getString("islem");
                            int hedef = result.getInt("hedef");
                            int tutar = result.getInt("tutar");
                            //int tarih = result.getInt("tarih");
                            System.out.println("\nİŞLEM NO : " + islem_no);
                            System.out.println("İŞLEM : " + islem);
                            System.out.println("HEDEF : " + hedef);
                            System.out.println("TUTAR : " + tutar);
                            System.out.println("");
                        }
                        statement.close();
                        break;
                    } else {
                        System.out.println("\nBu kullanıcıya erişemezsiniz?!Lütfen tekrar giriniz...");
                    }

                } else {
                    System.out.println("\nGirilen  hesap no yanlış.Lütfen tekrar giriniz...");
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void talep_onayi() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            int veri_girisi1 = 0;
            System.out.println("\nHesap açma işlemleri için 1'i,\n\nKredi onay işlemleri için 2'yi tuşlayınız,\n\nHesap silme işlemleri için 3'ü tuşlayınız.\n");
            veri_girisi1 = sc.nextInt();
            if (veri_girisi1 == 1) {
                int secim1 = 0;
                System.out.println("\nOnay vermek istiyorsanız 1'1, onay vermeyecekseniz 2'yi tuşlayınız");
                secim1 = sc.nextInt();
                if (secim1 == 1) {
                    String sql1 = "UPDATE talepler SET onay_durumu = ? where kullanıcı_id = ? ";
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    String sql2 = "INSERT INTO hesaplar (kid,hesap_no,kur,bakiye,toplam_borc,aylık_borc,faiz,gecikme_faizi,temsilci_no) VALUES(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement statement2 = connection.prepareStatement(sql2);
                    int kid = 0;
                    while (true) {
                        System.out.print("Müşteri kullanıcı id'si giriniz : ");
                        kid = sc.nextInt();
                        String ekleme = Integer.toString(kid);
                        sql1 = "SELECT COUNT(kullanıcı_id) FROM talepler WHERE kullanıcı_id = ";
                        sql1 = sql1.concat(ekleme);
                        PreparedStatement statement5 = connection.prepareStatement(sql1);
                        ResultSet result = statement5.executeQuery(sql1);
                        result.next();
                        int varmi = result.getInt("COUNT(kullanıcı_id)");
                        result.close();
                        sql1 = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                        sql1 = sql1.concat(ekleme);
                        statement5 = connection.prepareStatement(sql1);
                        result = statement5.executeQuery(sql1);
                        result.next();
                        int varmi1 = result.getInt("COUNT(kullanıcı_id)");
                        result.close();
                        if (varmi1 != 1) {
                            System.out.println("Yanlış kullanıcı id.Lütfen tekrar giriniz...");
                        } else if (varmi == 0) {
                            System.out.println("\nGirilen hesap no ya ait hesap açma talebi bulunmaktadır.Lütfen tekrar giriniz...");
                        } else {
                            break;
                        }
                    }
                    statement1.setString(1, "ONAY VERİLDİ.");
                    statement1.setInt(2, kid);
                    statement1.executeUpdate();
                    int hesap = 123;
                    while (true) {
                        System.out.println("\nLütfen hesap bilgilerini giriniz!");
                        System.out.print("\nHesap NO : ");
                        hesap = sc1.nextInt();
                        String ekleme = Integer.toString(hesap);
                        sql1 = "SELECT COUNT(hesap_no) FROM hesaplar WHERE hesap_no = ";
                        sql1 = sql1.concat(ekleme);
                        PreparedStatement statement5 = connection.prepareStatement(sql1);
                        ResultSet result = statement5.executeQuery(sql1);
                        result.next();
                        int varmi = result.getInt("COUNT(hesap_no)");
                        result.close();
                        if (varmi == 0) {
                            break;
                        } else {
                            System.out.println("\nGirilen hesap no ya ait hesap bulunmaktadır.Lütfen tekrar giriniz...");
                        }
                    }
                    String kur = "TL";
                    while (true) {
                        System.out.println("\nHangi kurdan hesap açmak istersiniz");
                        System.out.print("\nKur : ");
                        kur = br.readLine();
                        String sql = "SELECT COUNT(kur) FROM kurlar where kur = '";
                        sql = sql.concat(kur + "'");
                        PreparedStatement statement3 = connection.prepareStatement(sql);
                        ResultSet result1 = statement3.executeQuery(sql);
                        result1.next();
                        int varmi = result1.getInt("COUNT(kur)");
                        if (varmi == 1) {
                            break;
                        } else {
                            System.out.println("\nGirilen kur bulunmamaktadır.Lütfen tekrar giriniz...");
                        }
                    }
                    statement2.setInt(1, kid);
                    statement2.setInt(2, hesap);
                    statement2.setString(3, kur);
                    statement2.setInt(4, 0);
                    statement2.setInt(5, 0);
                    statement2.setInt(6, 0);
                    statement2.setFloat(7, 0);
                    statement2.setFloat(8, 0);
                    statement2.setInt(9, temsilci_no);
                    statement2.executeUpdate();
                    statement1.close();
                    statement2.close();
                } else if (secim1 == 2) {
                    String sql4 = "UPDATE talepler SET onay_durumu = ? where kullanıcı_id = ? ";
                    PreparedStatement statement4 = connection.prepareStatement(sql4);
                    int kid = 0;
                    System.out.println("Müşteri kullanıcı id'si giriniz : ");
                    kid = sc.nextInt();
                    statement4.setString(1, "REDDEDİLDİ.");
                    statement4.setInt(2, kid);
                    statement4.executeUpdate();
                    statement4.close();
                }
            } else if (veri_girisi1 == 2) {
                int kid = 0;
                while (true) {
                    System.out.print("\nMüşteri kullanıcı id'si giriniz : ");
                    kid = sc.nextInt();
                    String ekleme = Integer.toString(kid);
                    String sql = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                    sql = sql.concat(ekleme);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet result = statement.executeQuery(sql);
                    result.next();
                    int varmi1 = result.getInt("COUNT(kullanıcı_id)");
                    result.close();
                    sql = "SELECT COUNT(kullanıcı_id) FROM talepler WHERE kullanıcı_id = ";
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery(sql);
                    result.next();
                    int varmi = result.getInt("COUNT(kullanıcı_id)");
                    result.close();
                    if (varmi1 != 1) {
                        System.out.println("Yanlış kullanıcı id.Lütfen tekrar giriniz...");
                    } else if (varmi == 0) {
                        System.out.println("\nGirilen hesap no ya ait hesap açma talebi bulunmaktadır.Lütfen tekrar giriniz...");
                    } else {
                        while (true) {
                            System.out.print("Müşteri hesap no giriniz : ");
                            int hesap_no = sc.nextInt();
                            String ekleme1 = Integer.toString(kid);
                            String sql0 = "SELECT COUNT(kullanıcı_id) FROM talepler WHERE kullanıcı_id = ";
                            sql0 = sql0.concat(ekleme1);
                            ekleme1 = Integer.toString(hesap_no);
                            String sql1 = " and hesap_no = ";
                            sql1 = sql1.concat(ekleme1);
                            sql0 = sql0.concat(sql1);
                            String sql2 = sql0;
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            varmi = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            sql1 = " and (onay_durumu = 'REDDEDİLDİ.' or onay_durumu = 'ONAY VERİLDİ.')";
                            sql0 = sql0.concat(sql1);
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            int varmi2 = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            sql0 = "SELECT COUNT(hesap_no) FROM hesaplar WHERE hesap_no = ";
                            sql0 = sql0.concat(ekleme1);
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            varmi1 = result.getInt("COUNT(hesap_no)");
                            result.close();

                            sql0 = " and talep_türü = 'Kredi Talebi'";
                            sql2 = sql2.concat(sql0);
                            statement = connection.prepareStatement(sql2);
                            result = statement.executeQuery(sql2);
                            result.next();
                            int varmi3 = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            if (varmi1 == 0) {
                                System.out.println("\nGirilen hesap no ya ait hesap yoktur.Lütfen tekrar giriniz...");
                            } else if (varmi == 0) {
                                System.out.println("\nGirilen hesap no ya ait talep bulunamamaktadır.Lütfen tekrar giriniz...");
                            } else if (varmi2 == 1) {
                                System.out.println("\nBu talep önceden onaylanmış veya reddilmiştir.Lütfen tekrar giriniz...");
                            } else if (varmi3 == 0) {
                                System.out.println("\nGirilen hesap no ya ait kredi talebi bulunmamaktadır.Lütfen tekrar giriniz...");
                            } else {
                                sql = "SELECT * FROM hesaplar WHERE hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                result = statement.executeQuery(sql);
                                result.next();
                                int bakiye = result.getInt("bakiye");
                                int borc = result.getInt("toplam_borc");
                                System.out.println("\nHesap NO : " + hesap_no);
                                System.out.println("Toplam Bakiye : " + bakiye);
                                System.out.println("Toplam Borç : " + borc);
                                sql = "SELECT * FROM talepler WHERE hesap_no = ";
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                result = statement.executeQuery(sql);
                                result.next();
                                int miktar = result.getInt("miktar");
                                int vade = result.getInt("vade");
                                System.out.println("\nTalep edilen Miktar : " + miktar);
                                System.out.println("Talep edilen Vade : " + vade);
                                System.out.print("\nOnaylamak isterseniz 1 e Reddedmek isterseniz 0 a basınız : ");
                                veri_girisi = sc.nextInt();
                                if (veri_girisi == 1) {
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "ONAY VERİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                } else {
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "REDDEDİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            } else if (veri_girisi1 == 3) {
                int kid = 0;
                while (true) {
                    System.out.print("\nMüşteri kullanıcı id'si giriniz : ");
                    kid = sc.nextInt();
                    String ekleme = Integer.toString(kid);
                    String sql = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                    sql = sql.concat(ekleme);
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet result = statement.executeQuery(sql);
                    result.next();
                    int varmi1 = result.getInt("COUNT(kullanıcı_id)");
                    result.close();
                    sql = "SELECT COUNT(kullanıcı_id) FROM talepler WHERE kullanıcı_id = ";
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    result = statement.executeQuery(sql);
                    result.next();
                    int varmi = result.getInt("COUNT(kullanıcı_id)");
                    result.close();
                    if (varmi1 != 1) {
                        System.out.println("\nYanlış kullanıcı id.Lütfen tekrar giriniz...");
                    } else if (varmi == 0) {
                        System.out.println("\nGirilen hesap no ya ait hesap açma talebi bulunmaktadır.Lütfen tekrar giriniz...");
                    } else {
                        while (true) {
                            System.out.print("\nMüşteri hesap no giriniz : ");
                            int hesap_no = sc.nextInt();
                            String ekleme1 = Integer.toString(kid);
                            String sql0 = "SELECT COUNT(kullanıcı_id) FROM talepler WHERE kullanıcı_id = ";
                            sql0 = sql0.concat(ekleme1);
                            ekleme1 = Integer.toString(hesap_no);
                            String sql1 = " and hesap_no = ";
                            sql1 = sql1.concat(ekleme1);
                            sql0 = sql0.concat(sql1);
                            String sql2 = sql0;
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            varmi = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            sql1 = " and (onay_durumu = 'REDDEDİLDİ.' or onay_durumu = 'ONAY VERİLDİ.')";
                            sql0 = sql0.concat(sql1);
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            int varmi2 = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            sql0 = "SELECT COUNT(hesap_no) FROM hesaplar WHERE hesap_no = ";
                            sql0 = sql0.concat(ekleme1);
                            statement = connection.prepareStatement(sql0);
                            result = statement.executeQuery(sql0);
                            result.next();
                            varmi1 = result.getInt("COUNT(hesap_no)");
                            result.close();

                            sql0 = " and talep_türü = 'Hesap Silme'";
                            sql2 = sql2.concat(sql0);
                            statement = connection.prepareStatement(sql2);
                            result = statement.executeQuery(sql2);
                            result.next();
                            int varmi3 = result.getInt("COUNT(kullanıcı_id)");
                            result.close();

                            if (varmi1 == 0) {
                                System.out.println("\nGirilen hesap no ya ait hesap yoktur.Lütfen tekrar giriniz...");
                            } else if (varmi == 0) {
                                System.out.println("\nGirilen hesap no ya ait talep bulunamamaktadır.Lütfen tekrar giriniz...");
                            } else if (varmi2 == 1) {
                                System.out.println("\nBu talep önceden onaylanmış veya reddilmiştir.Lütfen tekrar giriniz...");
                            } else if (varmi3 == 0) {
                                System.out.println("\nGirilen hesap no ya ait Hesap Silme talebi bulunmamaktadır.Lütfen tekrar giriniz...");
                            } else {
                                sql = "SELECT * FROM hesaplar WHERE hesap_no = ";
                                ekleme = Integer.toString(hesap_no);
                                sql = sql.concat(ekleme);
                                statement = connection.prepareStatement(sql);
                                result = statement.executeQuery(sql);
                                result.next();
                                int bakiye = result.getInt("bakiye");
                                int borc = result.getInt("toplam_borc");
                                System.out.println("\nHesap NO : " + hesap_no);
                                System.out.println("Toplam Bakiye : " + bakiye);
                                System.out.println("Toplam Borç : " + borc);
                                System.out.print("\nOnaylamak isterseniz 1 e Reddedmek isterseniz 0 a basınız : ");
                                veri_girisi = sc.nextInt();
                                if (bakiye == 0 && borc == 0 && veri_girisi == 1) {
                                    String sql3 = "DELETE FROM hesaplar where hesap_no = ";
                                    ekleme = Integer.toString(hesap_no);
                                    sql3 = sql3.concat(ekleme);
                                    PreparedStatement statement2 = connection.prepareStatement(sql3);
                                    statement2.executeUpdate();
                                    statement2.close();
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "ONAY VERİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                    System.out.println(ekleme + " Hesap numaralı hesabınız silinmiştir...");
                                } else if (bakiye == 0 && borc == 0 && veri_girisi == 0) {
                                    String sql3 = "DELETE FROM hesaplar where hesap_no = ";
                                    ekleme = Integer.toString(hesap_no);
                                    sql3 = sql3.concat(ekleme);
                                    PreparedStatement statement2 = connection.prepareStatement(sql3);
                                    statement2.executeUpdate();
                                    statement2.close();
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "REDDEDİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                    System.out.println(ekleme + "Hesap numaralı hesabınız silinmiştir...");
                                } else if (bakiye == 0 && veri_girisi == 0) {
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "REDDEDİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                    System.out.println("\nNereye kaçıyorsunuz? Bankaya borçlusunuz amk!!");
                                    System.out.println("Toplam Borcunuz : " + borc);
                                } else if (borc == 0 && veri_girisi == 0) {
                                    sql = "UPDATE talepler SET onay_durumu = ? where hesap_no = ? ";
                                    statement = connection.prepareStatement(sql);
                                    statement.setString(1, "REDDEDİLDİ.");
                                    statement.setInt(2, hesap_no);
                                    statement.executeUpdate();
                                    statement.close();
                                    System.out.println("\nHeyyyyy.Paraları almadan nereye!! Bize uyar gerçi :D");
                                    System.out.println("\nToplam Bakiyeniz : " + bakiye);
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }
}
