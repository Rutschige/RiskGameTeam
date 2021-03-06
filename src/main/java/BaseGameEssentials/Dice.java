package BaseGameEssentials;

import BaseGameEssentials.Game;
import BaseGameEssentials.Player;
import BaseGameEssentials.Territory;
import static BaseGameEssentials.TelegramBot.WIN;
import java.util.*;
import java.util.Random;
import java.util.Arrays;

public class Dice extends Game {
    private int roll;
    private Integer[] diceArray;
    private Random die;
    public boolean comparison;
    public boolean telegram = false; // Set to true if the game is played through Telegram
    public Dice() {}
    public Integer[] roll(int numberOfDice) {
        diceArray = new Integer[numberOfDice];
        for(int i = 0; i < diceArray.length; i++) {
            die = new Random();
            roll = die.nextInt(5) + 1;
            diceArray[i] = roll;
        }
        Arrays.sort(diceArray, Collections.reverseOrder());
        System.out.println(java.util.Arrays.toString(diceArray));
        return diceArray;
    }

    public void compareFaceValue(Integer[] attacker, Integer[] defender, Territory invader, Territory defend) {
        Integer X = Math.min(defender.length , attacker.length);
        for (int i = 0; i < X; i++) {
            if (attacker[i] <= defender[i] && invader.getTroopCount()>1) {/*attacker loses one army*/
                invader.setTroopCount(invader.getTroopCount() - 1);
                System.out.println(invader.getTeam() + " you have lost an army, current number of armies:" +
                        " " + invader.getTroopCount());
            }
               if(invader.getTroopCount()==1){
                  // Attack may not continue
                  break;
                }
            if (attacker[i] > defender[i])
            {/*defender loses one army*/
            defend.setTroopCount(defend.getTroopCount()-1);
                System.out.println(defend.getTeam() +" you have lost an army, current number of armies:" +
                        " "+ defend.getTroopCount());
                if(defend.getTroopCount()==0){
                    System.out.println(defend.getTeam() +" you have lost "+ defend.getName());
                    String x = defend.getTeam();
                    defend.setTeam(invader.getTeam());
                    System.out.println(invader.getTeam()+ ", "+ defend.getName()+" now belongs to you"
                    +", You conquered new land, So may get a card, " +
                            "for every Three cards you turn in you will receive extra troops");
                    WIN = true;
                    if(!telegram){
                    for(Player p: playerList){
                        if(p.getTeam().equals(x)){
                            p.PlayerHand.add(Deck.drawACard());
                              for(int j=0 ; j< p.PlayerHand.size(); j++) {
                                Deck.ShowCard(p.PlayerHand.get(j));
                              }
                           }
                        }
                      }
                    }
                }
            }
           comparison=true;
        }
    }
