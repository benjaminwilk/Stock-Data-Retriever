import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    //https://www.investopedia.com/markets/api/partial/historical/?Symbol=aapl&Type=Historical+Prices&Timeframe=Daily&StartDate=Nov+28%2C+1991&EndDate=Dec+05%2C+1991
    String stockSymbol;
    String startDate;
    String endDate;


    public static void main(String[] args) {

        System.out.println("Hello World!");
        Main man = new Main();
        man.primaryLoop();

    }

    public void primaryLoop(){
        Scanner reader = new Scanner(System.in);
        getStockSymbol(reader);
        System.out.print("Enter Start Date: ");
        this.startDate = setDate(reader);
        System.out.print("Enter End Date: ");
        this.endDate = setDate(reader);
        getWebData();
    }

    public void getStockSymbol(Scanner reader){
        System.out.print("Enter Stock Symbol: ");
        String userInput = reader.nextLine();
        this.stockSymbol = userInput;
    }

    public String setDate(Scanner reader){
        String userDate = reader.nextLine();
        //System.out.println(userDate);
        return this.endDate = dateFormatter(userDate);

    }

    private String dateFormatter(String formatDate){
        char firstChar = formatDate.charAt(0);
        SimpleDateFormat newFormat = new SimpleDateFormat("MMM+dd+yyyy");
        if(Character.isDigit(firstChar)) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = formatter.parse(formatDate);
                //System.out.println(date);
                //System.out.println(formatter.format(date));
                return newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");

            try {

                Date date = formatter.parse(formatDate);
                //System.out.println(date);
                //System.out.println(formatter.format(date));
                return newFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return "-1 : Something went wrong.";
    }

    private void getWebData() {
        String dataURL = "https://www.investopedia.com/markets/api/partial/historical/?Symbol=" + this.stockSymbol + "&Type=Historical+Prices&Timeframe=Daily&StartDate=" + this.startDate + "&EndDate=" + this.endDate;

        try {
            URL url = new URL(dataURL);

            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;

            // read each line and write to System.out
            while ((line = br.readLine()) != null) {
                String strippedText = line.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                System.out.println(strippedText);
            }

        } catch (java.net.MalformedURLException mue) {
            System.err.println("Malformed URL Found: " + mue);

        } catch (IOException ioe){
            System.err.println("IO Exception Found: " + ioe);
        }


    }

}
