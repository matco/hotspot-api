/*
 * This file is generated by jOOQ.
 */
package name.matco.hotspot.model.jooq.tables;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import name.matco.hotspot.model.jooq.DefaultSchema;
import name.matco.hotspot.model.jooq.Keys;
import name.matco.hotspot.model.jooq.tables.Spot.SpotPath;
import name.matco.hotspot.model.jooq.tables.Stash.StashPath;
import name.matco.hotspot.model.jooq.tables.records.StashSpotRecord;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class StashSpot extends TableImpl<StashSpotRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * The reference instance of <code>stash_spot</code>
	 */
	public static final StashSpot STASH_SPOT = new StashSpot();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<StashSpotRecord> getRecordType() {
		return StashSpotRecord.class;
	}

	/**
	 * The column <code>stash_spot.stash_fk</code>.
	 */
	public final TableField<StashSpotRecord, Integer> STASH_FK = createField(DSL.name("stash_fk"), SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>stash_spot.spot_fk</code>.
	 */
	public final TableField<StashSpotRecord, Integer> SPOT_FK = createField(DSL.name("spot_fk"), SQLDataType.INTEGER.nullable(false), this, "");

	private StashSpot(Name alias, Table<StashSpotRecord> aliased) {
		this(alias, aliased, (Field<?>[]) null, null);
	}

	private StashSpot(Name alias, Table<StashSpotRecord> aliased, Field<?>[] parameters, Condition where) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
	}

	/**
	 * Create an aliased <code>stash_spot</code> table reference
	 */
	public StashSpot(String alias) {
		this(DSL.name(alias), STASH_SPOT);
	}

	/**
	 * Create an aliased <code>stash_spot</code> table reference
	 */
	public StashSpot(Name alias) {
		this(alias, STASH_SPOT);
	}

	/**
	 * Create a <code>stash_spot</code> table reference
	 */
	public StashSpot() {
		this(DSL.name("stash_spot"), null);
	}

	public <O extends Record> StashSpot(Table<O> path, ForeignKey<O, StashSpotRecord> childPath, InverseForeignKey<O, StashSpotRecord> parentPath) {
		super(path, childPath, parentPath, STASH_SPOT);
	}

	/**
	 * A subtype implementing {@link Path} for simplified path-based joins.
	 */
	public static class StashSpotPath extends StashSpot implements Path<StashSpotRecord> {

		private static final long serialVersionUID = 1L;
		public <O extends Record> StashSpotPath(Table<O> path, ForeignKey<O, StashSpotRecord> childPath, InverseForeignKey<O, StashSpotRecord> parentPath) {
			super(path, childPath, parentPath);
		}
		private StashSpotPath(Name alias, Table<StashSpotRecord> aliased) {
			super(alias, aliased);
		}

		@Override
		public StashSpotPath as(String alias) {
			return new StashSpotPath(DSL.name(alias), this);
		}

		@Override
		public StashSpotPath as(Name alias) {
			return new StashSpotPath(alias, this);
		}

		@Override
		public StashSpotPath as(Table<?> alias) {
			return new StashSpotPath(alias.getQualifiedName(), this);
		}
	}

	@Override
	public Schema getSchema() {
		return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public List<ForeignKey<StashSpotRecord, ?>> getReferences() {
		return Arrays.asList(Keys.STASH, Keys.SPOT);
	}

	private transient StashPath _stash;

	/**
	 * Get the implicit join path to the <code>hotspot.stash</code> table.
	 */
	public StashPath stash() {
		if (_stash == null)
			_stash = new StashPath(this, Keys.STASH, null);

		return _stash;
	}

	private transient SpotPath _spot;

	/**
	 * Get the implicit join path to the <code>hotspot.spot</code> table.
	 */
	public SpotPath spot() {
		if (_spot == null)
			_spot = new SpotPath(this, Keys.SPOT, null);

		return _spot;
	}

	@Override
	public StashSpot as(String alias) {
		return new StashSpot(DSL.name(alias), this);
	}

	@Override
	public StashSpot as(Name alias) {
		return new StashSpot(alias, this);
	}

	@Override
	public StashSpot as(Table<?> alias) {
		return new StashSpot(alias.getQualifiedName(), this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public StashSpot rename(String name) {
		return new StashSpot(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public StashSpot rename(Name name) {
		return new StashSpot(name, null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public StashSpot rename(Table<?> name) {
		return new StashSpot(name.getQualifiedName(), null);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot where(Condition condition) {
		return new StashSpot(getQualifiedName(), aliased() ? this : null, null, condition);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot where(Collection<? extends Condition> conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot where(Condition... conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot where(Field<Boolean> condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public StashSpot where(SQL condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public StashSpot where(@Stringly.SQL String condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public StashSpot where(@Stringly.SQL String condition, Object... binds) {
		return where(DSL.condition(condition, binds));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public StashSpot where(@Stringly.SQL String condition, QueryPart... parts) {
		return where(DSL.condition(condition, parts));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot whereExists(Select<?> select) {
		return where(DSL.exists(select));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public StashSpot whereNotExists(Select<?> select) {
		return where(DSL.notExists(select));
	}
}
