package model;

import exceptions.InsufficientBalanceException;
import exceptions.InvalidAmountException;
import exceptions.InvalidSelectionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit tests for Profile class
 */
class ProfileTest {
    private Profile richProfile;
    private Profile poorProfile;
    private Cryptocurrency bitcoin;
    private Cryptocurrency ethereum;
    private Cryptocurrency litecoin;
    private Cryptocurrency dogecoin;

    @BeforeEach
    public void setUp() {
        richProfile = new Profile("Python", 1000000);
        poorProfile = new Profile("Naaimur", 100);

        bitcoin = new Cryptocurrency("Bitcoin", "BTC", 43297.86);
        ethereum = new Cryptocurrency("Ethereum", "ETH", 2726.81);
        litecoin = new Cryptocurrency("Litecoin", "LTC", 158.51);
        dogecoin = new Cryptocurrency("Dogecoin", "DOGE", 0.25);

    }


    @Test
    public void buyTest() {
        try {
            richProfile.buy(ethereum, 10);
            assertTrue(richProfile.getCryptoWallet().contains(ethereum));
            assertEquals(10, richProfile.getCryptoWallet().get(0).getAmount());
        } catch (InsufficientBalanceException e) {
            fail();
        } catch (InvalidAmountException e) {
            fail();
        }

        try {
            poorProfile.buy(bitcoin, 2);
            fail();
        } catch (InsufficientBalanceException e) {
            // pass
        } catch (InvalidAmountException e) {
            fail();
        }


        try {
            poorProfile.buy(bitcoin, -1);
            fail();
        } catch (InsufficientBalanceException e) {
            fail();
        } catch (InvalidAmountException e) {
            // pass
        }

        try {
            richProfile.buy(ethereum, 5);
            assertEquals(15, richProfile.getCryptoWallet().get(0).getAmount());
            // pass
        } catch (InsufficientBalanceException e) {
            fail();
        } catch (InvalidAmountException e) {
            fail();
        }

    }


    @Test
    public void sellTest() {
        try {
            richProfile.buy(ethereum, 10);
            richProfile.buy(bitcoin, 5);
            richProfile.buy(dogecoin, 1000);
            richProfile.sell(1, 5);
            richProfile.sell(2, 0);
            assertEquals(5, richProfile.getCryptoWallet().get(0).getAmount());
            assertEquals(5, richProfile.getCryptoWallet().get(1).getAmount());
            // pass
        } catch (InsufficientBalanceException e) {
            fail();
        } catch (InvalidSelectionException e) {
            fail();
        } catch (InvalidAmountException e) {
            fail();
        }

        try {
            richProfile.sell(5, 5);
            fail();
        } catch (InvalidSelectionException e) {
            // pass
        } catch (InvalidAmountException e) {
            fail();
        }

        try {
            richProfile.sell(2, 1100);
            fail();
        } catch (InvalidSelectionException e) {
            fail();
        } catch (InvalidAmountException e) {
            // pass
        }

        try {
            richProfile.sell(3, 1000);
            assertFalse(richProfile.getCryptoWallet().contains(dogecoin));
            // pass
        } catch (InvalidAmountException e) {
            fail();
        } catch (InvalidSelectionException e) {
            fail();
        }

        try {
            richProfile.sell(-1, 1000);
            fail();
        } catch (InvalidAmountException e) {
            fail();
        } catch (InvalidSelectionException e) {
            // pass
        }
    }


    @Test
    public void tradeTest() {
        try {
            richProfile.buy(ethereum, 10);
            richProfile.buy(bitcoin, 5);
            richProfile.buy(dogecoin, 1000);
            richProfile.trade(4, litecoin);
            fail();
        } catch (InvalidSelectionException e) {
            // pass
        } catch (InsufficientBalanceException e) {
            fail();
        } catch (InvalidAmountException e) {
            fail();
        }

        try {
            richProfile.trade(2, litecoin);
            richProfile.trade(2, ethereum);
            assertFalse(richProfile.getCryptoWallet().contains(bitcoin));
            assertTrue(richProfile.getCryptoWallet().contains(litecoin));
            assertFalse(richProfile.getCryptoWallet().contains(dogecoin));
            assertTrue(ethereum.getAmount() > 10);
            // pass
        } catch (InvalidSelectionException e) {
            fail();
        }

        try {
            richProfile.trade(-1, ethereum);
            fail();
        } catch (InvalidSelectionException e) {
            // pass
        }
    }

}