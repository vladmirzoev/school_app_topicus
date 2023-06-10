package somtoday.registrations.sql;

/**
 * Updates an existing registration depending on the new/altered files or answered fields of a registration, and is the
 * UPDATE function of the CRUD operations. This class forwards the new data and performs an SQL ALTER TABLE and any
 * similar functions (+ refreshing materialized views if any) on the database.
 *
 * A check will be run to see if the altered attribute of that entry was actually performed.
 */
public class UpdateRegistrationSQL {
}
