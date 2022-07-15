package com.github.zipcodewilmington;

import com.github.zipcodewilmington.casino.Account;
import com.github.zipcodewilmington.casino.AccountManager;
import com.github.zipcodewilmington.casino.GameInterface;
import com.github.zipcodewilmington.casino.Player;
import com.github.zipcodewilmington.casino.games.cardgames.ThreeCardPokerGame;
import com.github.zipcodewilmington.casino.games.cardgames.ThreeCardPokerPlayer;
import com.github.zipcodewilmington.casino.games.dicegames.HighLowDice;
import com.github.zipcodewilmington.casino.games.dicegames.HighLowDicePlayer;
import com.github.zipcodewilmington.casino.games.slots.SlotsGame;
import com.github.zipcodewilmington.casino.games.slots.SlotsPlayer;
import com.github.zipcodewilmington.utils.AnsiColor;
import com.github.zipcodewilmington.utils.IOConsole;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by leon on 7/21/2020.
 */
public class Casino {
    private final IOConsole console = new IOConsole(AnsiColor.BLUE);
    private HashSet<Player> playerSet;

    public void run() throws IOException {
        String dashBoardInput;
        AccountManager accountManager = new AccountManager();
        try {
            do {
               dashBoardInput = getDashboardInput();
               if ("select-game".equals(dashBoardInput)) {
                   String gameSelectionInput = getGameSelectionInput().toUpperCase();
//                   if (gameSelectionInput.equals("SLOTS")) {
//                       play(new SlotsGame(), new SlotsPlayer());
//                   }
                   //                    else if (gameSelectionInput.equals("BLACK JACK")) {
                   //                        play(new BlackJackGame(), new BlackJackPlayer());
                   if (gameSelectionInput.equals("HIGH LOW DICE")) {
                       String accountName = console.getStringInput("Enter your account name:");
                       String accountPassword = console.getStringInput("Enter your account password:");
                       HashSet<HighLowDicePlayer> dicePlayers = new HashSet<>();
                       Account account = accountManager.getAccount(accountName, accountPassword);
                       if (account != null) {
                           HighLowDicePlayer dicePlayer = new HighLowDicePlayer(account);
                           dicePlayers.add(dicePlayer);
                           HighLowDice diceGame = new HighLowDice(dicePlayers);
                           diceGame.beginGame();
                           accountManager.updateAccounts();
                       } else {
                           // TODO - implement better exception handling
                           String errorMessage = "No account found with name of [ %s ] and password of [ %s ]";
                           throw new RuntimeException(String.format(errorMessage, accountPassword, accountName));
                       }
                   } else if (gameSelectionInput.equals("THREE CARD POKER")) {
                       String accountName = console.getStringInput("Enter your account name:");
                       String accountPassword = console.getStringInput("Enter your account password:");
                       HashSet<ThreeCardPokerPlayer> threeCardPlayers = new HashSet<>();
                       Account account = accountManager.getAccount(accountName, accountPassword);
                       if (account != null) {
                           ThreeCardPokerPlayer threeCardPokerPlayer = new ThreeCardPokerPlayer(account);
                           threeCardPlayers.add(threeCardPokerPlayer);
                           ThreeCardPokerGame threeCardPokerGame = new ThreeCardPokerGame(threeCardPlayers);
                           threeCardPokerGame.beginGame();
                           accountManager.updateAccounts();
                       } else {
                           // TODO - implement better exception handling
                           String errorMessage = "No account found with name of [ %s ] and password of [ %s ]";
                           throw new RuntimeException(String.format(errorMessage, accountPassword, accountName));
                       }
                   }
                   //                    } else if (gameSelectionInput.equals("ROULETTE")){
                   //                        play (new RouletteGame(), new RoulettePlayer());
                   //                    } else if (gameSelectionInput.equals("TIC TAC TOE")){
                   //                        play (new TicTacToeGame(), new TicTacToePlayer());
                   //                    }
                   else {
                       // TODO - implement better exception handling
                       String errorMessage = "[ %s ] is an invalid game selection";
                       throw new RuntimeException(String.format(errorMessage, gameSelectionInput));
                   }



               } else if ("create-account".equals(dashBoardInput)) {
                   console.println("Welcome to the account-creation screen.");
                   String accountName = console.getStringInput("Enter your account name:");
                   String accountPassword = console.getStringInput("Enter your account password:");
                   Account newAccount = accountManager.createAccount(accountName, accountPassword);
                   accountManager.updateAccounts();
               }
           } while (!"logout".equals(dashBoardInput));
       } catch (RuntimeException e){System.out.println(e.getMessage());}
    }

    private String getDashboardInput() {
        return console.getStringInput("Welcome to the Venetian!" +
                "\nFrom here, you can select any of the following options:" +
                "\n\t[ create-account ]  [ select-game ]");
    }

    private String getGameSelectionInput() {
        return console.getStringInput("From here, you can select any of the following games:" +
                "\n\t[ SLOTS ], [ THREE CARD POKER ], [ BLACK JACK ], [ ROULETTE ], [ HIGH LOW DICE ], [ TIC TAC TOE ]");
    }

    private void play(Object gameObject, Object playerObject) {
        GameInterface game = (GameInterface)gameObject;
        //PlayerInterface player = (PlayerInterface)playerObject;
        //game.add(player);
        //game.run();
    }
}
