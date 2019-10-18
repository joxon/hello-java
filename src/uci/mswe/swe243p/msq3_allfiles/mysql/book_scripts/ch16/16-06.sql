SHOW EVENTS;

SHOW EVENTS IN ap;

SHOW EVENTS IN ap LIKE 'mon%';

ALTER EVENT monthly_delete_audit_rows DISABLE;

ALTER EVENT monthly_delete_audit_rows ENABLE;

ALTER EVENT one_time_delete_audit_rows RENAME TO one_time_delete_audits;

DROP EVENT monthly_delete_audit_rows;

DROP EVENT IF EXISTS monthly_delete_audit_rows;