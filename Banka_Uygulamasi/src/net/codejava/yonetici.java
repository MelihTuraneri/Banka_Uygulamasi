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
import java.sql.Date;

/**
 *
 * @author melih
 */
public class yonetici {

    String url = "jdbc:mysql://localhost:3306/banka";
    String username = "root";
    String password = "Mysql_sifresi123";
    Scanner sc = new Scanner(System.in);
    Scanner sc1 = new Scanner(System.in);
    InputStreamReader ir = new InputStreamReader(System.in);
    BufferedReader br = new BufferedReader(ir);
    int veri_girisi = 0;
    int kullanıcı_id;
    int temsilci_maas;

    yonetici() {
    }

    void kur_güncelle() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO kurlar (kur, karsılıgı) VALUES(?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.print("\nKur eklemek istiyorsanız 1'e basın yoksa 0'a basın : ");
            veri_girisi = sc.nextInt();
            if (veri_girisi != 0) {
                System.out.println("Para birimi giriniz : ");
                String birim = br.readLine();
                System.out.println("Güncel değerini giriniz : ");
                float deger = sc1.nextFloat();
                statement.setString(1, birim);
                statement.setFloat(2, deger);
                statement.executeUpdate();
            }
            System.out.print("\nKur güncellemek istiyorsanız 1'e basın yoksa 0'a basın : ");
            veri_girisi = sc.nextInt();
            if (veri_girisi != 0) {
                String sql1 = "UPDATE kurlar SET karsılıgı = ? where kur= ?";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                System.out.println("Güncellemek istediğiniz Kur'u giriniz : ");
                String birim = br.readLine();
                System.out.println("Güncel değeri giriniz : ");
                float karsılıgı = sc1.nextFloat();
                statement1.setFloat(1, karsılıgı);
                statement1.setString(2, birim);
                statement1.executeUpdate();
                statement1.close();
            }
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }

    }

    void genel_durum_görüntüle() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            while (true) {
                String sql = "SELECT SUM(aylık_borc) FROM hesaplar";
                PreparedStatement statement = connection.prepareStatement(sql);
                String sql1 = "SELECT SUM(toplam_borc) FROM hesaplar";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                String sql2 = "SELECT SUM(aylık_borc) - SUM(toplam_borc) FROM hesaplar";
                PreparedStatement statement2 = connection.prepareStatement(sql2);
                String sql3 = "SELECT SUM(bakiye) FROM hesaplar";
                PreparedStatement statement3 = connection.prepareStatement(sql3);
                ResultSet result = statement.executeQuery(sql);
                int gelir = 0;
                System.out.println(" \n---BANKA GENEL DURUMU---\n ");
                while (result.next()) {
                    gelir = result.getInt("SUM(aylık_borc)");
                    System.out.println("Gelir Durumu : " + gelir);
                    break;
                }
                ResultSet result1 = statement1.executeQuery(sql1);
                int gider = 0;
                while (result1.next()) {
                    gider = result1.getInt("SUM(toplam_borc)");
                    System.out.println("Gider Durumu : " + gider);
                    break;
                }
                ResultSet result2 = statement2.executeQuery(sql2);
                int kar = 0;
                while (result2.next()) {
                    kar = result2.getInt("SUM(aylık_borc) - SUM(toplam_borc)");
                    System.out.println("Kar Durumu : " + kar);
                    break;
                }
                ResultSet result3 = statement3.executeQuery(sql3);
                float toplam_bakiye = 0;
                while (result3.next()) {
                    toplam_bakiye = result3.getInt("SUM(bakiye)");
                    System.out.println("Banka Toplam Bakiyesi : " + toplam_bakiye);
                    break;
                }
                statement.close();
                break;
            }
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void maas_belirle() {
        System.out.print("\nMaaş giriniz : ");
        temsilci_maas = sc.nextInt();
    }

    void islem_goruntule() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.print("\nSondan görüntülemek istediğiniz işlem sayısını giriniz : ");
            veri_girisi = sc.nextInt();
            String sql = "SELECT * FROM özetler WHERE islem_no > (SELECT MAX(islem_no) - ";
            String sql1 = " FROM özetler)";
            String ekleme = Integer.toString(veri_girisi);
            sql = sql.concat(ekleme);
            sql = sql.concat(sql1);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int islem_no = result.getInt("islem_no");
                String islem = result.getString("islem");
                int kaynak = result.getInt("kaynak");
                int hedef = result.getInt("hedef");
                int tutar = result.getInt("tutar");
                int kaynak_bakiye = result.getInt("kaynak_bakiye");
                int hedef_bakiye = result.getInt("hedef_bakiye");
                Date tarih = result.getDate("tarih");
                System.out.println("\nİŞLEM NO : " + islem_no);
                System.out.println("İŞLEM : " + islem);
                System.out.println("KAYNAK : " + kaynak);
                System.out.println("HEDEF : " + hedef);
                System.out.println("KAYNAK BAKİYE : " + kaynak_bakiye);
                System.out.println("HEDEF BAKİYE : " + hedef_bakiye);
                System.out.println("TUTAR : " + tutar);
                System.out.println("TARİH : " + tarih);
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }

    }

    void faiz_belirle() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            int kid = 0;
            while (true) {
                System.out.print("Müşteri hesap no giriniz : ");
                int hesap_no = sc.nextInt();
                String sql0 = "SELECT COUNT(kullanıcı_id) FROM talepler where hesap_no = ";
                String ekleme = Integer.toString(hesap_no);
                sql0 = sql0.concat(ekleme);
                String sql2 = sql0;
                PreparedStatement statement = connection.prepareStatement(sql0);
                ResultSet result = statement.executeQuery(sql0);
                result.next();
                int varmi = result.getInt("COUNT(kullanıcı_id)");
                result.close();

                String sql1 = " and onay_durumu = 'ONAY VERİLDİ.'";
                sql0 = sql0.concat(sql1);
                statement = connection.prepareStatement(sql0);
                result = statement.executeQuery(sql0);
                result.next();
                int varmi2 = result.getInt("COUNT(kullanıcı_id)");
                result.close();

                sql0 = "SELECT COUNT(hesap_no) FROM hesaplar WHERE hesap_no = ";
                sql0 = sql0.concat(ekleme);
                statement = connection.prepareStatement(sql0);
                result = statement.executeQuery(sql0);
                result.next();
                int varmi1 = result.getInt("COUNT(hesap_no)");
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
                } else if (varmi2 != 1) {
                    System.out.println("\nBu talep önceden onaylanmamış veya reddilmiştir.Lütfen tekrar giriniz...");
                } else if (varmi3 == 0) {
                    System.out.println("\nGirilen hesap no ya ait kredi talebi bulunmamaktadır.Lütfen tekrar giriniz...");
                } else {
                    String sql = "SELECT * FROM hesaplar WHERE hesap_no = ";
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
                    System.out.print("\nFaiz : ");
                    float faiz = sc1.nextFloat();
                    System.out.print("Gecikme Faiz : ");
                    float gecikme_faizi = sc1.nextFloat();
                    sql1 = "UPDATE hesaplar SET faiz = ?,gecikme_faizi = ?,bakiye = ?,toplam_borc = ?,aylık_borc = ? where hesap_no = ?";
                    PreparedStatement statement1 = connection.prepareStatement(sql1);
                    statement1.setFloat(1, faiz);
                    statement1.setFloat(2, gecikme_faizi);
                    statement1.setInt(3, bakiye + miktar);
                    statement1.setInt(4, borc + miktar + (int) (miktar * faiz));
                    statement1.setInt(5, (miktar + (int) (miktar * faiz)) / vade);
                    statement1.setInt(6, hesap_no);
                    statement1.executeUpdate();
                    sql = "DELETE FROM talepler where hesap_no = ";
                    sql = sql.concat(ekleme);
                    statement1 = connection.prepareStatement(sql);
                    statement1.executeUpdate();
                    statement1.close();
                    break;
                }
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void musteri_ekle() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO bilgi (ad_soyad,tel_no,tc_no,adres,e_posta,pozisyon,kullanıcı_id) VALUES(?,?,?,?,?,?,?)";
            String sql1 = "INSERT INTO hesaplar (kid,hesap_no,kur,bakiye,toplam_borc,aylık_borc,faiz,gecikme_faizi,temsilci_no) VALUES(?,?,?,?,?,?,?,?,?)";
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
            System.out.println("\nLütfen hesap bilgilerini giriniz!");
            System.out.print("\nHesap NO : ");
            int hesap = sc1.nextInt();
            int temsilci_no = 0;
            while (true) {
                System.out.println("\nLütfen temsilci atayınız!");
                System.out.print("\nTemsilci NO : ");
                temsilci_no = sc1.nextInt();
                sql = "SELECT COUNT(temsilci_no) FROM bilgi where temsilci_no = ";
                String ekleme = Integer.toString(temsilci_no);
                sql = sql.concat(ekleme);
                PreparedStatement statement3 = connection.prepareStatement(sql);
                ResultSet result1 = statement3.executeQuery(sql);
                result1.next();
                int varmi = result1.getInt("COUNT(temsilci_no)");
                if (varmi == 1) {
                    break;
                } else {
                    System.out.println("\nBu temsilci no ya ait temsilci bulunamamıştır.Lütfen tekrar giriniz...");
                }
            }
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
            statement2.setFloat(8, 0);
            statement2.setInt(9, temsilci_no);
            statement.executeUpdate();
            statement2.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("\nOops, error");
            e.printStackTrace();
        }
    }

    void yasama_yürütme() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT * FROM hesaplar WHERE kur = 'TL'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int hesap_no = result.getInt("hesap_no");
                int temsilci_no = result.getInt("temsilci_no");
                int aylık_borc = result.getInt("aylık_borc");
                int toplam_borc = result.getInt("toplam_borc");
                String kur = result.getString("kur");
                int para = result.getInt("bakiye");
                float gecikme_faizi = result.getFloat("gecikme_faizi");
                int gecikme_borcu = result.getInt("gecikme_borcu");
                if (toplam_borc == 0) {
                } else if (para >= aylık_borc + gecikme_borcu) {
                    String str = "2015-03-31";
                    Date date = Date.valueOf(str);
                    sql = "UPDATE hesaplar SET bakiye = ?,toplam_borc = ?,aylık_borc = ?,gecikme_borcu = ? where hesap_no = ";
                    String ekleme = Integer.toString(hesap_no);
                    sql = sql.concat(ekleme);
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, para - aylık_borc - gecikme_borcu);
                    statement.setInt(2, toplam_borc - aylık_borc - gecikme_borcu);
                    if (toplam_borc - aylık_borc == 0) {
                        statement.setInt(3, 0);
                    } else {
                        statement.setInt(3, aylık_borc);
                    }
                    statement.setInt(4, 0);
                    statement.executeUpdate();
                    statement.close();
                    sql = "INSERT INTO özetler (kaynak,hedef,islem,tutar,kaynak_bakiye,tarih,temsilci_no) VALUES(?,?,?,?,?,?,?)";
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, hesap_no);
                    statement.setInt(2, 99999);
                    statement.setString(3, "Borç Ödeme");
                    statement.setInt(4, aylık_borc + gecikme_borcu);
                    statement.setInt(5, para);
                    statement.setDate(6, date);
                    statement.setInt(7, temsilci_no);
                    statement.executeUpdate();
                } else {
                    String str = "2015-03-31";
                    Date date = Date.valueOf(str);
                    sql = "UPDATE hesaplar SET bakiye = ?,toplam_borc = ?,aylık_borc = ?,gecikme_borcu = ? where hesap_no = ";
                    String ekleme = Integer.toString(hesap_no);
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

}
