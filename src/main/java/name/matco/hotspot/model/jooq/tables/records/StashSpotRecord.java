/*
 * This file is generated by jOOQ.
 */
package name.matco.hotspot.model.jooq.tables.records;


import name.matco.hotspot.model.jooq.tables.StashSpot;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class StashSpotRecord extends TableRecordImpl<StashSpotRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * Setter for <code>stash_spot.stash_fk</code>.
	 */
	public void setStashFk(Integer value) {
		set(0, value);
	}

	/**
	 * Getter for <code>stash_spot.stash_fk</code>.
	 */
	public Integer getStashFk() {
		return (Integer) get(0);
	}

	/**
	 * Setter for <code>stash_spot.spot_fk</code>.
	 */
	public void setSpotFk(Integer value) {
		set(1, value);
	}

	/**
	 * Getter for <code>stash_spot.spot_fk</code>.
	 */
	public Integer getSpotFk() {
		return (Integer) get(1);
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached StashSpotRecord
	 */
	public StashSpotRecord() {
		super(StashSpot.STASH_SPOT);
	}

	/**
	 * Create a detached, initialised StashSpotRecord
	 */
	public StashSpotRecord(Integer stashFk, Integer spotFk) {
		super(StashSpot.STASH_SPOT);

		setStashFk(stashFk);
		setSpotFk(spotFk);
		resetChangedOnNotNull();
	}
}
