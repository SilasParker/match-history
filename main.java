import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Main {

    private static SetList arr = new SetList();

    public static void main(String[] args) throws ParseException {
        String newDate = "09/12/2002";
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "24/09/1999";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
       
        newDate = "25/09/2010";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "24/07/2009";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "24/01/2019";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        
        newDate = "18/08/2006";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "14/12/2003";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "07/05/2002";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "10/05/2003";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "24/05/2017";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "28/01/2019";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "01/01/1999";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        newDate = "14/12/2003";
        date = new SimpleDateFormat("dd/MM/yyyy").parse(newDate);
        arr.addSet(new Set(null,null,null,null,null,date));
        arr.toStringTest();
    }


}

