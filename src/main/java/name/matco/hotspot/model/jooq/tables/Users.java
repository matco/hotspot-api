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
import name.matco.hotspot.model.jooq.tables.Spot.SpotPath;
import name.matco.hotspot.model.jooq.tables.Stash.StashPath;
import name.matco.hotspot.model.jooq.tables.records.UsersRecord;
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
public class Users extends TableImpl<UsersRecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * The reference instance of <code>users</code>
	 */
	public static final Users USERS = new Users();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UsersRecord> getRecordType() {
		return UsersRecord.class;
	}

	/**
	 * The column <code>users.pk</code>.
	 */
	public final TableField<UsersRecord, Integer> PK = createField(DSL.name("pk"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

	/**
	 * The column <code>users.creation_date</code>.
	 */
	public final TableField<UsersRecord, ZonedDateTime> CREATION_DATE = createField(DSL.name("creation_date"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("current_timestamp()"), SQLDataType.LOCALDATETIME)), this, "", new DateConverter());

	/**
	 * The column <code>users.modification_date</code>.
	 */
	public final TableField<UsersRecord, ZonedDateTime> MODIFICATION_DATE = createField(DSL.name("modification_date"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field(DSL.raw("current_timestamp()"), SQLDataType.LOCALDATETIME)), this, "", new DateConverter());

	/**
	 * The column <code>users.handle</code>.
	 */
	public final TableField<UsersRecord, String> HANDLE = createField(DSL.name("handle"), SQLDataType.VARCHAR(200).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	/**
	 * The column <code>users.firstname</code>.
	 */
	public final TableField<UsersRecord, String> FIRSTNAME = createField(DSL.name("firstname"), SQLDataType.VARCHAR(200).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	/**
	 * The column <code>users.lastname</code>.
	 */
	public final TableField<UsersRecord, String> LASTNAME = createField(DSL.name("lastname"), SQLDataType.VARCHAR(200).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	/**
	 * The column <code>users.email</code>.
	 */
	public final TableField<UsersRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(200).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	/**
	 * The column <code>users.password</code>.
	 */
	public final TableField<UsersRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(60).defaultValue(DSL.field(DSL.raw("NULL"), SQLDataType.VARCHAR)), this, "");

	private Users(Name alias, Table<UsersRecord> aliased) {
		this(alias, aliased, (Field<?>[]) null, null);
	}

	private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters, Condition where) {
		super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
	}

	/**
	 * Create an aliased <code>users</code> table reference
	 */
	public Users(String alias) {
		this(DSL.name(alias), USERS);
	}

	/**
	 * Create an aliased <code>users</code> table reference
	 */
	public Users(Name alias) {
		this(alias, USERS);
	}

	/**
	 * Create a <code>users</code> table reference
	 */
	public Users() {
		this(DSL.name("users"), null);
	}

	public <O extends Record> Users(Table<O> path, ForeignKey<O, UsersRecord> childPath, InverseForeignKey<O, UsersRecord> parentPath) {
		super(path, childPath, parentPath, USERS);
	}

	/**
	 * A subtype implementing {@link Path} for simplified path-based joins.
	 */
	public static class UsersPath extends Users implements Path<UsersRecord> {

		private static final long serialVersionUID = 1L;
		public <O extends Record> UsersPath(Table<O> path, ForeignKey<O, UsersRecord> childPath, InverseForeignKey<O, UsersRecord> parentPath) {
			super(path, childPath, parentPath);
		}
		private UsersPath(Name alias, Table<UsersRecord> aliased) {
			super(alias, aliased);
		}

		@Override
		public UsersPath as(String alias) {
			return new UsersPath(DSL.name(alias), this);
		}

		@Override
		public UsersPath as(Name alias) {
			return new UsersPath(alias, this);
		}

		@Override
		public UsersPath as(Table<?> alias) {
			return new UsersPath(alias.getQualifiedName(), this);
		}
	}

	@Override
	public Schema getSchema() {
		return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
	}

	@Override
	public Identity<UsersRecord, Integer> getIdentity() {
		return (Identity<UsersRecord, Integer>) super.getIdentity();
	}

	@Override
	public UniqueKey<UsersRecord> getPrimaryKey() {
		return Keys.KEY_USERS_PRIMARY;
	}

	@Override
	public List<UniqueKey<UsersRecord>> getUniqueKeys() {
		return Arrays.asList(Keys.KEY_USERS_USER_HANDLE, Keys.KEY_USERS_USER_EMAIL);
	}

	private transient SpotPath _spot;

	/**
	 * Get the implicit to-many join path to the <code>hotspot.spot</code> table
	 */
	public SpotPath spot() {
		if (_spot == null)
			_spot = new SpotPath(this, null, Keys.SPOT_USER.getInverseKey());

		return _spot;
	}

	private transient StashPath _stash;

	/**
	 * Get the implicit to-many join path to the <code>hotspot.stash</code> table
	 */
	public StashPath stash() {
		if (_stash == null)
			_stash = new StashPath(this, null, Keys.STASH_USER.getInverseKey());

		return _stash;
	}

	@Override
	public Users as(String alias) {
		return new Users(DSL.name(alias), this);
	}

	@Override
	public Users as(Name alias) {
		return new Users(alias, this);
	}

	@Override
	public Users as(Table<?> alias) {
		return new Users(alias.getQualifiedName(), this);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Users rename(String name) {
		return new Users(DSL.name(name), null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Users rename(Name name) {
		return new Users(name, null);
	}

	/**
	 * Rename this table
	 */
	@Override
	public Users rename(Table<?> name) {
		return new Users(name.getQualifiedName(), null);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users where(Condition condition) {
		return new Users(getQualifiedName(), aliased() ? this : null, null, condition);
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users where(Collection<? extends Condition> conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users where(Condition... conditions) {
		return where(DSL.and(conditions));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users where(Field<Boolean> condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Users where(SQL condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Users where(@Stringly.SQL String condition) {
		return where(DSL.condition(condition));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Users where(@Stringly.SQL String condition, Object... binds) {
		return where(DSL.condition(condition, binds));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	@PlainSQL
	public Users where(@Stringly.SQL String condition, QueryPart... parts) {
		return where(DSL.condition(condition, parts));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users whereExists(Select<?> select) {
		return where(DSL.exists(select));
	}

	/**
	 * Create an inline derived table from this table
	 */
	@Override
	public Users whereNotExists(Select<?> select) {
		return where(DSL.notExists(select));
	}
}
