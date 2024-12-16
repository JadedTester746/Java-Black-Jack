import java.util.*;
public class Blackjack
{
    static Player human;
    static Player ai;
    public static void main(String[] args){
        System.out.println("Lets play Blackjack, I'll be the dealer!");
        System.out.println("Setting up game, this might take a millisecond");
        Card.setUpMap();
        Deck d = new Deck();
        Scanner scan = new Scanner(System.in);
        d.shuffle(15);
        
        human = new Player(false);
        ai = new Player(true);
        
        do{
            System.out.println("Cards are being dealt");
            for(int i = 0; i < 2; i++){
                human.hit(d.draw());
                ai.hit(d.draw());
            }
            System.out.println("Lets go gambling!");
            printHands(false);
            
            System.out.println("Alright, make your bet(NOTE, if you bet above how much you have, it is assumed you're going all in)");
            System.out.printf("Your current amount is: $%.2f \n", human.money);
            double bet = getBet(scan, human);
            System.out.printf("Ok, your current bet is: $%.2f \n", bet);
            
                
            
            if(human.sumOfCards() == 21){
                System.out.println("You won this round!");
                human.score++;
                dealWithMoney(true, human, bet);
            }
            else{
                boolean flag = true;
                do{
                    System.out.println("Do you want to hit or stay?(hit/stay)");
                    String input = scan.next();
                    if(input.equals("hit")){
                        human.hit(d.draw());
                    }
                    else if(input.equals("stay")){
                        flag = false;
                    }
                    else{
                        System.out.println("Try again, the inputs are hit or stay");
                    }
                    if(d.deck.isEmpty()){
                        d = new Deck();
                        d.shuffle(15);
                    }
                    System.out.print("Your hand: ");
                    human.printHand();
                }while(flag &&  human.sumOfCards() < 21);
                System.out.println("");
                System.out.println("Alright, your total is: " + human.sumOfCards());
                if(human.sumOfCards()==21){
                    System.out.println("You Won!!!");
                    human.score++;
                    dealWithMoney(true, human, bet);
                }
                else if(human.sumOfCards() > 21){
                    System.out.println("Womp Womp, you busted");
                    ai.score++;
                    dealWithMoney(false, human, bet);
                }
                else{
                    System.out.println("Scary AI's turn");
                    while(ai.shouldHit()){
                        ai.hit(d.draw());
                        if(d.deck.isEmpty()){
                            d = new Deck();
                            d.shuffle(15);
                        }
                    }
                }
                System.out.println("Final hands");
                printHands(true);
                if(!(human.sumOfCards()>= 21)){
                    if(human.sumOfCards() == ai.sumOfCards()){
                        System.out.println("Tie! No points given");
                        human.money += bet;
                    }
                    else{
                        if(aiWon(human.sumOfCards(), ai.sumOfCards())){
                            System.out.println("AI won");
                            ai.score++;
                            dealWithMoney(false, human, bet);
                        }
                        else{
                            System.out.println("You won!!!!!");
                            human.score++;
                            dealWithMoney(true, human, bet);
                        }
                    }
                }
                
            }
            System.out.printf("Current Standings: Human %d / AI %d\n", human.score, ai.score);
            System.out.printf("Your current amount is: $%.2f \n", human.money);
            human.hand.clear();
            ai.hand.clear();
            if(d.deck.size() < 10){
                d = new Deck();
                d.shuffle(15);
            }
            System.out.println("Would you like to play again? (y/n)");
        }while(human.money > 0 && scan.next().equals("y"));
        System.out.printf("Current Standings: Human %d / AI %d\n", human.score, ai.score);
        System.out.printf("You gained: $%.2f \n", human.money - 1000);
    }
    public static void printHands(boolean showAll){
        System.out.print("Your hand: ");
        human.printHand();
        System.out.println("");
        System.out.print("Scary AI hand: ");
        if(showAll){
            for(int i = 0; i < ai.hand.size(); i++){
                System.out.print(ai.hand.get(i) + ", ");
            }
        }
        else{
            System.out.print("HIDDEN CARD, ");
            for(int i = 1; i < ai.hand.size(); i++){
                System.out.print(ai.hand.get(i) + ", ");
            }
        }
        System.out.println("");
        
    }
    
    public static boolean aiWon(int humanScore, int aiScore){
        if(aiScore > 21){
            return false;//only need to check AI
        }
        else{
            return Math.abs(21 - aiScore) < Math.abs(21 - humanScore);
        }
    }
    
    public static double getBet(Scanner scan, Player p){
        double input = scan.nextDouble();
        if(input > p.money){
            input = p.money;
        }
        if(input <= 0){
            input = 0;
        }
        p.money -= input;
        return input;
    }
    
    public static void dealWithMoney(boolean won, Player p, double amount){
        if(won){p.money += amount * 2; System.out.println("I can't stop winning!!!!!");}
        else{System.out.println("Aw dang it");}
    }
}
