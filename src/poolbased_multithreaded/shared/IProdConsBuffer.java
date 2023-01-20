package poolbased_multithreaded.shared;

import java.net.Socket;

public interface IProdConsBuffer {
	/**
	 * Put the message m in the buffer
	 * 
	 * @param m the message to put in the buffer
	 * @throws InterruptedException
	 */
	public void put(Socket listenSoc) throws InterruptedException;

	/**
	 * @return a message from the buffer, following a FIFO order
	 * @throws InterruptedException
	 */
	public Socket get() throws InterruptedException;

	/**
	 * @param k
	 * @return k consecutive messages from the prodcons buffer
	 * @throws InterruptedException
	 */
//	public Message[] get(int k) throws InterruptedException;

	/**
	 * @return the number of messages currently available in the buffer
	 */
	public int msg();

	/**
	 * @return the total number of messages that have been put in the buffer since
	 *         its creation
	 */
	public int totmsg();

}
