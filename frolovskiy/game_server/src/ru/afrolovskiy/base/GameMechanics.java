package ru.afrolovskiy.base;

public interface GameMechanics extends Abonent, Runnable {
	public void startGameSession(int userId1, int userId2);
	public void replicateGamesToFrontend();
	public void addDot(int userId, int i, int j);
	public GameSession getGameSession(int userId);
}
