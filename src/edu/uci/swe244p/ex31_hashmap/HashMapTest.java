package edu.uci.swe244p.ex31_hashmap;

import java.util.HashMap;

/**
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
 *
 * First, it is not possible for two invocations of synchronized methods on the same object to
 * interleave. When one thread is executing a synchronized method for an object, all other threads
 * that invoke synchronized methods for the same object block (suspend execution) until the first
 * thread is done with the object.
 *
 * Second, when a synchronized method exits, it automatically establishes a happens-before
 * relationship with any subsequent invocation of a synchronized method for the same object. This
 * guarantees that changes to the state of the object are visible to all threads.
 *
 */
public class HashMapTest {

	private boolean running = true;
	private HashMap<String, Integer> people = new HashMap<String, Integer>();

	private synchronized void addPerson() {
		people.put(RandomUtils.randomString(), RandomUtils.randomInteger());
	}

	private synchronized void deletePeople(String pattern) {
		// https://stackoverflow.com/questions/8104692/how-to-avoid-java-util-concurrentmodificationexception-when-iterating-through-an

		// code throws the ConcurrentModificationException exception because you are modifying the list
		// while iterating.

		// for (String name : people.keySet()) {
		// if (name.contains(pattern))
		// people.remove(name);
		// }
		var it = people.keySet().iterator();
		while (it.hasNext()) {
			var name = it.next();
			if (name.contains(pattern)) {
				System.out.println("Deleting " + name);
				it.remove();
			}
		}

	}

	private synchronized void printPeople() {
		// ! Exception in thread "Printer" java.util.ConcurrentModificationException
		// https://www.journaldev.com/378/java-util-concurrentmodificationexception
		for (HashMap.Entry<String, Integer> entry : people.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("People size = " + people.size());
		System.out.println("-----------------------------------------");
	}

	public void run() {
		// Start printer thread
		new Thread(new Runnable() {
			public void run() {
				Thread.currentThread().setName("Printer");
				while (running) {
					printPeople();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();

		// Start deleter thread
		new Thread(new Runnable() {
			public void run() {
				Thread.currentThread().setName("Deleter");
				while (running) {
					deletePeople("a");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();

		// This thread adds entries
		for (int i = 0; i < 100; i++) {
			addPerson();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		running = false;
	}

	public static void main(String[] args) {
		new HashMapTest().run();
	}

}
