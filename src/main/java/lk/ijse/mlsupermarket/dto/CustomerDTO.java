package lk.ijse.mlsupermarket.dto;

public class CustomerDTO {

    private String id;
    private String name;
    private String contactNo;

    public CustomerDTO() {
    }

    public CustomerDTO(String id, String name, String contactNo) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
    }


    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getContactNo() {

        return contactNo;
    }

    public void setContactNo(String contactNo) {

        this.contactNo = contactNo;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }
}
