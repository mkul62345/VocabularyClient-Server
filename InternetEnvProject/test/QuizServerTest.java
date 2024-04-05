
import CryptoAlgos.SymmetricEncrypter;
import dao.QuestionDaoImpl;
import dao.UserDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import service.QuestionService;

public class QuizServerTest {
    static SymmetricEncrypter encrypter;
    static String encryptionKey;
    static String decryptionKey;

    @BeforeAll
    public static void setUpAll(){
        System.out.println("Setting up keys");
        encrypter = new SymmetricEncrypter();
        encryptionKey = "419";
        int tempKey = 26 - ( Integer.parseInt(encryptionKey) % 526);
        decryptionKey = String.valueOf(tempKey);
    }

    @Test
    public void check_encryption_encrypt(){
        System.out.println("Testing encryption");
        Assertions.assertEquals(">;@Da!C09#", encrypter.transform(">;@Ax!Z09#", encryptionKey));
    }

    @Test
    public void check_encryption_decrypt(){
        System.out.println("Testing decryption");
        Assertions.assertEquals(">;@Ax!Z09#", encrypter.transform(">;@Da!C09#", decryptionKey));
    }

    @Test
    public void test_service(){
        System.out.println("Testing loading data (Dao) and content generation (Service)");
        QuestionService questionService = new QuestionService(new QuestionDaoImpl(), new UserDaoImpl());
        System.out.println(questionService.MakePoll().toString());
        Assertions.assertNotEquals(null, questionService);
    }

    @AfterAll
    public static void wrapUp(){
        System.out.println("Testing Finished");
    }


}

