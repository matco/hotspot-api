/*
 * This file is generated by jOOQ.
 */
package name.matco.hotspot.model.jooq.tables.records;


import java.time.ZonedDateTime;

import name.matco.hotspot.model.jooq.tables.Stash;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class StashRecord extends UpdatableRecordImpl<StashRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * Setter for <code>stash.pk</code>.
	 */
	public void setPk(Integer value) {
		set(0, value);
	}

	/**
	 * Getter for <code>stash.pk</code>.
	 */
	public Integer getPk() {
		return (Integer) get(0);
	}

	/**
	 * Setter for <code>stash.user_fk</code>.
	 */
	public void setUserFk(Integer value) {
		set(1, value);
	}

	/**
	 * Getter for <code>stash.user_fk</code>.
	 */
	public Integer getUserFk() {
		return (Integer) get(1);
	}

	/**
	 * Setter for <code>stash.uuid</code>.
	 */
	public void setUuid(String value) {
		set(2, value);
	}

	/**
	 * Getter for <code>stash.uuid</code>.
	 */
	public String getUuid() {
		return (String) get(2);
	}

	/**
	 * Setter for <code>stash.creation_date</code>.
	 */
	public void setCreationDate(ZonedDateTime value) {
		set(3, value);
	}

	/**
	 * Getter for <code>stash.creation_date</code>.
	 */
	public ZonedDateTime getCreationDate() {
		return (ZonedDateTime) get(3);
	}

	/**
	 * Setter for <code>stash.modification_date</code>.
	 */
	public void setModificationDate(ZonedDateTime value) {
		set(4, value);
	}

	/**
	 * Getter for <code>stash.modification_date</code>.
	 */
	public ZonedDateTime getModificationDate() {
		return (ZonedDateTime) get(4);
	}

	/**
	 * Setter for <code>stash.name</code>.
	 */
	public void setName(String value) {
		set(5, value);
	}

	/**
	 * Getter for <code>stash.name</code>.
	 */
	public String getName() {
		return (String) get(5);
	}

	/**
	 * Setter for <code>stash.description</code>.
	 */
	public void setDescription(String value) {
		set(6, value);
	}

	/**
	 * Getter for <code>stash.description</code>.
	 */
	public String getDescription() {
		return (String) get(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached StashRecord
	 */
	public StashRecord() {
		super(Stash.STASH);
	}

	/**
	 * Create a detached, initialised StashRecord
	 */
	public StashRecord(Integer pk, Integer userFk, String uuid, ZonedDateTime creationDate, ZonedDateTime modificationDate, String name_, String description) {
		super(Stash.STASH);

		setPk(pk);
		setUserFk(userFk);
		setUuid(uuid);
		setCreationDate(creationDate);
		setModificationDate(modificationDate);
		setName(name_);
		setDescription(description);
		resetChangedOnNotNull();
	}
}
