package config;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.io.*;
import java.util.Properties;

public class Config {
    private static volatile Config instance;

    private final File CFG_FILE = new File("config.ini");
    private Properties props = new Properties();

    private Config() {

        if (!CFG_FILE.exists()) {
            System.out.println("Файл " + CFG_FILE.getAbsolutePath() + " не найден");
            System.exit(1);
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(CFG_FILE),"UTF-8")) {
            props.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    public static Config getInstance(){
        Config localInstance = instance;
        if  (localInstance == null){
            synchronized (Config.class){
                localInstance = instance;
                if(localInstance == null){
                    instance = localInstance = new Config();
                }
            }
        }
        return localInstance;
    }

/*    public Properties getProps() {
        return props;
    }*/

    public File getXlsFile(){
        return new File(props.getProperty("xlsFile").replace("\\","/"));
    }
    public String getXlsSheet(){
        return props.getProperty("xlsSheet");
    }
    public String getColumn(){
        return props.getProperty("xlsColumn");
    }
    public int getRowStart(){
        return Integer.valueOf(props.getProperty("xlsRowStart"));
    }
    public int getRowStop () throws NumberFormatException {
        return Integer.valueOf(props.getProperty("xlsRowStop"));
    }
}
