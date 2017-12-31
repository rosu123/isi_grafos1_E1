import static org.junit.Assert.*;

import java.io.*;
import org.junit.*;
//import java.util.*;

public class StutterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        
        // static variables in class Stutter must be reinitialized
        Stutter.lastdelimit = true;
        Stutter.curWord = "";
        Stutter.prevWord = "";
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }


    // This test tours main:if (fileName == null)   
    //.It is difficult/impossible to execute, as it requires passing a command line 
    // argument that is null
    
    @Test //#11
    public void testFromStdinWithNullArgs() {
        String[] args = {null};
        String string = "word\nword\nword\n";
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        
        System.setIn(stringStream);

        try {
            Stutter.main(args);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals("Repeated word on line 2: word word\n" +
                     "Repeated word on line 3: word word\n", outContent.toString());
        
        System.setIn(System.in);

    }
    
    @Test //#5: único test añadido a los que había, los demás cubren los pares du
    public void testFromStdinWithOneArgNull() {
        String[] args = new String[1];
        String string = "word word";
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        
        System.setIn(stringStream);

        try {
            Stutter.main(args);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals("Repeated word on line 1: word word\n", outContent.toString());
        
        System.setIn(System.in);

    }
    


    @Test //#6
    public void testFromFile() {
        try {
            Stutter.main(new String[] { "inputFile" });
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals("Repeated word on line 1: word word\n", outContent.toString());
    }
    
    @Test //#7 y #10
    public void t1() {
        String string = "hi";
        InputStream stringStream = new ByteArrayInputStream(string.getBytes());
        
        System.setIn(stringStream);

        try {
            Stutter.main(new String[] {});
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals("", outContent.toString());
        
        System.setIn(System.in);
    }
    
  @Test //#1, #2, #3
  public void t2() {
      String string = "hi hi";
      InputStream stringStream = new ByteArrayInputStream(string.getBytes());   
      System.setIn(stringStream);

      try {
          Stutter.main(new String[] {});
      } catch (IOException e) {
          fail(e.getMessage());
      }
      assertEquals("Repeated word on line 1: hi hi\n", outContent.toString());
      
      System.setIn(System.in);
  }
  
  @Test //#4, #8, #9
  public void t3() {
      String string = "yup\nhi hi";
      InputStream stringStream = new ByteArrayInputStream(string.getBytes());   
      System.setIn(stringStream);

      try {
          Stutter.main(new String[] {});
      } catch (IOException e) {
          fail(e.getMessage());
      }
      assertEquals("Repeated word on line 2: hi hi\n", outContent.toString());
      
      System.setIn(System.in);
  }
  
  @Test //#8, #9
  public void t4() {
      String string = "yup\n\nhi hi";
      InputStream stringStream = new ByteArrayInputStream(string.getBytes());   
      System.setIn(stringStream);

      try {
          Stutter.main(new String[] {});
      } catch (IOException e) {
          fail(e.getMessage());
      }
      assertEquals("Repeated word on line 3: hi hi\n", outContent.toString());
      
      System.setIn(System.in);
  }

}
