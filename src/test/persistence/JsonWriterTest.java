package persistence;

import model.Cryptocurrency;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit tests for JsonWriter class
 */
class  JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Profile profile = new Profile("Python", 1000000);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Profile profile = new Profile("DogeLover123", 1000000);
            JsonWriter writer = new JsonWriter("./data/test/jsonWriterTestEmptyCryptowallet.json");
            writer.open();
            writer.write(profile);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/jsonWriterTestEmptyCryptowallet.json");
            profile = reader.read();
            assertEquals("DogeLover123", profile.getName());
            assertEquals(1000000, profile.getBalance());
            assertEquals(0, profile.getCryptoWallet().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Cryptocurrency safemoon = new Cryptocurrency("Safemoon", "SAFEMOON", 0.000003074, 10);
            Cryptocurrency shibainu = new Cryptocurrency("Shiba Inu", "SHIB", 0.00000768, 26);
            Cryptocurrency stellar = new Cryptocurrency("Stellar Lumen", "XLM", 0.3331, 30);
            Profile profile = new Profile("BearMarketComingSoon", 250);
            profile.getCryptoWallet().add(safemoon);
            profile.getCryptoWallet().add(shibainu);
            profile.getCryptoWallet().add(stellar);
            JsonWriter writer = new JsonWriter("./data/test/jsonWriterTestNormalCryptoWallet.json");
            writer.open();
            writer.write(profile);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/jsonWriterTestNormalCryptoWallet.json");
            profile = reader.read();
            assertEquals("BearMarketComingSoon", profile.getName());
            assertEquals(250, profile.getBalance());
            List<Cryptocurrency> cryptoWallet = profile.getCryptoWallet();
            assertEquals(3, cryptoWallet.size());
            checkCrypto("Safemoon", "SAFEMOON", 0.000003074, 10, safemoon);
            checkCrypto("Shiba Inu", "SHIB", 0.00000768, 26, shibainu);
            checkCrypto("Stellar Lumen", "XLM", 0.3331, 30, stellar);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}