package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

public class Basketball {
    private boolean check;

    public boolean isValid(String valid) {

        check = valid.equals("yes");
        return check;

    }
    public String randomNumber (int range, int add){//int range
        /*
        Random rn = new Random();
        String s = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return s;*/
        Random rn = new Random();
        int rand = rn.nextInt(range) + add;
        return String.valueOf(rand);
    }
    public String randomFirstname (){

        String[] firstname = {"Jim", "Carl", "Mikael", "Jenny"};

        return firstname[Integer.parseInt(randomNumber(firstname.length,0))];
    }
    public String randomLastname (){

        String[] lastname = {"Karlsson", "Lundin", "Hardin", "Solomon"};

        return lastname[Integer.parseInt(randomNumber(lastname.length,0))];
    }
    public String randomEmail (){
        return randomFirstname() + "." + randomLastname() + randomNumber(100,1) + "@yopmail.com";
    }
    public String randomPassword (){
        return randomFirstname() + randomNumber(100,1) + randomLastname();
    }
}
