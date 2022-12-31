import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Penjualan extends App implements Ps{

    static Connection con;
    Scanner i = new Scanner(System.in);
    //method date
    LocalDateTime myDateObj = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    float rental;
    float rentalps3=0;
    float rentalps4=0;
    float rentalps5=0;

    int harga, no, ino;
    String admin, pelanggan, tgl,url,sql;
    
    //contructor class Penjualan
    public Penjualan(){

    }

    @Override
    public int noNota(int iharga) throws SQLException {
        try{
            url = "jdbc:mysql://localhost:3306/ps";
            con = DriverManager.getConnection(url, "root", "");
            String sql = "SELECT MAX(No) FROM rental";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if(result.next()){
                int max = result.getInt(1);
                if(max>0){
                    no = max + 1;
                } else{
                    no = 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Penomoran Gagal");
            System.out.println(e.getMessage());
        }
        return no;  
    }

    @Override
    public String admin(String iadm) {
        admin = iadm;
        return admin;
    }

    @Override
    public String pelanggan(String ipelanggan) {
        pelanggan = ipelanggan;
        return pelanggan;
    }

    @Override
    public int harga(int iharga) {
        harga = iharga;
        return harga;
    }

    @Override
    public String tanggal(int iharga) {
        tgl = myDateObj.format(myFormatObj);
        return tgl;
    }
    //prioses hitung rental
    //ps3 Rp5000/jam
    @Override
    public float ps3(int iharga) {
        rental = ((float)iharga/5000)*60;
        return rental;
    }
    //ps4 Rp8000/jam
    @Override
    public float ps4(int iharga) {
        rental = ((float)iharga/8000)*60;
        return rental;
    }
    //ps5 Rp10000/jam
    @Override
    public float ps5(int iharga) {
        rental = ((float)iharga/10000)*60;
        return rental;
    }


    //create data
    public void tambahPenjualan(String iadm) throws SQLException{
        //exception
        try {
            url = "jdbc:mysql://localhost:3306/ps";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            System.out.print("\n---TAMBAH DATA RENTAL PS---");
            System.out.println("\n1. PS3\n2. PS4\n3. PS5");
            System.out.print("Jenis: ");
            int jenis = i.nextInt();
            System.out.print("Nama :");
            String ipelanggan = i.next();
            System.out.print("Harga : ");
            int iharga = i.nextInt();
            if(jenis==1){
                rentalps3 = ps3(iharga);
                rentalps4 = 0;
                rentalps5 = 0;
                sql = "INSERT INTO rental (No, Tanggal, Admin, Pelanggan, Waktu_ps3, Waktu_ps4, Waktu_ps5, Total_Harga) VALUES ('"+noNota(iharga)+"','"+tanggal(iharga)+"','"+admin(iadm)+"','"+pelanggan(ipelanggan)+"','"+rentalps3+"','"+rentalps4+"','"+rentalps5+"','"+harga(iharga)+"')";                   
                statement.executeUpdate(sql);     
                System.out.println("Data dengan nomor nota "+no+" berhasil ditambahkan dengan waktu "+rental+ " menit!");            
            }
            else if(jenis==2){
                rentalps4 = ps4(iharga);    
                // rentalps3 = 0;
                // rentalps5 = 0;
                sql = "INSERT INTO rental (No, Tanggal, Admin, Pelanggan, Waktu_ps3, Waktu_ps4, Waktu_ps5, Total_Harga) VALUES ('"+noNota(iharga)+"','"+tanggal(iharga)+"','"+admin(iadm)+"','"+pelanggan(ipelanggan)+"','"+rentalps3+"','"+rentalps4+"','"+rentalps5+"','"+harga(iharga)+"')";                   
                statement.executeUpdate(sql);     
                System.out.println("Data dengan nomor nota "+no+" berhasil ditambahkan dengan waktu "+rental+ " menit!" );        
            }   
            else if(jenis==3)
            {
                rentalps5 = ps5(iharga);
                // rentalps3 = 0;
                // rentalps4 = 0;
                sql = "INSERT INTO rental (No, Tanggal, Admin, Pelanggan, Waktu_ps3, Waktu_ps4, Waktu_ps5, Total_Harga) VALUES ('"+noNota(iharga)+"','"+tanggal(iharga)+"','"+admin(iadm)+"','"+pelanggan(ipelanggan)+"','"+rentalps3+"','"+rentalps4+"','"+rentalps5+"','"+harga(iharga)+"')";                   
                statement.executeUpdate(sql);     
                System.out.println("Data dengan nomor nota "+no+" berhasil ditambahkan dengan waktu "+rental+ " menit!");
            
            }
            else{
                System.out.println("Jenis Rental PS tidak tersedia");
            }
        }
        catch (SQLException e) {
            System.out.println("Data gagal ditambahkan!");
            System.err.println(e.getMessage());
	    }
    }


    //select or view data
    public void riwayatPenjualan(String iadm) throws SQLException{
        //exception
        try{
            url = "jdbc:mysql://localhost:3306/ps";
            con = DriverManager.getConnection(url,"root","");
            String sqlc ="SELECT COUNT(*) AS total FROM rental";
            Statement statementc = con.createStatement();
            ResultSet resultc = statementc.executeQuery(sqlc);
            
            if(resultc.next()){
                int jml = resultc.getInt("total");
                if(jml>0){
                    String sql ="SELECT * FROM rental";
                    Statement statement = con.createStatement();
                    ResultSet result = statement.executeQuery(sql);
                    System.out.println("\n\n---RIWAYAT TRANSAKSI PENJUALAN---");
                    while (result.next()){
                        System.out.print("\nNo Nota  : ");
                        System.out.print(result.getInt("No"));
                        System.out.print("\nAdmin      : ");                    
                        System.out.print(result.getString("Admin"));
                        System.out.print("\nTanggal    : ");
                        System.out.print(result.getString("Tanggal"));
                        System.out.print("\nPelanggan  : ");
                        System.out.print(result.getString("Pelanggan"));
                        System.out.print("\nJenis      : ");
                        if(result.getInt("Waktu_ps3")!=0)
                        {
                            System.out.print("PS3");
                        }
                        else if(result.getInt("Waktu_ps4")!=0)
                        {
                            System.out.print("PS4");
                        }
                        else if(result.getInt("Waktu_ps5")!=0)
                        {
                            System.out.print("PS5");
                        }
                        System.out.print("\nJumlah     : ");
                        if(result.getInt("Waktu_ps3")!=0)
                        {
                            System.out.print(result.getInt("Waktu_ps3")+" Menit");
                        }
                        else if(result.getInt("Waktu_ps4")!=0)
                        {
                            System.out.print(result.getInt("Waktu_ps4")+" Menit");
                        
                        }
                        else if(result.getInt("Waktu_ps5")!=0)
                        {
                            System.out.print(result.getInt("Waktu_ps5")+" Menit");
                        }
                        System.out.print("\nHarga      : ");                    
                        System.out.print(result.getInt("Total_Harga"));
                        System.out.println("\n");
                        }
                }
                else{
                    System.out.println("Database kosong");
                }
            }
        }
        catch (SQLException e) {
	        System.err.println("Terjadi kesalahan dalam menampilkan data");
            System.err.println(e.getMessage());
	    }
    }

    //edit or update data
    public void editPenjualan(String iadm) throws SQLException{
        //exception
        try {
            riwayatPenjualan(iadm);
            System.out.print("Masukkan nomor nota yang akan diedit : ");
            no = i.nextInt();
            String sql = "SELECT * FROM rental WHERE NO = " +no;
            url = "jdbc:mysql://localhost:3306/ps";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()){
                    System.out.println("\n\nNama sebelumnya : "+result.getString("PELANGGAN"));
                    System.out.print("Nama terbaru : ");
                    String inm = i.next();
                    sql = "UPDATE rental SET PELANGGAN='"+inm+"' WHERE NO='"+no+"'";
                    if(statement.executeUpdate(sql) > 0){
                        System.out.println("Berhasil memperbaharui nama pelanggan pada nota nomor "+no+" menjadi "+inm);
                    }
            }
            statement.close();  
            i.nextLine();      
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }
    }


    //delete data
    public void hapusPenjualan(String iadm) throws SQLException{
        //exception
        try{
            url = "jdbc:mysql://localhost:3306/ps";
            con = DriverManager.getConnection(url,"root","");
            String sql;
            System.out.println("---HAPUS DATA PENJUALAN---");
            riwayatPenjualan(iadm);
            System.out.println("\n1. Hapus nomor tertentu\n2. Hapus semua");
            System.out.print("Kategori Hapus: ");
            no = i.nextInt();
            if(no==1)
            {
                System.out.print("\nNomor nota yang akan dihapus : ");
	            no = i.nextInt();
                sql = "DELETE FROM rental WHERE No = "+ no;
                Statement statement = con.createStatement();
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil menghapus data penjualan dengan nomor "+no);
                }
            }
            else if(no==2){
                sql = "TRUNCATE TABLE rental";
                Statement statement = con.createStatement();
                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Berhasil menghapus keseluruhan data!");
                }
            }
	   }catch(SQLException e){
	        System.out.println("Terjadi kesalahan dalam menghapus data barang");
            System.err.println(e.getMessage());
	    }        
    }

    //seacrh data
    public void cariPenjualan(String iadm) throws SQLException{
        try {
            System.out.println("---CARI DATA PENJUALAN---");
            riwayatPenjualan(iadm);
            System.out.println("\n1. Nomor nota\n2. Nama pelanggan");
            System.out.print("Berdasarkan: ");
            int ic = i.nextInt();
            if(ic==1){
                searchNo(iadm);
            }
            else if(ic==2){
                searchPelanggan(iadm);
            }
            else{
                System.out.println("Data hanya dapat dicari berdasarkan nomor nota atau nama pelanggan!");
            }
            
        } catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }

    public void searchNo(String iadm) throws SQLException{
        try {
            Clean.clearScreen();
            System.out.print("\nMasukkan nomor nota yang dicari: ");
            no = i.nextInt();
            url = "jdbc:mysql://localhost:3306/ps";
            String sql = "SELECT * FROM rental WHERE No LIKE '%"+no+"%'";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                System.out.print("\nNo Nota  : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");                    
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nJenis      : ");
                if(result.getInt("Waktu_ps3")!=0)
                {
                    System.out.print("PS3");
                
                }
                else if(result.getInt("Waktu_ps4")!=0)
                {
                    System.out.print("PS4");
                
                }
                else if(result.getInt("Waktu_ps5")!=0)
                {
                    System.out.print("PS5");
                
                }
                System.out.print("\nJumlah     : ");
                if(result.getInt("Waktu_ps3")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps3")+" Menit");
                
                }
                else if(result.getInt("Waktu_ps4")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps4")+" Menit");
                
                }
                else if(result.getInt("Waktu_ps5")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps5")+" Menit");
                
                }
                System.out.print("\nHarga      : ");                    
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
                }
            statement.close();        
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }

    public void searchPelanggan(String iadm) throws SQLException{
        try {
            Clean.clearScreen();
            i.nextLine();
            System.out.print("\nMasukkan nama admin yang dicari: ");
            String inm = i.nextLine();
            url = "jdbc:mysql://localhost:3306/ps";
            String sql = "SELECT * FROM rental WHERE Pelanggan LIKE '%"+inm+"%'";
            con = DriverManager.getConnection(url,"root","");
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                System.out.print("\nNo Nota    : ");
                System.out.print(result.getInt("No"));
                System.out.print("\nAdmin      : ");                    
                System.out.print(result.getString("Admin"));
                System.out.print("\nTanggal    : ");
                System.out.print(result.getString("Tanggal"));
                System.out.print("\nPelanggan  :");
                System.out.print(result.getString("Pelanggan"));
                System.out.print("\nJenis      : ");
                if(result.getInt("Waktu_ps3")!=0)
                {
                    System.out.print("PS3");
                
                }
                else if(result.getInt("Waktu_ps4")!=0)
                {
                    System.out.print("PS4");
                
                }
                else if(result.getInt("Waktu_ps5")!=0)
                {
                    System.out.print("PS5");
                
                }
                System.out.print("\nJumlah     : ");
                if(result.getInt("Waktu_ps3")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps3")+" Menit");
                
                }
                else if(result.getInt("Waktu_ps4")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps4")+" Menit");
                
                }
                else if(result.getInt("Waktu_ps5")!=0)
                {
                    System.out.print(result.getInt("Waktu_ps5")+" Menit");
                
                }
                System.out.print("\nHarga      : ");                    
                System.out.print(result.getInt("Total_Harga"));
                System.out.println("");
                }
            statement.close();        
        } 
        catch (SQLException e) {
            System.err.println("Terjadi kesalahan dalam mencari data");
            System.err.println(e.getMessage());
        }
    }


}
