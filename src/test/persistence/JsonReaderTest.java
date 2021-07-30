package persistence;

import model.Cryptocurrency;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/dogeToTheMoon.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/jsonReaderTestEmptyCryptoWallet.json");
        try {
            Profile profile = reader.read();
            assertEquals("Python", profile.getName());
            assertEquals(0, profile.getCryptoWallet().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/jsonReaderTestNormalCryptoWallet.json");
        try {
            Profile profile = reader.read();
            assertEquals("Python", profile.getName());
            List<Cryptocurrency> cryptoWallet = profile.getCryptoWallet();
            assertEquals(2, cryptoWallet.size());
            checkCrypto("Bitcoin", "BTC", 41030.134582499995, 10, cryptoWallet.get(0));
            checkCrypto("Dogecoin", "DOGE", 0.26000, 10000, cryptoWallet.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}