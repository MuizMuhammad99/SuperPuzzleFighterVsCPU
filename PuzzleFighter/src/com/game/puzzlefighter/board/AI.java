package com.game.puzzlefighter.board;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

/**
 * A simple AI which uses a simple algorithm. This AI places in such a way that
 * it would occupy less space on board. So, the board does not get filled
 * quickly. The level of the AI determines how much fast does the place blocks
 *
 */
public class AI {

	// state
	public static final int IDLE = 1;
	public static final int PLACING_GEM = 2;

	// level
	public static final int EASY = 1;
	public static final int NORMAL = 2;
	public static final int HARD = 3;

	// moves
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int ROTATE_LEFT = 3;
	public static final int ROTATE_RIGHT = 4;

	private Board board;
	private int state = IDLE;

	private float waitTime = 3f;
	private float waitTimer;

	private int lowestRow;
	private int targetCol;

	private Robot robot;
	private ArrayList<Integer> movesToMake;
	private float moveDelay = 0.4f;
	private float moveTimer;
	private int lastKeyPressed;

	/**
	 * Constructor
	 * 
	 * @param board the board AI will play on
	 * @param level level of intelligence, i.e in this case it is the speed with
	 *              which the ai will place blocks
	 */
	public AI(Board board, int level) {
		this.board = board;

		switch (level) {
		case EASY:
			waitTime = 4f;
			moveDelay = 0.4f;
			break;

		case NORMAL:
			waitTime = 3f;
			moveDelay = 0.2f;
			break;

		case HARD:
			waitTime = 1f;
			moveDelay = 0.2f;
			break;
		}

		movesToMake = new ArrayList<Integer>();
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void update(float delta) {
		releaseLastKeyPressed();
		if (!board.isInputState())
			return;

		switch (state) {
		case IDLE:

			// wait
			waitTimer += delta;
			if (waitTimer >= waitTime) {
				waitTimer = 0;

				// find lowest row which has a cell
				findLowestRow();

				// if no lowest row, meaning board is empty
				if (lowestRow == -1) {

					// make moves
					movesToMake.add(LEFT);
					movesToMake.add(LEFT);
				} else {

					// find a valid target place
					findTargetPlace();

					// make moves to that target place
					int col = 3;
					if (col < targetCol)
						for (int i = col; i < targetCol; i++)
							movesToMake.add(RIGHT);
					else
						for (int i = targetCol; i < col; i++)
							movesToMake.add(LEFT);
				}

				// change state
				state = PLACING_GEM;
			}
			break;
		case PLACING_GEM:

			// if all moves are made then, fast place and return
			if (movesToMake.size() == 0) {
				state = IDLE;
				robot.keyPress(board.getFastPlaceKey());
				lastKeyPressed = board.getFastPlaceKey();
				return;
			}

			// make move with a delay between each move
			moveTimer += delta;
			if (moveTimer >= moveDelay) {
				moveTimer = 0;
				makeMove(movesToMake.get(0));
				movesToMake.remove(0);
			}

			break;
		}
	}

	/**
	 * Releases the last key that was pressed. So it can press other keys
	 */
	private void releaseLastKeyPressed() {
		try {
			robot.keyRelease(lastKeyPressed);
		} catch (Exception e) {

		}
	}

	/**
	 * This function tries to finds a place which is below the lowestRow i.e It
	 * finds a place which would not occupy much space of board by placing it as
	 * below of board as it can.
	 */
	private void findTargetPlace() {
		Cell[][] cells = board.getCells();
		targetCol = 0;

		// see if we can place vertically
		for (int i = Board.ROWS - 2; i >= 0; i--)
			for (int j = 0; j < Board.COLS; j++) {
				if (cells[i][j].isEmpty() && cells[i + 1][j].isEmpty() && i >= lowestRow) {
					targetCol = j;
					return;
				}
			}

		// see if we can place vertically
		for (int i = Board.ROWS - 1; i >= 0; i--)
			for (int j = 0; j + 1 < Board.COLS; j++) {
				if (cells[i][j].isEmpty() && cells[i][j + 1].isEmpty() && i >= lowestRow) {
					targetCol = j;

					movesToMake.add(Math.random() <= 0.5 ? ROTATE_LEFT : ROTATE_RIGHT);
					return;
				}
			}

	}

	/**
	 * This makes the move like a human player by pressing the key for the move
	 * 
	 * @param move the move to make
	 */
	private void makeMove(int move) {
		switch (move) {
		case LEFT:
			robot.keyPress(board.getLeftKey());
			lastKeyPressed = board.getLeftKey();
			break;
		case RIGHT:
			robot.keyPress(board.getRightKey());
			lastKeyPressed = board.getRightKey();
			break;
		case ROTATE_LEFT:
			robot.keyPress(board.getRotateLeftKey());
			lastKeyPressed = board.getRotateLeftKey();
			break;
		case ROTATE_RIGHT:
			robot.keyPress(board.getRotateRightKey());
			lastKeyPressed = board.getRotateRightKey();
			break;
		}
	}

	/**
	 * Finds the lowest row number of a gem placed in the board i.e the gem whoose
	 * position is higher than others.
	 */
	private void findLowestRow() {
		lowestRow = -1;
		for (int i = 0; i < Board.ROWS; i++)
			for (int j = 0; j < Board.COLS; j++)
				if (!board.getCells()[i][j].isEmpty()) {
					lowestRow = i;
					return;
				}
	}

}
