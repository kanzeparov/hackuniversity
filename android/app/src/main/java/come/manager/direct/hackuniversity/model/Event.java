package come.manager.direct.hackuniversity.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event  implements Parcelable {

    public Event(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Event(String image, String city, String name, String owner, String email, String fio, String date, String ticketId, String price, String status) {
        this.image = image;
        this.city = city;
        this.name = name;
        this.owner = owner;
        this.email = email;
        this.fio = fio;
        this.date = date;
        this.ticketId = ticketId;
        this.price = price;
        this.status = status;
    }

    public Event(Parcel in) {
        String[] data = new String[10];
        in.readStringArray(data);
        image = data[0];
        city = data[1];
        name = data[2];
        owner = data[3];
        email = data[4];
        fio = data[5];
        date = data[6];
        ticketId = data[7];
        price = data[8];
        status = data[9];


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {  image,  city,  name,  owner,  email,  fio,  date,  ticketId,  price,  status });
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    String image;
    String city;
    String name;
    String owner;
    String email;
    String fio;
    String date;
    String ticketId;
    String price;
    String status;
}
