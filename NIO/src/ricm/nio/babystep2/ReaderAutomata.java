package ricm.nio.babystep2;

public class ReaderAutomata {
	public enum State {
		IDLE, READING
	};

	private State currentState;
	public int totalSize;
	public int receivedSize;
	public byte[] message;

	public ReaderAutomata() {
		this.currentState = State.IDLE;
	}

	public void setReading(int size) {
		this.currentState = State.READING;
		this.totalSize = size;
		this.receivedSize = 0;
		this.message = new byte[size];
	}

	public void setIdle() {
		this.currentState = State.READING;
		this.totalSize = 0;
		this.receivedSize = 0;
	}

	public void setState(State state) {
		this.currentState = state;
	}

	public State getState() {
		return this.currentState;
	}

	public boolean addToMessage(byte[] data) throws Exception {
		if (this.currentState != State.READING)
			throw new Exception();
		for (int i = 0; i < data.length; i++) {
			this.message[i + this.receivedSize] = data[i];
		}
		return this.message.length >= this.totalSize;
	}
}
