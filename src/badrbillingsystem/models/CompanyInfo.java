
package badrbillingsystem.models;

public class CompanyInfo {
    private long id;
    private String name;
    private String logo;
    private String phone;
    private String address;

    public CompanyInfo() {
    }

    public CompanyInfo(long id, String name, String logo, String phone, String address) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.phone = phone;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" + "id=" + id + ", name=" + name + ", logo=" + logo + ", phone=" + phone + ", address=" + address + '}';
    }
}
