package ma.enset.servers;
public class Client {
    private Long id;
    private String addresIp;

    public Client() {
    }

    public Client(Long id, String addresIp) {
        this.id = id;
        this.addresIp = addresIp;
    }

    public Client(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddresIp() {
        return addresIp;
    }

    public void setAddresIp(String addresIp) {
        this.addresIp = addresIp;
    }
}