package persistence;

import model.Cryptocurrency;
import model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit tests for JsonReader class
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/test/dogeToTheMoon.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/test/jsonReaderTestEmptyCryptoWallet.json");
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
        JsonReader reader = new JsonReader("./data/test/jsonReaderTestNormalCryptoWallet.json");
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

    @Test
    void testReadMarketWithNormalMarket() {
        JsonReader read = new JsonReader("./data/cryptoMarket.json");
        try {
            List<Cryptocurrency> marketTest = new ArrayList<>();
            marketTest = read.readMarket();
            assertEquals("Bitcoin", marketTest.get(0).getCryptoName());
            assertEquals("Ethereum", marketTest.get(1).getCryptoName());
            assertEquals("Litecoin", marketTest.get(2).getCryptoName());
            assertEquals("Dogecoin", marketTest.get(3).getCryptoName());
            assertEquals("Cardano", marketTest.get(4).getCryptoName());
            assertEquals("Tether", marketTest.get(5).getCryptoName());
            assertEquals("Ripple", marketTest.get(6).getCryptoName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}