package ricm.nio.babystep2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

public class WriterAutomata {
	public enum State {
		IDLE, WRITING_LENGTH, WRITING_MSG
	};

	private State currentState;
	public int totalSize;
	public int sentSize;
	public byte[] message;
	public LinkedList<byte[]> pendingMessage;
	private ByteBuffer bb;

	public WriterAutomata() {
		this.currentState = State.IDLE;
		bb = ByteBuffer.allocate(4);
		this.pendingMessage = new LinkedList<byte[]>();
	}

//	public void setWritingSize(byte[] message) {
//		this.currentState = State.WRITING_LENGTH;
//		this.totalSize = message.length;
//		this.sentSize = 0;
//		this.message = null;
//	}

	public void setIdle() {
		this.currentState = State.IDLE;
		this.totalSize = 0;
		this.sentSize = 0;
	}

	public void setState(State state) {
		this.currentState = state;
	}

	public State getState() {
		return this.currentState;
	}

	public boolean isFinished() {
		if (this.pendingMessage.isEmpty() && this.sentSize >= message.length) {
			this.message = null;
			this.currentState = State.IDLE;
			this.sentSize = 0;
			return true;
		}
		return false;
	}

	public void sendMessage(byte[] data) {

		pendingMessage.add(data);
		if (this.currentState == State.IDLE)
			this.currentState = State.WRITING_LENGTH;

	}

	public ByteBuffer getWriteBuffer() throws IOException {
		if (message == null) {
			message = pendingMessage.removeFirst();
			bb.rewind();
		}

		switch (this.currentState) {
		case WRITING_LENGTH:
			bb.putInt(message.length);
			bb.rewind();
			this.currentState = State.WRITING_MSG;
			return bb;
		case WRITING_MSG:
			bb.rewind();
			System.out.println("Sending message from " + this.sentSize);
			for (int i = this.sentSize; i < this.sentSize + 4; i++) {
				if (i < message.length)
					bb.put(message[i]);
				else
					bb.put((byte) 0);
			}
			this.sentSize += 4;
			bb.rewind();
			return bb;

		default:
			throw new IOException();
		}
	}

//	public boolean addToMessage(byte[] data) throws Exception {
//		if (this.currentState != State.READING)
//			throw new Exception();
//		for (int i = 0; i < data.length; i++) {
//			this.message[i + this.receivedSize] = data[i];
//		}
//		return this.message.length >= this.totalSize;
//	}
}
