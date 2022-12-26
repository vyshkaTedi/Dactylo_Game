package model;

import java.util.ArrayList;
import java.util.List;

public class GameNormalSolo extends Game {
	private List<String> currentList;
	private int currentPos;
	private int score;

	@Override
	public void init() {
		WordList.generateList();
		this.currentList = WordList.startingList();
		this.currentPos = 0;
		this.score = 0;
	}
	
	public static void main(String[] args) {

		GameNormalSolo game = new GameNormalSolo();
		game.init();
        System.out.println(game.currentList.toString());
        String word1 = game.currentList.get(0);
        for(int i = 0; i < word1.length()-1; i++) {
        	game.keyInput(word1.charAt(i+1));
        }
    	game.keyInput(' ');
	}

	@Override
	public boolean keyInput(int k) {
		// TODO handle input (character k)
		if(k == ' ') {
			String word = this.currentList.get(0);
			if(word.length() == this.currentPos) {
				//Word done -> move to next one
				this.score++;
				this.currentPos = 0;
				WordList.update(currentList); //Update list, remove head and add new word
				System.out.println("Finished word: " + word + " , score = " + this.score);
				return true;
			}
			return false;
		}
		else {
			System.out.println("Adding character: " + ((char)k));
			String word = this.currentList.get(0);
			if(word.length() == this.currentPos) {
				System.out.println("Wrong character");
				return false; //Wrong input, waiting for space
			}
			else if (k == word.charAt(this.currentPos)) {
				this.currentPos++;
				return true;
			}else {
				System.out.println("Expected " + word.charAt(this.currentPos) + " ; got " + (char)k);
				return false;
			}
		}
	}

	@Override
	public String getWord() {
		return this.currentList.get(0);
	}

	@Override
	public List<String> getList() {
		return this.currentList;
	}


}
