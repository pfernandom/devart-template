package com.pfms.dev.ffzhz.common;

public enum Genre{
	DISCO(115,120), JAZZ(80,125), TECHNO(140,150), REGGAE(60,80), ROCK(100,160), POP(86,128);
	
	private int min;
	private int max;
	Genre(int min, int max){
		this.min = min;
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	
	public int getRandomTempo(){
		return Util.random(min, max);
	}
}
