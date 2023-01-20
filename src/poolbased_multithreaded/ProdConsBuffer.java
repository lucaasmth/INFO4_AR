package poolbased_multithreaded;

import java.net.Socket;
import java.util.concurrent.Semaphore;

import poolbased_multithreaded.shared.IProdConsBuffer;

public class ProdConsBuffer implements IProdConsBuffer {

	int bufferSize;
	Socket[] buffer;
	int putIndex;
	int getIndex;
	boolean producersFinished;
	Semaphore notFull;
	Semaphore notEmpty;
	Semaphore mutex;
	Semaphore getK;

	public ProdConsBuffer(int bufferSize) {
		this.bufferSize = bufferSize;
		this.buffer = new Socket[bufferSize];
		this.putIndex = 0;
		this.getIndex = 0;
		this.producersFinished = false;
		this.notFull = new Semaphore(bufferSize);
		this.notEmpty = new Semaphore(0);
		this.mutex = new Semaphore(1);
		this.getK = new Semaphore(1);
	}

	@Override
	public void put(Socket listenSoc) throws InterruptedException {
		this.notFull.acquire();
		this.mutex.acquire();
		this.buffer[this.putIndex % this.bufferSize] = listenSoc;
		this.putIndex++;
		System.out.println("Message " + listenSoc + " put");
		this.mutex.release();
		this.notEmpty.release();
	}

	@Override
	public Socket get() throws InterruptedException {
		this.notEmpty.acquire();
		this.mutex.acquire();
		Socket listenSoc = this.buffer[this.getIndex % this.bufferSize];
		// this.buffer[this.getIndex % this.bufferSize] = null;
		if (listenSoc != null)
			this.getIndex++;
		this.mutex.release();
		this.notFull.release();
		return listenSoc;
	}

	@Override
	public synchronized int msg() {
		return this.putIndex - this.getIndex;
	}

	@Override
	public int totmsg() {
		return this.putIndex;
	}

	public void finished() {
		this.producersFinished = true;
	}

	public boolean isFinished() {
		return this.producersFinished;
	}

//	@Override
//	public Message[] get(int k) throws InterruptedException {
//		this.getK.acquire();
//		Message[] messages = new Message[k];
//		for (int i = 0; i < k; i++) {
//			try {
//				this.notEmpty.acquire();
//				this.mutex.acquire();
//				messages[i] = this.buffer[this.getIndex % this.bufferSize];
//				this.getIndex++;
//				this.mutex.release();
//				this.notFull.release();
//			} catch (InterruptedException e) {
//				// Si on reçoit une interruption alors que le tableau a commencé à être rempli
//				if (i > 0)
//					System.out.println("Warning remaining messages!");
//				throw e;
//			}
//		}
//		// this.buffer[this.getIndex % this.bufferSize] = null;
//		System.out.print("ConsThread got [");
//		for (int i = 0; i < k; i++) {
//			System.out.print(messages[i] + ",");
//		}
//		System.out.println("]");
//		this.getK.release();
//		return messages;
//
//	}

}
