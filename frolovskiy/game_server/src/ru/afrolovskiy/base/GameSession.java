package ru.afrolovskiy.base;

import java.util.Date;

import ru.afrolovskiy.gameMechanics.GameMechanicsResource;
import ru.afrolovskiy.resourceSystem.ResourceFactoryImpl;

public class GameSession {
	private int userId1;
	private int userId2;
	private GameState gameState;
	private int currentStepUserId;
	private Date startTime = null;
	private Date finishTime = null;
	private Field field;
	
	public GameSession(int userId1, int userId2) {
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.gameState = GameState.Initialization;
		
		ResourceFactory factory = ResourceFactoryImpl.getInstance();
		GameMechanicsResource resource = (GameMechanicsResource) factory.getResource(
				"src/ru/afrolovskiy/GameMechanics/GameMechanicsResource.xml");
		this.field = new Field(resource.getFieldRowCount(), 
							   resource.getFieldColumnCount());
	}
	
	
	
	
	public void start() {		
		startTime = new Date();
		this.gameState = GameState.Play;
		this.currentStepUserId = userId1;
	}
	
	public void nextStep() {
		if (currentStepUserId == userId1) {
			currentStepUserId = userId2;
		} else if (currentStepUserId == userId2){
			currentStepUserId = userId1;
		}
	}
	
	public boolean addDot(int userId, int i, int j) {
		if (currentStepUserId == userId && field.isEmpty(i, j)) {
			field.setPointState(i, j, getUserPointState(userId));
			return true;
		}
		return false;
	}
	
	public PointState getUserPointState(int userId) {
		if (userId == userId1) {
			return PointState.BLUE;
		}
		return PointState.RED;
	}
	
	public void finish() {
		finishTime = new Date();
		this.gameState = GameState.Finish;
	}
	
	public int getUserId1() {
		return this.userId1;
	}
	
	public int getUserId2() {
		return this.userId2;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public Date getStartTime() {
		return this.startTime;
	}
	
	public Date getFinishTime() {
		return this.finishTime;
	}
	
	public int getCurrentStepUserId() {
		return this.currentStepUserId;
	}
	
	public Field getField() {
		return this.field;
	}
}
