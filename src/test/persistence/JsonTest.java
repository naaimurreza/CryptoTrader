package persistence;


import model.Cryptocurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCrypto(String cryptoName, String cryptoCode, double price, double amount, Cryptocurrency cryptocurrency) {
        assertEquals(cryptoName, cryptocurrency.getCryptoName());
        assertEquals(cryptoCode, cryptocurrency.getCryptoCode());
        assertEquals(price, cryptocurrency.getPrice());
        assertEquals(amount, cryptocurrency.getAmount());
    }
}
