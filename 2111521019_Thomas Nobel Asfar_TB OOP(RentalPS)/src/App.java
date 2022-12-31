import java.util.Scanner;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap; 

//class App;
public class App {

    static Connection con;

    //constructor class App
    public App(){

    }

    public static void main(String[] args){
    String url = "jdbc:mysql://localhost:3306/ps";
    //exception
    try {
        //collection framework hashmap
        HashMap<String, String> admin = new HashMap<String, String>();
        admin.put("thomas", "Thomas");
        admin.put("malik", "Malik");
        admin.put("sasuke", "Sasuke");
    
        Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url,"root","");
        Scanner i = new Scanner(System.in);
        LocalDateTime myDateObj = LocalDateTime.now();//set tanggal dan waktu secara realtime

        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        
        boolean lanjut = true;
        Penjualan j = new Penjualan();
        String iadm;
		System.out.println("Driver Ready");
        System.out.print("Admin     : ");
        //method string lowercase
        iadm = i.nextLine().toLowerCase();
        //percabangan
        if(admin.get(iadm)!=null){
            //looping
                do{
                    Clean.clearScreen();
                    System.out.println("\n\n==================================");
                    System.out.println("|              MENU               |");
                    System.out.println("==================================");
                    System.out.println("1. Lihat Riwayat Data Penjualan");
                    System.out.println("2. Tambah Data Penjualan");
                    System.out.println("3. Edit Data Penjualan");
                    System.out.println("4. Hapus Data Penjualan");
                    System.out.println("5. Cari Data Penjualan");
                    System.out.print("no : ");
                    int menu = Integer.parseInt(i.nextLine());
                    if (menu==1){
                        Clean.clearScreen();
                        j.riwayatPenjualan(iadm);
                    }
                    else if(menu==2){
                       Clean.clearScreen();
                        j.tambahPenjualan(iadm);
                    }
                    else if(menu==3){
                        Clean.clearScreen();
                       j.editPenjualan(iadm);
                    }
                    else if(menu==4){
                        Clean.clearScreen();
                        j.hapusPenjualan(iadm);
                     }
                    else if(menu==5){
                        Clean.clearScreen();
                        j.cariPenjualan(iadm);
                     }    
                    else{
                        Clean.clearScreen();
                        System.out.println("Menu tidak tersedia");
                    }             
                    System.out.print("\n\nKembali? (y/n) : ");
                    String y = i.nextLine();
                    lanjut = y.equalsIgnoreCase("y");
                }while(lanjut); 
            }
                     
            else{
                System.out.println("Akun tidak tersedia. Program hanya dapat diakses oleh admin yang terdaftar");         
            }
        System.out.println("\nTerima kasih telah menggunakan program ini! \nThomas Nobel Asfar, "+formattedDate);
        i.close();
    }
    catch (ClassNotFoundException ex) {
        System.err.println("Driver eror");
        System.exit(0);
    }
    catch(SQLException e){
        System.err.println("Tidak berhasil Koneksi");
    }
    }
}
