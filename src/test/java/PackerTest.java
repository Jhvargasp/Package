import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;


public class PackerTest {


    @Before
    public void setUp() {
    }

    @Test
    public void pack() {
        String file1=getFileFromResources("fileDemo1.txt");
        try {
            String response=Packer.pack(file1);
            System.out.println(response);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void packWithConstraints() {
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Weight of package can not be higher than 100!"));
        }
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints1.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Weight of package can not be lower that 0!"));
        }
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints2.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Weight of an item can not be higher than 100!"));
        }
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints3.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Cost of an item can not be higher than 100!"));
        }
        try {
            //max than 15 results
            System.out.println(Packer.pack(getFileFromResources("fileDemoConstraints4.txt")));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("File not found"));
        }
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints5.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Weight of an item can not be lower than 100!"));
        }
        try {
            Packer.pack(getFileFromResources("fileDemoConstraints6.txt"));
        } catch (APIException e) {
            Assert.assertTrue(e.getMessage().contains("Max Cost of an item can not be lower than 100!"));
        }

    }

    @Test( expected = IllegalArgumentException.class)
    public void packNotFoundFileException() {
        String file1=getFileFromResources("fileDemo1Not Exist.txt");
        try {
            Packer.pack(file1);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    private String getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile()).getAbsolutePath();
        }

    }

}