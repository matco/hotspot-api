/*
 * This file is generated by jOOQ.
 */
package name.matco.hotspot.model.jooq.tables;


import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import name.matco.hotspot.model.jooq.DefaultSchema;
import name.matco.hotspot.model.jooq.Keys;
import name.matco.hotspot.model.jooq.tables.StashSpot.StashSpotPath;
import name.matco.hotspot.model.jooq.tables.Users.UsersPath;
import name.matco.hotspot.model.jooq.tables.records.SpotRecord;
import name.matco.hotspot.services.jooq.configuration.DateConverter;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Spot extends TableImpl<SpotRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * The reference instance of <code>spot</code>
	 */
	public static final Spot SPOT = new Spot();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<SpotRecord> getRecordType() {
		return SpotRecord.class;
	}

	/**
	 * The column <code>spot.pk</code>.
	 */
	public final TableField<SpotRecord, Integer> PK = createField(DSL.name("pk"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

	/**
	 * The column <code>spot.user_fk</code>.
	 */
	public final TableField<SpotRecord, Integer> USER_FK = createField(DSL.name("user_fk"), SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>spot.uuid</code>.
	 */
	public final TableField<SpotRecord, String> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR(64).nullable(false), this, "");

	/**
	 * The column <code>spot.creation_date</code>.
	 */
	public final TableField<SpotRecord, ZonedDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("current_timestamp()"), SQLDataType.LOCALDATETIME)), this, "", new DateConverter());

	/**
	 * The column <code>spot.modification_date</code>.
	 */
	public final TableField<SpotRecord, ZonedDateTime> MODIFICATION_DATE = createField(DSL.name("modification_date"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("current_timestamp()"), SQLDataType.LOCALDATETIME)), this, "", new DateConverter());

	/**
	 * The column <code>spot.name</code>.
	 */
	public final TableField<SpotRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(200).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	/**
	 * The column <code>spot.latitude</code>.
	 */
	public final TableField<SpotRecord, Double> LATITUDE = createField(DSL.name("latitude"), SQLDataType.DOUBLE.defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.DOUBLE)), this, "");

	/**
	 * The column <code>spot.longitude</code>.
	 */
	public final TableField<SpotRecord, Double> LONGITUDE = createField(DSL.name("longitude"), SQLDataType.DOUBLE.defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.DOUBLE)), this, "");

	/**
	 * The column <code>spot.description</code>.
	 */
	public final TableField<SpotRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.CLOB.defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.CLOB)), this, "");

	private Spot(Name alias, Table<SpotRecord> aliased) {
		this(alias, aliased, (Field<?>[]) null, null);
	}

	private Spot(Name alias, Table<SpotRecord> aliased, Field<?>[] parameters, Condition where) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
	}

	/**
	 * Create an aliased <code>spot</code> table reference
	 */
	public Spot(String alias) {
		this(DSL.name(alias), SPOT);
	}

	/**
	 * Create an aliased <code>spot</code> table reference
	 */
	public Spot(Name alias) {
		this(alias, SPOT);
	}

	/**
	 * Create a <code>spot</code> table reference
	 */
	public Spot() {
		this(DSL.name("spot"), null);
	}

	public <O extends Record> Spot(Table<O> path, ForeignKey<O, SpotRecord> childPath, InverseForeignKey<O, SpotRecord> parentPath) {
		super(path, childPath, parentPath, SPOT);
	}

	/**
	 * A subtype implementing {@link Path} for simplified path-based joins.
	 */
	public static class SpotPath extends Spot implements Path<SpotRecord> {

		private static final long serialVersionUID = 1L;
		public <O extends Record> SpotPath(Table<O> path, ForeignKey<O, SpotRecord> childPath, InverseForeignKey<O, SpotRecord> parentPath) {
			super(path, childPath, parentPath);
		}
		private SpotPath(Name alias, Table<SpotRecord> aliased) {
			super(alias, aliased);
		}

		@Override
		public SpotPath as(String alias) {
			return new SpotPath(DSL.name(alias), this);
		}

		@Override
		public SpotPath as(Name alias) {
			return new SpotPath(alias, this);
		}

		@Override
		public SpotPath as(Table<?> alias) {
			return new SpotPath(alias.getQualifiedName(), this);
		}
	}

	@Override
	public Schema getSchema() {
		return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public Identity<SpotRecord, Integer> getIdentity() {
		return (Identity<SpotRecord, Integer>) super.getIdentity();
	}

	@Override
	public UniqueKey<SpotRecord> getPrimaryKey() {
		return Keys.KEY_SPOT_PRIMARY;
	}

	@Override
	public List<UniqueKey<SpotRecord>> getUniqueKeys() {
		return Arrays.asList(Keys.KEY_SPOT_SPOT_UUID);
	}

	@Override
	public List<ForeignKey<SpotRecord, ?>> getReferences() {
		return Arrays.asList(Keys.SPOT_USER);
	}

	private transient UsersPath _users;

	/**
	 * Get the implicit join path to the <code>hotspot.users</code> table.
	 */
	public UsersPath users() {
		if (_users == null)
			_users = new UsersPath(this, Keys.SPOT_USER, null);

		return _users;
	}

	private transient StashSpotPath _stashSpot;

	/**
	 * Get the implicit to-many join path to the <code>hotspot.stash_spot</code>
	 * table
	 */
	public StashSpotPath stashSpot() {
		if (_stashSpot == null)
			_stashSpot = new StashSpotPath(this, null, Keys.SPOT.getInverseKey());

		return _stashSpot;
	}

	@Override
	public Spot as(String alias) {
		return new Spot(DSL.name(alias), this);
	}

	@Override
	public Spot as(Name alias) {
		return new Spot(alias, this);
	}

	@Override
	public Spot as(Table<?> alias) {
		return new Spot(alias.getQualifiedName(), this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Spot rename(String name) {
		return new Spot(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Spot rename(Name name) {
		return new Spot(name, null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Spot rename(Table<?> name) {
		return new Spot(name.getQualifiedName(), null);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot where(Condition condition) {
		return new Spot(getQualifiedName(), aliased() ? this : null, null, condition);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot where(Collection<? extends Condition> conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot where(Condition... conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot where(Field<Boolean> condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Spot where(SQL condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Spot where(@Stringly.SQL String condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Spot where(@Stringly.SQL String condition, Object... binds) {
		return where(DSL.condition(condition, binds));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Spot where(@Stringly.SQL String condition, QueryPart... parts) {
		return where(DSL.condition(condition, parts));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot whereExists(Select<?> select) {
		return where(DSL.exists(select));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Spot whereNotExists(Select<?> select) {
		return where(DSL.notExists(select));
	}
}
