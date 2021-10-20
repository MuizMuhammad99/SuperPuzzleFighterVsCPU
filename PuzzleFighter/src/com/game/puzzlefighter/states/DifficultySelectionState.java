package com.game.puzzlefighter.states;

import java.awt.Graphics2D;

import com.game.puzzlefighter.Game;
import com.game.puzzlefighter.PuzzleFighter;
import com.game.puzzlefighter.board.AI;
import com.game.ui.ButtonListener;
import com.game.ui.UIButton;

/**
 * A state which shows difficulty selection for the game against the AI
 *
 */
public class DifficultySelectionState extends State {

	private final UIButton easyBtn;
	private final UIButton normalBtn;
	private final UIButton hardBtn;
	private int levelSelected;

	/**
	 * Constructor
	 * @param game	game
	 */
	public DifficultySelectionState(PuzzleFighter game) {
		super(game);

		// initialize buttons
		easyBtn = new UIButton("easy", "easyHover", 3);
		normalBtn = new UIButton("normal", "normalHover", 3);
		hardBtn = new UIButton("hard", "hardHover", 3);

		// set location
		int x = Game.getWidth() / 2 - easyBtn.getWidth() / 2;
		int y = Game.getHeight() / 2 - easyBtn.getHeight();

		easyBtn.setLocation(x, y);
		normalBtn.setLocation(x, y + easyBtn.getHeight() + 10);
		hardBtn.setLocation(x, y + (easyBtn.getHeight() * 2) + 20);

		// add listeners to buttons
		easyBtn.setButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed() {
				levelSelected = AI.EASY;
			}

		});

		normalBtn.setButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed() {
				levelSelected = AI.NORMAL;
			}

		});

		hardBtn.setButtonListener(new ButtonListener() {

			@Override
			public void buttonPressed() {
				levelSelected = AI.HARD;
			}

		});
	}

	@Override
	public void update(float delta) {
		// update collision bounds,because the coordinates might change if the window
		// resizes
		int x = Game.getWidth() / 2 - easyBtn.getWidth() / 2;
		int y = Game.getHeight() / 2 - easyBtn.getHeight();
		easyBtn.updateBounds(x, y);
		normalBtn.updateBounds(x, y + easyBtn.getHeight() + 10);
		hardBtn.updateBounds(x, y + (easyBtn.getHeight() * 2) + 20);

		// update button
		easyBtn.update();
		normalBtn.update();
		hardBtn.update();

		// if a level is selected,then start game
		if (levelSelected != 0) {
			game.startGame(levelSelected);
			levelSelected = 0;
		}

	}

	@Override
	public void render(Graphics2D g2) {
		easyBtn.render(g2);
		normalBtn.render(g2);
		hardBtn.render(g2);
	}

}
