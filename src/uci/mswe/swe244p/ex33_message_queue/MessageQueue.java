package uci.mswe.swe244p.ex33_message_queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {
	private static int n_ids = 0;

	public static void main(String[] args) {
		int producerCount = 1;
		int consumerCount = 1;

		try {
			producerCount = Integer.valueOf(args[0]);
			consumerCount = Integer.valueOf(args[1]);
		} catch (Exception e) {
			System.err.println("Invalid arguments or no arguments.");
		} finally {
			System.out
					.println("Using producerCount=" + producerCount + ", consumerCount=" + consumerCount);
		}

		BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(10);
		List<Producer> producers = new ArrayList<Producer>();
		List<Consumer> consumers = new ArrayList<Consumer>();

		for (int i = 0; i < producerCount; ++i) {
			var producer = new Producer(queue, n_ids++);
			producers.add(producer);
			new Thread(producer).start();
		}

		for (int i = 0; i < consumerCount; ++i) {
			var consumer = new Consumer(queue, n_ids++);
			consumers.add(consumer);
			new Thread(consumer).start();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (var producer : producers) {
			producer.stop();
		}

		// ! make sure all consumers stop when there are fewer producers
		if (producerCount < consumerCount) {
			int extraStopsNeeded = consumerCount - producerCount;
			for (int i = 0; i < extraStopsNeeded; ++i) {
				try {
					queue.put(new Message("stop"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
