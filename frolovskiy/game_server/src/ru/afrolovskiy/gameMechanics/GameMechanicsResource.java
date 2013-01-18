package ru.afrolovskiy.gameMechanics;

import ru.afrolovskiy.base.Resource;

public class GameMechanicsResource implements Resource {
	private static final long serialVersionUID = 6089129371735876689L;
	private int FIELD_ROW_COUNT;
	private int FIELD_COLUMN_COUNT;
	private int SLEEP_TIME;
	
	public int getFieldRowCount() {
		return this.FIELD_ROW_COUNT;
	}
	
	public int getFieldColumnCount() {
		return this.FIELD_COLUMN_COUNT;
	}
	
	public int getSleepTime() {
		return this.SLEEP_TIME;
	}

}
