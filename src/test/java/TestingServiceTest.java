import org.junit.Assert;
import org.mockito.Mock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;


import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TestingServiceTest {


    private TestingService testingService;
    private ExampleEntity exampleEntity;

    @Before
    public void setUp() throws Exception {
        ExampleDAO exampleDAO= Mockito.mock(ExampleDAO.class);
        IdGenerator idGenerator=Mockito.mock(IdGenerator.class);
    }
    @DisplayName("Incorrect title")
    @Test(expected = NullPointerException.class)
    public void addNewItem() {
        testingService.addNewItem("",new BigDecimal(150.00));
    }

    @DisplayName("Incorrect price")
    @Test(expected = NullPointerException.class)
    public void addNewItemWrongPrice() {
        testingService.addNewItem("Laptop",new BigDecimal(23));
    }

   @Test
   public void getStatistic() {

   }
}