package com.game.puzzlefighter.board;

/**
 * Board observer interface
 * This is implemented by class which observes the board
 *
 */
public interface BoardObserver {

	/**
	 * Notifies the observer that the gems have been destoryed
	 * 
	 * @param board	the notifier board
	 * @param numGems	num of gems
	 */
	public void notifyGemsDestroyed(Board board,int numGems);
	
	/**
	 * notifies that the game has been ended because the entrance cell is filled.
	 * @param board	the notifier board
	 */
	public void notifyGameFinished(Board board);
}
