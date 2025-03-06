package hotel.aplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


class Reservation {
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Reservation(int roomNumber, Date checkIn, Date checkOut) {
        this.roomNumber = roomNumber;
        updateDates(checkIn, checkOut);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getCheckIn() {

        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public int duration(){
        return (int)((checkOut.getTime() - checkIn.getTime())/(1000*60*60*24));
    }

    public void updateDates(Date checkIn, Date checkOut) throws IllegalArgumentException{

        Date now = new Date();
        if(checkIn.after(checkOut)){
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        if (checkIn.before(now)){
            throw new IllegalArgumentException("Reservation dates for updates must be must be in future");
        }

        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Room "
                + getRoomNumber()
                + ", Check-in: "
                + sdf.format(getCheckIn())
                + ", Check-out: "
                + sdf.format(getCheckOut())
                + ", "
                + duration()
                + " nights";
    }


}


public class Hotel {
    public static void main(String[] args) {
        try {

            Scanner sc = new Scanner(System.in);

            System.out.print("Room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine();

            System.out.print("Check-in date (dd/mm/yyyy) ");

            String data = sc.nextLine();

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            
            Date checkInDate = format.parse(data);


            System.out.print("Check-out date (dd/mm/yyyy) ");

            data = sc.nextLine();

            Date checkOutDate = format.parse(data);

            Reservation reservation = new Reservation(roomNumber, checkInDate, checkOutDate);
            System.out.println(reservation);

            System.out.println("\nEnter date to update reservation: ");
            System.out.print("Check-in date (dd/mm/yyyy) ");

            data = sc.nextLine();
            checkInDate = format.parse(data);

            System.out.print("Check-out date (dd/mm/yyyy) ");

            data = sc.nextLine();
            checkOutDate = format.parse(data);

            reservation.updateDates(checkInDate, checkOutDate);
            System.out.println(reservation);

            sc.close();

        }

        catch ( ParseException e ){
            System.out.println("Wasn't possible to covert date data");
        }

        catch ( IllegalArgumentException e ){
            System.out.println(e.getMessage());
        }

        catch (RuntimeException e){
            System.out.println("Unexpected error");
        }
    }
}
