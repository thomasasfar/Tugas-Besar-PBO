import java.sql.SQLException;
//interface Ps
public interface Ps {

    public int noNota(int iharga) throws SQLException;
    public String admin(String iadm);
    public String pelanggan(String ipelanggan);
    public int harga(int iharga);
    public String tanggal(int iharga);
    public float ps3(int iharga);
    public float ps4(int iharga);
    public float ps5(int iharga);
}
