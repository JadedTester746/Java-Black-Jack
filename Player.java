import java.util.*;
public class Player
{
    ArrayList<Card>hand;
    boolean chatGPT;
    int score;
    double money;    
    public Player(boolean AI){
        hand = new ArrayList<Card>();
        score = 0;
        money = 1000.0;
    }
    
    public int sumOfCards(){
        int out = 0;
        ArrayList<Integer> aceIndex = new ArrayList<Integer>();
        
        for(int i = 0; i < hand.size(); i++){
            if(hand.get(i).value > 10){
                out += 10;
            }
            else if(hand.get(i).value == 1){
                out += 11;
                aceIndex.add(i);
            }
            else{
                out += hand.get(i).value;
            }
        }
        while(out > 21 && aceIndex.size() >= 1){
            out -= 10; //change an ace to a 1
            aceIndex.remove(aceIndex.size() - 1);
        }
        return out; // should be the closest value you can get to 21
    }
    
    public void hit(Card c){
        hand.add(c);
    }
    
    public boolean shouldHit(){
        return (sumOfCards() <= 16);

        
    }
    
    public void printHand(){
        for(Card c : hand){
            System.out.print(c + ", ");
        }
    }
}
