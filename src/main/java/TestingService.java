import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.*;


public class TestingService implements ExampleService {
   public IdGenerator generator;
   public ExampleDAO exampleDAO;
   public ExampleEntity myItem=new ExampleEntity();
   public BigDecimal limit = new BigDecimal(15.00);

   public void addNewItem(String title, BigDecimal price){
       if(title.isEmpty()) System.out.println("Title is empty");
       else {
           if ((title.length()) > 3 && (title.length() <= 20))
               myItem.setTitle(title);
           else System.out.println("Title size is not matched required size");
       }
       if (price.compareTo(limit)<0) System.out.println("Price is under the limit");
       else {
         if  ((price.compareTo(limit) > 0)&&(price.compareTo(BigDecimal.ZERO) != 0))
           myItem.setPrice(price.setScale(2, RoundingMode.HALF_UP));
       }
       myItem.setDateIn(Instant.now());
       myItem.setId(generator.nextId());
       for(ExampleEntity ent:exampleDAO.findAll())
       {
           if(ent.getTitle().equals(title)) return;
       }
       exampleDAO.store(myItem);
    }
   public Map<LocalDate, BigDecimal> getStatistic(){

       HashMap<LocalDate,BigDecimal> statistics=new HashMap<>();
       HashMap<LocalDate,ArrayList<ExampleEntity>> buff=new HashMap<>();
       BigDecimal bufPrice=new BigDecimal(0.00);
       int i=0;

        for(ExampleEntity ent:exampleDAO.findAll()){
           Instant myDate= ent.getDateIn();
           LocalDate myLDate=myDate.atZone(ZoneId.systemDefault()).toLocalDate();
           bufPrice=ent.getPrice();

            if (buff.containsKey(myLDate))
                buff.get(myLDate).add(ent);
            else {
                buff.put(myLDate, new ArrayList<>());
                buff.get(myLDate).add(ent);
            }

           for(LocalDate date:buff.keySet()){
               ArrayList<ExampleEntity> buffList = buff.get(date);
               for (ExampleEntity entity:buffList) {
                   bufPrice = entity.getPrice().add(bufPrice);
               }
               statistics.put(date, bufPrice.divide(new BigDecimal(buffList.size()), RoundingMode.HALF_UP));
           }
        }
        return statistics;
        }
}
