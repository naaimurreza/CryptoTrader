# CryptoTrader ©

### The world's best cryptocurrency trading simulator
***

CryptoTrader © is a game that lets you try out cryptocurrency trading without the fear of losing a lot of money.
In fact, you don't lose any money at all, you just gain experience. Start off with a small loan of a million dollars
(or any sum of your desire) and *buy*, *sell* and *trade* cryptocurrencies to expand your fortune! Made for anyone and 
everyone living through the crypto revolution, this game is fun, easy and helps you build better trading strategies.

Blockchain technology and cryptocurrencies fascinate me, and I believe they are here to stay. As a beginner trader, I 
have had lost a lot of money trading with almost no strategy and having a game like this back then would have been
helpful.

[![CryptoTrader demo](https://img.youtube.com/vi/lmBl4x1qzeg/0.jpg)](https://www.youtube.com/watch?v=lmBl4x1qzeg)



### User Stories
- As a user, I want to be able to buy and add a new cryptocurrency to my crypto wallet.
- As a user, I want to be able to sell and remove cryptocurrencies from my crypto wallet.
- As a user, I want to be able to trade a cryptocurrency I own for another.
- As a user, I want to be able to view all my cryptocurrencies and their amounts in my crypto wallet.  
- As a user, I want to be able to start a new profile with a new name and balance.  
- As a user, I want to be able to close the application and have my data automatically saved to file.
- As a user, I want to be able to open the application and have my data automatically loaded from file.


### Phase 4: Task 2
**Java language construct added:** Throwing a checked exception.

In Profile class, the method buy() throws InsufficientBalanceException and InvalidAmountException. The method sell() 
throws InvalidSelectionException and InvalidAmountException and finally the method trade() throws InvalidSelectionException 
and all these exceptions are caught appropriately in the classes in the ui package. They are also tested correctly 
in the ProfileTest 
class.

### Phase 4: Task 3
Improvement to my design:

- There is duplicated code in my BuyFrame, SellFrame, TradeFrame and WalletFrame class that I can refactor and put in 
  the Frame superclass.
  

  

