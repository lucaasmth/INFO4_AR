package poolbased_multithreaded.shared;

public class Message {
	int text;

	public Message(int text) {
		this.text = text;
	}

	public String toString() {
		return Integer.toString(text);
	}
}
