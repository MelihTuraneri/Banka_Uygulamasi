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
public class Kışla {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/banka";
        String username = "root";
        String password = "Mysql_sifresi123";
        Scanner sc = new Scanner(System.in);
        int kullanıcı_id = 0;
        int temsilci_no = 0;
        int kullanıcı_id1 = 0;
        while (true) {
            System.out.println("\nMusteri girişi için 1 i tuşlayınız!!");
            System.out.println("\nTemsilci girişi için 2 yi tuşlayınız!!");
            System.out.println("\nYönetici girişi için 3 ü tuşlayınız!!");
            System.out.println("\nÇıkış için 4 ü tuşlayınız!!\n");
            int secim = sc.nextInt();
            if (secim == 1) {
                try {
                    while (true) {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        System.out.print("\nKullanıcı ID'nizi giriniz : ");
                        kullanıcı_id1 = sc.nextInt();
                        String sql = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        String ekleme = Integer.toString(kullanıcı_id1);
                        sql = sql.concat(ekleme);
                        ResultSet result = statement.executeQuery(sql);
                        result.next();
                        int varmi = result.getInt("COUNT(kullanıcı_id)");
                        result.close();
                        if (varmi == 1) {
                            System.out.println("\n    HOŞGELDİNİZ!!");
                            System.out.println("\nBilgi güncellemek için 1'i");
                            System.out.println("Para çekmek için 2'yi");
                            System.out.println("Para yatirmak için 3'ü");
                            System.out.println("Para trasnfer etmek için 4'ü");
                            System.out.println("Aylık kredi borcunu yatırmak için 5'i");
                            System.out.println("Toplam kredi borcunu ödemek için 6'yı");
                            System.out.println("Özet görüntülemek için 7'yi");
                            System.out.print("Talepte bulunmak için 8'i     tuşlayınız : ");
                            musteri musteri1 = new musteri(kullanıcı_id1);
                            int secim5 = sc.nextInt();
                            if (secim5 == 1) {
                                musteri1.musteri_bilgi_guncelleme();
                            } else if (secim5 == 2) {
                                musteri1.para_cekme();
                            } else if (secim5 == 3) {
                                musteri1.para_yatirma();
                            } else if (secim5 == 4) {
                                musteri1.para_transferi();
                            } else if (secim5 == 5) {
                                musteri1.aylik_kredi_borcu();
                            } else if (secim5 == 6) {
                                musteri1.toplam_kredi_borcu();
                            } else if (secim5 == 7) {
                                musteri1.özet();
                            } else if (secim5 == 8) {
                                musteri1.müşteri_talepleri();
                            } else {
                                System.out.println("\nYanlış Giriş yaptın");
                            }
                            break;
                        } else {
                            System.out.print("\nGirilen Kullanıcı ID yanlış.Lütfen tekrar giriniz...");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("\nOops, error");
                    e.printStackTrace();
                }
            } else if (secim == 2) {
                try {
                    while (true) {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        System.out.print("\nKullanıcı ID giriniz : ");
                        kullanıcı_id = sc.nextInt();
                        System.out.print("\nTemsilci NO giriniz : ");
                        temsilci_no = sc.nextInt();
                        String sql1 = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ? and temsilci_no = ?";
                        PreparedStatement statement = connection.prepareStatement(sql1);
                        statement.setInt(1, kullanıcı_id);
                        statement.setInt(1, temsilci_no);
                        String ekleme = Integer.toString(kullanıcı_id);
                        String ekleme2 = Integer.toString(temsilci_no);
                        String sql2 = "SELECT COUNT(kullanıcı_id) FROM bilgi WHERE kullanıcı_id = ";
                        sql2 = sql2.concat(ekleme);
                        String sql3 = " and temsilci_no = ";
                        sql3 = sql3.concat(ekleme2);
                        sql1 = sql2.concat(sql3);
                        ResultSet result = statement.executeQuery(sql1);
                        result.next();
                        int varmi = result.getInt("COUNT(kullanıcı_id)");
                        result.close();
                        if (varmi == 1) {
                            System.out.println("\n           HOŞGELDİNİZ :D");
                            System.out.println("\nMusteri eklemek için 1'i");
                            System.out.println("Musteri bilgisini güncellemek 2'yi");
                            System.out.println("Hesap görüntülemek için 3'ü");
                            System.out.println("İşlem görüntülemek için 4'ü");
                            System.out.print("Talep onaylamak için 5'i    tuşlayınız : ");
                            int secim5 = sc.nextInt();
                            temsilci temsilci1 = new temsilci(kullanıcı_id, temsilci_no);
                            if (secim5 == 1) {
                                temsilci1.musteri_ekleme();
                            } else if (secim5 == 2) {
                                temsilci1.musteri_bilgi_guncelleme();
                            } else if (secim5 == 3) {
                                temsilci1.hesap_goruntule();
                            } else if (secim5 == 4) {
                                temsilci1.islem_goruntuleme();
                            } else if (secim5 == 5) {
                                temsilci1.talep_onayi();
                            } else {
                                System.out.println("\nYanlış Giriş yaptın");
                            }
                            break;
                        } else {
                            System.out.println("\nGirilen Kullanıcı ID veya Temsilci NO yanlış.Lütfen tekrar giriniz...");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("\nOops, error");
                    e.printStackTrace();
                }
            } else if (secim == 3) {
                System.out.println("\n           HOŞGELDİNİZ MÜDÜRÜM :D");
                System.out.println("\nKur güncellemek için 1'i");
                System.out.println("Genel durumu görüntülemek için 2'yi");
                System.out.println("Temsilci maaşını belirlemek için 3'ü");
                System.out.println("Kredi faizlerini belirlemek için 4'ü");
                System.out.println("Müşteri ekleme için 5'i");
                System.out.println("İşlem görüntülemek için 6'yı");
                System.out.print("Yasama yürütme yargı için 7'yi       tuşlayınız : ");
                int secim5 = sc.nextInt();
                yonetici yonetici1 = new yonetici();
                if (secim5 == 1) {
                    yonetici1.kur_güncelle();
                } else if (secim5 == 2) {
                    yonetici1.genel_durum_görüntüle();
                } else if (secim5 == 3) {
                    yonetici1.maas_belirle();
                } else if (secim5 == 4) {
                    yonetici1.faiz_belirle();
                } else if (secim5 == 5) {
                    yonetici1.musteri_ekle();
                } else if (secim5 == 6) {
                    yonetici1.islem_goruntule();
                } else if (secim5 == 7) {
                    yonetici1.yasama_yürütme();
                } else {
                    System.out.println("\nYanlış Giriş yaptın");
                }
                System.out.print("\nTemsilci maaş görüntülemek için 1 i yoksa 0 ı tuşlayınız : ");
                int secim2 = sc.nextInt();
                if (secim2 == 1) {
                    System.out.println("\nTemsilci Maasşları : " + yonetici1.temsilci_maas);
                }
                break;
            } else if (secim == 4) {
                System.out.println("\nİyi Günler Dileriz!!");
                break;
            }
        }
    }
}
