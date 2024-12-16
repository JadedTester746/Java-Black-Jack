import java.util.*;
public class Deck
{
    Stack<Card> deck;
    
    public Deck(){
        String[] suites = {"♠", "♥", "♦", "♣"};
        deck = new Stack<Card>();
        for(int i = 0; i < suites.length; i++){
            for(int j = 1; j <=13; j++ ){
                deck.push(new Card(j, suites[i]));
            }
        }
        
    }
    public void shuffleHelper(){
        Random rand = new Random();
        Stack<Card>left = new Stack<Card>();
        Stack<Card>right = new Stack<Card>();
        ArrayList<Card>list = new ArrayList<Card>(deck);
        for(int i = 0; i < 26; i++){
            left.push(deck.pop());
        }
        for(int i = 26; i < 52; i++){
            right.push(deck.pop());
        }
        for(int i = 0; i < 26; i++){
            int amountLeft = rand.nextInt(3) + 1;
            int amountRight = rand.nextInt(3) + 1;
            int j = 0;
            while(j < amountLeft && !left.isEmpty()){
                deck.push(left.pop());
                j++;
            }
            j = 0;
            while(j < amountRight && !right.isEmpty()){
                deck.push(right.pop());
                j++;
            }
           
        }
        
        
    }
    public Card draw(){
        return deck.pop();
    }
    public void shuffle(int times){
        for(int i = 0; i < times; i++){
            shuffleHelper();
        }
        System.out.println(deck.size());
        //while(!deck.isEmpty()){
            //System.out.println((deck.pop()));
        //}
    }
    public static void main(String[] args){
        Card.setUpMap();
        Deck d = new Deck();
        d.shuffle(15);
        Player p = new Player(false);
        p.hit(new Card(1, "Spades"));
        p.hit(new Card(1, "Spades"));
        System.out.println(p.sumOfCards());
    }
}
