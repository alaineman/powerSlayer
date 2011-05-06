package org.powerbot.powerslayer.common;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.rsbot.script.methods.Players;
import org.rsbot.script.wrappers.RSGroundItem;
import org.rsbot.script.wrappers.RSNPC;
import org.rsbot.script.wrappers.RSObject;
import org.rsbot.script.wrappers.RSPlayer;
import org.rsbot.script.wrappers.RSTile;

public abstract class DMethodProvider {
	public MethodBase methods;

	/**
	 * The logger instance
	 */
	public final Logger log;

	public DMethodProvider(MethodBase methods) {
		this.methods = methods;
		log = Logger
				.getLogger((methods != null && methods.parent != null ? methods.parent
						.getClass().getName() + "-"
						: "")
						+ getClass().getName());
	}

	/**
	 * Returns the current client's local player.
	 * 
	 * @return The current client's <tt>RSPlayer</tt>.
	 * @see Players#getMyPlayer()
	 */
	public RSPlayer getMyPlayer() {
		return methods.players.getMyPlayer();
	}

	/**
	 * Returns a random integer with min as the inclusive lower bound and max as
	 * the exclusive upper bound.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random integer min <= n < max.
	 */
	public int random(int min, int max) {
		int n = Math.abs(max - min);
		return Math.min(min, max) + (n == 0 ? 0 : methods.random.nextInt(n));
	}

	/**
	 * Checks for the existence of a NPC.
	 * 
	 * @param npc
	 *            The NPC to check for.
	 * @return <tt>true</tt> if found.
	 */
	public boolean verify(RSNPC npc) {
		return npc != null;
	}

	/**
	 * Checks for the existence of a RSObject.
	 * 
	 * @param o
	 *            The RSObject to check for.
	 * @return <tt>true</tt> if found.
	 */
	public boolean verify(RSObject o) {
		return o != null;
	}

	/**
	 * Checks for the existence of a RSTile.
	 * 
	 * @param t
	 *            The RSTile to check for.
	 * @return <tt>true</tt> if found.
	 */
	public boolean verify(RSTile t) {
		return t != null;
	}

	/**
	 * Checks for the existence of a RSGroundItem.
	 * 
	 * @param i
	 *            The RSGroundItem to check for.
	 * @return <tt>true</tt> if found.
	 */
	public boolean verify(RSGroundItem i) {
		return i != null;
	}

	/**
	 * Returns a random double with min as the inclusive lower bound and max as
	 * the exclusive upper bound.
	 * 
	 * @param min
	 *            The inclusive lower bound.
	 * @param max
	 *            The exclusive upper bound.
	 * @return Random double min <= n < max.
	 */
	public double random(double min, double max) {
		return Math.min(min, max) + methods.random.nextDouble()
				* Math.abs(max - min);
	}

	/**
	 * Pauses execution for a random amount of time between two values.
	 * 
	 * @param minSleep
	 *            The minimum time to sleep.
	 * @param maxSleep
	 *            The maximum time to sleep.
	 * @see #sleep(int)
	 * @see #random(int, int)
	 */
	public void sleep(int minSleep, int maxSleep) {
		sleep(random(minSleep, maxSleep));
	}

	/**
	 * Pauses execution for a given number of milliseconds.
	 * 
	 * @param toSleep
	 *            The time to sleep in milliseconds.
	 */
	public void sleep(int toSleep) {
		try {
			long start = System.currentTimeMillis();
			Thread.sleep(toSleep);

			// Guarantee minimum sleep
			long now;
			while (start + toSleep > (now = System.currentTimeMillis())) {
				Thread.sleep(start + toSleep - now);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints to the RSBot log.
	 * 
	 * @param message
	 *            Object to log.
	 */
	public void log(Object message) {
		log.info(message.toString());
	}

	/**
	 * Prints to the RSBot log with a font color
	 * 
	 * @param color
	 *            The color of the font
	 * @param message
	 *            Object to log
	 */
	public void log(Color color, Object message) {
		Object[] parameters = { color };
		log.log(Level.INFO, message.toString(), parameters);
	}
}